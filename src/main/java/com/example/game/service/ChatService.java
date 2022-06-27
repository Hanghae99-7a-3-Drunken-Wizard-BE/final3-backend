package com.example.game.service;

import com.example.game.Game.player.User;
import com.example.game.model.chat.ChatMessage;
import com.example.game.model.room.Room;
import com.example.game.repository.chat.RedisRepository;
import com.example.game.repository.room.EnterUserRepository;
import com.example.game.repository.room.RoomRepository;
import com.example.game.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatService {

    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final RedisRepository redisRepository;
    private final RoomRepository roomRepository;

    private final String defaultImg = "이미지 url";

    //     destination정보에서 roomId 추출
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1) {
            return destination.substring(lastIndex + 1);
        } else {
            return "";
        }
    }

    public void sendChatMessage(ChatMessage chatMessage) throws InterruptedException {

        chatMessage.setUserCount(redisRepository.getUserCount(chatMessage.getRoomId()));

        // 스터디룸 입장
        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 방에 입장했습니다.");
            chatMessage.setSender("\uD83D\uDD14 [알림]");
        }
        // 스터디룸 퇴장
        if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 방에서 나갔습니다.");
            chatMessage.setSender("\uD83D\uDD14 [알림]");
        }

        // 스터디 시작
        if (ChatMessage.MessageType.CLOSE.equals(chatMessage.getType())) {
            chatMessage.setMessage("스터디가 시작되었습니다.");
            chatMessage.setSender("\uD83D\uDD14 [알림]");
            Room room = roomRepository.findByRoomId(chatMessage.getRoomId());
            roomRepository.save(room);
        }
        // 스터디 종료
        if (ChatMessage.MessageType.OPEN.equals(chatMessage.getType())) {
            chatMessage.setMessage("스터디가 종료되었습니다.");
            chatMessage.setSender("\uD83D\uDD14 [알림]");
            Room room = roomRepository.findByRoomId(chatMessage.getRoomId());
            roomRepository.save(room);
        }

//        // 추방 기능
//        if (ChatMessage.MessageType.BAN.equals(chatMessage.getType())) {
//            String roomId = chatMessage.getRoomId();
//            Room room = roomRepository.findByroomId(roomId).orElseThrow(
//                    () -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));
//            User user = userRepository.findAllByUsername(banuser);
//
//
//            chatMessage.setSenderId(chatMessage.getSenderId());
//            chatMessage.setMessage(chatMessage.getBanUsername() + "님이 추방 당했습니다.");
//            chatMessage.setSender("[알림]");
//            chatMessage.setProfileImg(defaultImg);
//            chatMessage.setBanUsername(banuser);
//            chatMessage.setRoomTitle(chatMessage.getRoomTitle());
//        }

        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }
}
