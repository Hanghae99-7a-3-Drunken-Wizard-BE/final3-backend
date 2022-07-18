package com.example.game.websocket;

import com.example.game.Game.gameDataDto.DtoGenerator;
import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.dto.response.GameRoomCreateResponseDto;
import com.example.game.dto.response.GameRoomJoinResponseDto;
import com.example.game.dto.response.GameRoomListResponseDto;
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
    private final DtoGenerator dtoGenerator;

    // 채팅방 목록 조회
    @GetMapping(value = "/game/rooms")
    public ResponseEntity<GameRoomListResponseDto> readGameRooms(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) throws JsonProcessingException {

        page = page - 1;

        return ResponseEntity.ok().body(dtoGenerator.gameRoomListResponseDtoMaker(gameRoomService.getAllGameRooms(page, size)));
    }

    // GameRoom 생성
    @PostMapping(value = "/game/room")
    public ResponseEntity<GameRoomCreateResponseDto> createGameRoom(
            @RequestBody GameRoomRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws JsonProcessingException {
        return gameRoomService.createGameRoom(requestDto, userDetails);
    }

    // ChatRoom roomName으로 검색 조회
    @GetMapping(value = "/game/rooms/search")
    public ResponseEntity<GameRoomListResponseDto> searchGameRooms(
            @RequestParam(required = false) String keyword,
            @RequestParam("page") int page,
            @RequestParam("size") int size)
    throws JsonProcessingException{
        if (keyword != null) {
            page = page - 1;
            return ResponseEntity.ok().body(dtoGenerator.gameRoomListResponseDtoMaker(gameRoomService.searchGameRooms(keyword, page, size)));
        }
        page = page - 1;
        return ResponseEntity.ok().body(dtoGenerator.gameRoomListResponseDtoMaker(gameRoomService.getAllGameRooms(page, size)));
    }

    @PostMapping("/game/{roomId}/join")
    public ResponseEntity<GameRoomJoinResponseDto> joinGameRoom(
            @PathVariable String roomId, @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws JsonProcessingException {
        return gameRoomService.joinGameRoom(roomId,userDetails);
    }

    @PostMapping("/game/{roomId}/leave")
    public ResponseEntity<GameRoomListResponseDto> leaveGameRoom(
            @PathVariable String roomId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam("page") int page,
            @RequestParam("size") int size)
            throws JsonProcessingException {
        page = page - 1;
        return gameRoomService.leaveGameRoom(roomId, page, size, userDetails);
    }
}