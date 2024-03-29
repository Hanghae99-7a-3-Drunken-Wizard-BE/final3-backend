package com.example.game.websocket;

import com.example.game.Game.gameDataDto.DtoGenerator;
import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.Game.h2Package.GameRoom;
import com.example.game.Game.repository.GameRoomRepository;
import com.example.game.dto.response.GameRoomCreateResponseDto;
import com.example.game.dto.response.GameRoomJoinResponseDto;
import com.example.game.dto.response.UserWinRateResponseDto;
import com.example.game.repository.user.UserRepository;
import com.example.game.security.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GameRoomController {
    private final GameRoomService gameRoomService;
    private final SimpMessageSendingOperations messagingTemplate;
    private final GameRoomRepository gameRoomRepository;
    private final UserRepository userRepository;
    private final JsonStringBuilder jsonStringBuilder;
    private final DtoGenerator dtoGenerator;

    // 채팅방 목록 조회
    @GetMapping(value = "/game/rooms")
    public ResponseEntity<Page<GameRoom>> readGameRooms(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok().body(gameRoomService.readGameRooms(page, size));
    }

    // 전적 불러오기
    @PostMapping( "/userhistory")
    public ResponseEntity<UserWinRateResponseDto> readUserDetail(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(new UserWinRateResponseDto(userDetails.getUser().getWinCount(), userDetails.getUser().getLoseCount()));
    }

    // GameRoom 생성
    @PostMapping(value = "/game/room")
    public ResponseEntity<GameRoomCreateResponseDto> createGameRoom(
            @RequestBody GameRoomRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws JsonProcessingException {
        return gameRoomService.createGameRoom(requestDto, userDetails);
    }

    // ChatRoom roomName으로 검색 조회
    @GetMapping(value = "/game/rooms/search")
    public ResponseEntity<Page<GameRoom>> searchGameRooms(
            @RequestParam(required = false) String keyword,
            @RequestParam int page, @RequestParam int size) {
        if (keyword != null) {
            page = page - 1;
            return ResponseEntity.ok().body(gameRoomService.searchGameRooms(keyword, page, size));
        }
        page = page - 1;
        return ResponseEntity.ok().body(gameRoomService.getAllGameRooms(page, size));
    }

    @PostMapping("/game/{roomId}/join")
    public ResponseEntity<GameRoomJoinResponseDto> joinGameRoom(
            @PathVariable String roomId, @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws JsonProcessingException {
        System.out.println("포스트매핑 조인 메서드 작동중");
        return gameRoomService.joinGameRoom(roomId,userDetails.getUser().getId());
    }

    @PostMapping("/game/{roomId}/leave")
    public String leaveGameRoom(
            @PathVariable String roomId,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws JsonProcessingException {
        gameRoomService.leaveGameRoom(roomId, userDetails);
        return "퇴장 완료";
    }
}