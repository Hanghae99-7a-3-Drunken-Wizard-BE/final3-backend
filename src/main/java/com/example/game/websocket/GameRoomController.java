package com.example.game.websocket;

import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import com.example.game.security.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameRoomController {
    private final GameRoomService gameRoomService;
    private final SimpMessageSendingOperations messagingTemplate;
    private final GameRoomRepository gameRoomRepository;
    private final UserRepository userRepository;
    private final JsonStringBuilder jsonStringBuilder;

    // 채팅방 목록 조회
    @GetMapping(value = "/game/rooms")
    public ResponseEntity<List<GameRoom>> readGameRooms() {
        return ResponseEntity.ok().body(gameRoomService.getAllGameRooms());
    }

    // GameRoom 생성
    @PostMapping(value = "/game/room")
    public ResponseEntity createGameRoom(@RequestBody GameRoomRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String roomId = gameRoomService.createGameRoom(requestDto, userDetails);
        return ResponseEntity.ok().body(roomId);
    }

    // ChatRoom roomName으로 검색 조회
    @GetMapping(value = "/game/rooms/search")
    public ResponseEntity<List<GameRoom>> searchGameRooms(@RequestParam(required = false) String keyword) {
        if (keyword != null) {
            return ResponseEntity.ok().body(gameRoomService.searchGameRooms(keyword));
        }
        return ResponseEntity.ok().body(gameRoomService.getAllGameRooms());
    }

    @PostMapping("/game/{roomId}/join")
    public ResponseEntity<String> joinGameRoom(
            @PathVariable String roomId, @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws JsonProcessingException {
        GameRoom gameRoom = gameRoomRepository.findByRoomId(roomId);
        System.out.println(gameRoom.getRoomName());
//        if (gameRoom.getUserList().size() >= 4) {
//            return ResponseEntity.badRequest().body("정원 초과");
//package com.example.game.websocket;
//
//import com.example.game.Game.GameRoom;
//import com.example.game.security.UserDetailsImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
////@RequestMapping(value = "/chat", method = {RequestMethod.GET, RequestMethod.POST})
//@RequiredArgsConstructor
//public class GameRoomController {
//    private final GameRoomService gameRoomService;
//
//    // 채팅방 목록 조회
//    @GetMapping(value = "/rooms")
//    public ResponseEntity<List<GameRoom>> readChatRooms() {
//        return ResponseEntity.ok().body(gameRoomService.getAllGameRooms());
//    }
//
//    // ChatRoom 생성
//    @PostMapping(value = "/room")
//    public ResponseEntity createGameRoom(@RequestBody GameRoomRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return ResponseEntity.ok().body(gameRoomService.createGameRoom(requestDto, userDetails));
//    }
//
//    // ChatRoom roomName으로 검색 조회
//    @GetMapping(value = "/rooms/search")
//    public ResponseEntity<List<GameRoom>> searchGameRooms(@RequestParam(required = false) String keyword) {
//        if (keyword != null) {
//            return ResponseEntity.ok().body(gameRoomService.searchGameRooms(keyword));
//        }
        System.out.println(userDetails.getUsername());
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                ()-> new NullPointerException("유저 없음"));
        gameRoom.addUser(user);
        System.out.println(gameRoom.getUserList().get(0).getUsername());
        System.out.println(roomId);
        user.setRoomId(roomId);
        userRepository.save(user);
        gameRoomRepository.save(gameRoom);
        GameMessage message = new GameMessage();
        message.setRoomId(roomId);
        message.setContent(jsonStringBuilder.lobbyUserListDtoJsonBuilder(gameRoom.getUserList()));
        message.setType(GameMessage.MessageType.JOIN);
        messagingTemplate.convertAndSend("/sub/game/" + roomId, message);

        return ResponseEntity.ok().body("게임 로비에 입장했습니다.");
    }

    @PostMapping("/game/{roomId}/leave")
    public ResponseEntity<List<GameRoom>> leaveGameRoom(
            @PathVariable String roomId, @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws JsonProcessingException {
        GameRoom gameRoom = gameRoomRepository.findByRoomId(roomId);
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                ()-> new NullPointerException("유저 없음"));;
        gameRoom.removeUser(user);
        user.setRoomId(null);
        if (gameRoom.getUserList().size() == 0) {gameRoomRepository.delete(gameRoom);}
        GameMessage message = new GameMessage();
        message.setRoomId(roomId);
        message.setContent(jsonStringBuilder.lobbyUserListDtoJsonBuilder(gameRoom.getUserList()));
        message.setType(GameMessage.MessageType.LEAVE);
        messagingTemplate.convertAndSend("/sub/game/" + roomId, message);

        return ResponseEntity.ok().body(gameRoomService.getAllGameRooms());
    }
}