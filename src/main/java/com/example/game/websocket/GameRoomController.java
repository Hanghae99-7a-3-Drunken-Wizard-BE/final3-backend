package com.example.game.websocket;

import com.example.game.Game.gameDataDto.DtoGenerator;
import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.dto.response.GameRoomCreateResponseDto;
import com.example.game.dto.response.GameRoomJoinResponseDto;
import com.example.game.repository.user.UserRepository;
import com.example.game.security.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    public ResponseEntity<Page<GameRoom>> readGameRooms(
            @RequestParam int page, @RequestParam int size
            ) {
        System.out.println(LocalDateTime.now());
        System.out.println("겟 메서드 접수");
        System.out.println(page);
        System.out.println(size);
        page -= 1;
        Pageable pageable = PageRequest.of(page, size);
        System.out.println(gameRoomRepository.findAll().size());
        Page<GameRoom> gameRooms = gameRoomRepository.findAllByOrderByCreatedAtDesc(pageable);
        System.out.println(LocalDateTime.now());
        return ResponseEntity.ok().body(gameRooms);
    }

    // GameRoom 생성
    @PostMapping(value = "/game/room")
    public ResponseEntity<GameRoomCreateResponseDto> createGameRoom(
            @RequestBody GameRoomRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return gameRoomService.createGameRoom(requestDto, userDetails);
    }

    // ChatRoom roomName으로 검색 조회
    @GetMapping(value = "/game/rooms/search")
    public ResponseEntity<Page<GameRoom>> searchGameRooms(
            @RequestParam(required = false) String keyword,
            @RequestParam int page, @RequestParam int size
    ) {
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
        return gameRoomService.joinGameRoom(roomId,userDetails);
    }

    @PostMapping("/game/{roomId}/leave")
    public ResponseEntity<Page<GameRoom>> leaveGameRoom(
            @PathVariable String roomId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam int page, @RequestParam int size
    )
            throws JsonProcessingException {
        page = page - 1;
        return gameRoomService.leaveGameRoom(roomId, page, size, userDetails);
    }
}