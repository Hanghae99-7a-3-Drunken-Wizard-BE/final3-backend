package com.example.game.websocket;

import com.example.game.Game.gameDataDto.DtoGenerator;
import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.dto.response.GameRoomCreateResponseDto;
import com.example.game.dto.response.GameRoomJoinResponseDto;
import com.example.game.dto.response.GameRoomListResponseDto;
import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import com.example.game.security.UserDetailsImpl;
import com.example.game.websocket.redis.RedisSubscriber;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameRoomService {

    private final GameRoomRepository gameRoomRepository;
    private final UserRepository userRepository;
    private final JsonStringBuilder jsonStringBuilder;
    private final DtoGenerator dtoGenerator;
    private final SimpMessageSendingOperations messagingTemplate;

    private final RedisMessageListenerContainer redisMessageListener;

    private final RedisSubscriber redisSubscriber;

    //ChatRoom 전체 조회
    public Page<GameRoom> getAllGameRooms(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return gameRoomRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public ResponseEntity<GameRoomCreateResponseDto> createGameRoom(GameRoomRequestDto requestDto, UserDetailsImpl userDetails) {
        GameRoom room = new GameRoom(UUID.randomUUID().toString(), requestDto.getRoomName());
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                ()-> new NullPointerException("유저 없음"));
        gameRoomRepository.save(room);
        user.setRoomId(room.getRoomId());
        userRepository.save(user);
        GameRoomCreateResponseDto responseDto = new GameRoomCreateResponseDto(room.getRoomId(), requestDto.getRoomName());
        System.out.println("gameRoom roomId = " + room.getRoomId()); // roomId 콘솔창에 찍기
        return ResponseEntity.ok().body(responseDto);
    }

    public ResponseEntity<GameRoomJoinResponseDto> joinGameRoom(String roomId, UserDetailsImpl userDetails) throws JsonProcessingException{
        List<User> userList = userRepository.findByRoomId(roomId);
        if (userList.size() >= 4) {
            GameRoomJoinResponseDto responseDto = new GameRoomJoinResponseDto(false);
            return ResponseEntity.ok().body(responseDto);
        }
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                ()-> new NullPointerException("유저 없음"));
        user.setRoomId(roomId);
        userRepository.save(user);
        GameRoomJoinResponseDto responseDto = new GameRoomJoinResponseDto(true);
        GameRoom room = gameRoomRepository.findByRoomId(roomId);
        String userListMessage = jsonStringBuilder.gameRoomResponseDtoJsonBuilder(
                roomId, room.getRoomName(), userList);
        GameMessage gameMessage = new GameMessage();
        gameMessage.setRoomId(roomId);
        gameMessage.setContent(userListMessage);
        gameMessage.setType(GameMessage.MessageType.UPDATE);
        messagingTemplate.convertAndSend("/sub/game/" + roomId, gameMessage);
        return ResponseEntity.ok().body(responseDto);
    }

    @Transactional
    public ResponseEntity<GameRoomListResponseDto> leaveGameRoom(String roomId, int page, int size, UserDetailsImpl userDetails) throws JsonProcessingException {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                ()-> new NullPointerException("유저 없음"));
        System.out.println("나가는 유저 조회중 : "+user.getUsername());
        GameRoom gameRoom = gameRoomRepository.findByRoomId(roomId);
        user.setRoomId(null);
        Long userId = user.getId();
        if (Objects.equals(gameRoom.getPlayer1(), userId)) {gameRoom.setPlayer1(null);}
        if (Objects.equals(gameRoom.getPlayer2(), userId)) {gameRoom.setPlayer2(null);}
        if (Objects.equals(gameRoom.getPlayer3(), userId)) {gameRoom.setPlayer3(null);}
        if (Objects.equals(gameRoom.getPlayer4(), userId)) {gameRoom.setPlayer4(null);}
        System.out.println(userRepository.findByRoomId(roomId).size() + " 줄었나 확인해볼까");
        List<User> userList = userRepository.findByRoomId(roomId);
        for(User u : userList) {
            System.out.println(u.getUsername() + " 지금은 누가 남았지");
        }
        String userListMessage = jsonStringBuilder.gameRoomResponseDtoJsonBuilder(
                roomId, gameRoom.getRoomName(), userList);
        GameMessage message = new GameMessage();
        message.setRoomId(roomId);
        message.setContent(userListMessage);
        message.setType(GameMessage.MessageType.UPDATE);
        messagingTemplate.convertAndSend("/sub/game/" + roomId, message);
        if (userList.size() == 0) {
            gameRoomRepository.delete(gameRoom);
        }
        return ResponseEntity.ok().body(dtoGenerator.gameRoomListResponseDtoMaker(getAllGameRooms(page, size)));
    }

    public Page<GameRoom> searchGameRooms(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return gameRoomRepository.findByRoomNameContaining(keyword, pageable);
    }


//    /**
//     * 채팅방 입장 : redis에 topic을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다.
//     */
//    public void enter() {
//        ChannelTopic topic = new ChannelTopic("public");
//        if (topic == null) {
//            topic = new ChannelTopic("public");
//            redisMessageListener.addMessageListener(redisSubscriber, topic);
//            topics.put(topic);
//        }
//    }
//
//    public ChannelTopic getTopic() {
//        return topics;
//    }
}
//package com.example.game.websocket;
//
//import com.example.game.Game.GameRoom;
//import com.example.game.security.UserDetailsImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class GameRoomService {
//
//    private final GameRoomRepository gameRoomRepository;
//
//    //ChatRoom 전체 조회
//    public List<GameRoom> getAllGameRooms() {
//        return gameRoomRepository.findAllByOrderByCreatedAtDesc();
//    }
//
//    // ChatRoom 생성
//    public ResponseEntity createGameRoom(GameRoomRequestDto requestDto, UserDetailsImpl userDetails) {
//        GameRoom room = GameRoom.builder()
//                .roomId(UUID.randomUUID().toString())
//                .nickName(userDetails.getUser().getNickname())
//                .roomName(requestDto.getRoomName())
//                .build();
//        gameRoomRepository.save(room);
//        System.out.println("ChatRoom roomId = " + room.getRoomId()); // roomId 콘솔창에 찍기
//        return new ResponseEntity("생성 성공", HttpStatus.OK);
//    }
//
//    // roonName 검색으로 ChatRoom 조회
//    public List<GameRoom> searchGameRooms(String keyword) {
//        return gameRoomRepository.findByRoomNameContaining(keyword);
//    }
//}

