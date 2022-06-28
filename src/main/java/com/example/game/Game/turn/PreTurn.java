package com.example.game.Game.turn;

import com.example.game.Game.player.Player;
import com.example.game.Game.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class PreTurn {

    private final PlayerRepository playerRepository;

    @Transactional
    public boolean PreTurn(Long id) {
        Player player = playerRepository.findById(id).orElseThrow(
                ()->new NullPointerException("해당 플레이어가 존재하지 않습니다"));
        if(player.getPoisonedDuration() > 0){player.setHealth(player.getHealth()-1);}
        if(player.getStunnedDuration() > 0){return false;}
        if(player.getPetrifiedDuration() > 0){return false;}
        if(player.getSleepDuration()
                > 0){return false;}
        return true;
    }
}
