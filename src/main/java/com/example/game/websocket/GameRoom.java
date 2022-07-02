//package com.example.game.websocket;
//
//import com.example.game.model.Timestamped;
//import com.example.game.security.UserDetailsImpl;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.UUID;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Entity
//public class GameRoom extends Timestamped {
//
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    private Long id;
//
//    @Column
//    private String roomId;
//
//    @Column
//    private String nickName;
//
//    @Column
//    private String roomName;
//
//    @Builder
//    public GameRoom(String roomId, String nickName, String roomName) {
//        this.roomId = roomId;
//        this.nickName = nickName;
//        this.roomName = roomName;
//    }
//
//    public GameRoom(GameRoomRequestDto requestDto, UserDetailsImpl userDetails) {
//        this.roomId = UUID.randomUUID().toString();
//        this.nickName = userDetails.getUser().getNickname();
//        this.roomName = requestDto.getRoomName();
//    }
//}
