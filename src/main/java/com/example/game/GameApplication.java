package com.example.game;

import com.example.game.Game.card.ApplyCard;
import com.example.game.Game.card.magic.attack.MagicMissile;
import com.example.game.Game.player.Player;
import com.example.game.Game.player.PlayerStatus;
import com.example.game.Game.player.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableScheduling // 스케줄링 사용
@EnableJpaAuditing  // 생성 시간/수정 시간 자동 업데이트
@SpringBootApplication
public class GameApplication {

    public static void main(String[] args) {

        SpringApplication.run(GameApplication.class, args);

        PlayerStatus playerStatus = new PlayerStatus();
        ApplyCard applyCard = new ApplyCard(playerStatus);
        User user1= new User(1L, "user1", "password", 1, 1);
        User user2 = new User(2L, "user2", "password", 1, 1);
        Player player1 = new Player(user1);
        Player player2 = new Player(user2);
        MagicMissile magicMissile = new MagicMissile();
        applyCard.applyCardtoTarget(player1, player2, magicMissile);
    }

    // 타임스탬프시간 맞추기
    @PostConstruct
    public void started(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
