package com.example.game.websocket;

import com.example.game.Game.gameDataDto.DtoGenerator;
import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.dto.response.GameRoomListResponseDto;
import com.example.game.dto.response.GameRoomResponseDto;
import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import com.example.game.security.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameRoomService {

    private final GameRoomRepository gameRoomRepository;
    private final UserRepository userRepository;
    private final JsonStringBuilder jsonStringBuilder;
    private final DtoGenerator dtoGenerator;
    private final SimpMessageSendingOperations messagingTemplate;


    public List<GameRoom> getAllGameRooms() {
        return gameRoomRepository.findAllByOrderByCreatedAtDesc();
    }

    public GameRoomResponseDto createGameRoom(GameRoomRequestDto requestDto, UserDetailsImpl userDetails) throws JsonProcessingException {
        GameRoom room = GameRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(requestDto.getRoomName())
                .build();
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                ()-> new NullPointerException("유저 없음"));
        gameRoomRepository.save(room);
        user.setRoomId(room.getRoomId());
        userRepository.save(user);
        List<User> userList = userRepository.findByRoomId(room.getRoomId());
        System.out.println("gameRoom roomId = " + room.getRoomId()); // roomId 콘솔창에 찍기
        return new GameRoomResponseDto(room.getRoomId(), requestDto.getRoomName(), userList);
    }

    public ResponseEntity<GameRoomResponseDto> joinGameRoom(String roomId, UserDetailsImpl userDetails) throws JsonProcessingException{
        GameRoom room = gameRoomRepository.findByRoomId(roomId);
        List<User> userList = userRepository.findByRoomId(roomId);
        if (userList.size() >= 4) {
            throw new IllegalArgumentException("제한인원 초과");
        }
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                ()-> new NullPointerException("유저 없음"));
        user.setRoomId(roomId);
        userRepository.save(user);
        userList.add(user);
        GameRoomResponseDto gameRoomResponseDto = new GameRoomResponseDto(roomId, room.getRoomName(), userList);
        String userListMessage = jsonStringBuilder.gameRoomResponseDtoJsonBuilder(
                roomId, room.getRoomName(), userList);
        GameMessage message = new GameMessage();
        message.setRoomId(roomId);
        message.setContent(userListMessage);
        message.setType(GameMessage.MessageType.JOIN);
        messagingTemplate.convertAndSend("/sub/game/" + roomId, message);

        return ResponseEntity.ok().body(gameRoomResponseDto);
    }

    public ResponseEntity<GameRoomListResponseDto> leaveGameRoom(String roomId, UserDetailsImpl userDetails) throws JsonProcessingException {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                ()-> new NullPointerException("유저 없음"));
        List<User> userList = userRepository.findByRoomId(roomId);
        GameRoom gameRoom = gameRoomRepository.findByRoomId(roomId);
        user.setRoomId(null);
        String userListMessage = jsonStringBuilder.gameRoomResponseDtoJsonBuilder(
                roomId, gameRoom.getRoomName(), userList);
        GameMessage message = new GameMessage();
        message.setRoomId(roomId);
        message.setContent(userListMessage);
        message.setType(GameMessage.MessageType.LEAVE);
        messagingTemplate.convertAndSend("/sub/game/" + roomId, message);
        if (userList.size() == 0) {
            gameRoomRepository.delete(gameRoom);
        }
        return ResponseEntity.ok().body(dtoGenerator.gameRoomListResponseDtoMaker(getAllGameRooms()));
    }

    public List<GameRoom> searchGameRooms(String keyword) {
        return gameRoomRepository.findByRoomNameContaining(keyword);
    }
}