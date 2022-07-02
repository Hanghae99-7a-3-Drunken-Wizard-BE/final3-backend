package com.example.game.websocket;

import com.example.game.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameRoomService {

    private final GameRoomRepository gameRoomRepository;

    //ChatRoom 전체 조회
    public List<GameRoom> getAllGameRooms() {
        return gameRoomRepository.findAllByOrderByCreatedAtDesc();
    }

    // ChatRoom 생성
    public ResponseEntity createGameRoom(GameRoomRequestDto requestDto, UserDetailsImpl userDetails) {
        GameRoom room = GameRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .nickName(userDetails.getUser().getNickname())
                .roomName(requestDto.getRoomName())
                .build();
        gameRoomRepository.save(room);
        System.out.println("ChatRoom roomId = " + room.getRoomId()); // roomId 콘솔창에 찍기
        return new ResponseEntity("생성 성공", HttpStatus.OK);
    }

    // roonName 검색으로 ChatRoom 조회
    public List<GameRoom> searchGameRooms(String keyword) {
        return gameRoomRepository.findByRoomNameContaining(keyword);
    }
}
