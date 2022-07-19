package com.example.game.dto.response;

import com.example.game.model.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GameRoomResponseDto {
    private String roomId;
    private String roomName;
    private Long player1;
    private String player1Nickname;
    private Boolean player1ready;
    private Long player2;
    private String player2Nickname;
    private Boolean player2ready;
    private Long player3;
    private String player3Nickname;
    private Boolean player3ready;
    private Long player4;
    private String player4Nickname;
    private Boolean player4ready;


    public GameRoomResponseDto(String roomId,
                               String roomName,
                               Long player1,
                               String player1Nickname,
                               Long player2,
                               String player2Nickname,
                               Long player3,
                               String player3Nickname,
                               Long player4,
                               String player4Nickname
    ) {
        this.roomId = roomId;
        this.roomName = roomName;
        if (player1 != null) {
            this.player1 = (player1 > 0) ? player1 : player1 * -1;
            this.player1ready = player1 > 0;
            this.player1Nickname = player1Nickname;}
        if (player2 != null) {
            this.player2 = (player2 > 0) ? player2 : player2 * -1;
            this.player2ready = player2 > 0;
            this.player2Nickname = player2Nickname;}
        if (player3 != null) {
            this.player3 = (player3 > 0) ? player3 : player3 * -1;
            this.player3ready = player3 > 0;
            this.player3Nickname = player3Nickname;}
        if (player4 != null) {
            this.player4 = (player4 > 0) ? player4 : player4 * -1;
            this.player4ready = player4 > 0;
            this.player4Nickname = player4Nickname;}

    }
}
