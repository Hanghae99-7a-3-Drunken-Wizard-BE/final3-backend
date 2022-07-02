package com.example.game.websocket;

import com.example.game.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping(value = "/chat", method = {RequestMethod.GET, RequestMethod.POST})
@RequiredArgsConstructor
public class GameRoomController {
    private final GameRoomService gameRoomService;

    // 채팅방 목록 조회
    @GetMapping(value = "/rooms")
    public ResponseEntity<List<GameRoom>> readChatRooms() {
        return ResponseEntity.ok().body(gameRoomService.getAllGameRooms());
    }

    // ChatRoom 생성
    @PostMapping(value = "/room")
    public ResponseEntity createGameRoom(@RequestBody GameRoomRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(gameRoomService.createGameRoom(requestDto, userDetails));
    }

    // ChatRoom roomName으로 검색 조회
    @GetMapping(value = "/rooms/search")
    public ResponseEntity<List<GameRoom>> searchGameRooms(@RequestParam(required = false) String keyword) {
        if (keyword != null) {
            return ResponseEntity.ok().body(gameRoomService.searchGameRooms(keyword));
        }
        return ResponseEntity.ok().body(gameRoomService.getAllGameRooms());
    }
}