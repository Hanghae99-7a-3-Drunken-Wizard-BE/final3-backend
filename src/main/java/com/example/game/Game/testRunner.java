package com.example.game.Game;

import com.example.game.Game.card.ApplyCardToCharacter;
import com.example.game.Game.player.User;
import com.example.game.Game.repository.GameRepository;
import com.example.game.Game.repository.UserRepository;
import com.example.game.Game.service.GameCloser;
import com.example.game.Game.service.GameStarter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class testRunner implements ApplicationRunner {

    private final GameStarter gameStarter;
    private final GameCloser gameCloser;
    private final UserRepository userRepository;
    private final ApplyCardToCharacter applyCardToCharacter;


    @Override
    public void run(ApplicationArguments args) throws Exception {


        User user1 = new User("user1", "111");
        User user2 = new User("user2", "111");
        User user3 = new User("user3", "111");
        User user4 = new User("user4", "111");

        List<User> userList = new ArrayList<>();

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

        userRepository.saveAll(userList);

        GameRoom gameRoom = gameStarter.createGameRoom(userList);

        applyCardToCharacter.cardInitiator(1L, 2L, 1L);






    }
}
