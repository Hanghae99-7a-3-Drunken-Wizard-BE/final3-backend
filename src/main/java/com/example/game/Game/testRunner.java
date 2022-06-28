package com.example.game.Game;

import com.example.game.Game.card.ApplyCardToCharacter;
import com.example.game.Game.service.GameCloser;
import com.example.game.Game.service.GameStarter;
import com.example.game.model.User;
import com.example.game.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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


        User user1 = new User("user1", "111", "nickname1", "email@emal1.com");
        User user2 = new User("user2", "111", "nickname2", "email@emal2.com");
        User user3 = new User("user3", "111", "nickname3", "email@emal3.com");
        User user4 = new User("user4", "111", "nickname4", "email@emal4.com");

        List<User> userList = new ArrayList<>();

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

        userRepository.saveAll(userList);

        GameRoom gameRoom = gameStarter.createGameRoom(userList);

        applyCardToCharacter.cardInitiator(1L, 2L, 4L);
        applyCardToCharacter.cardInitiator(1L, 1L, 1L);
        applyCardToCharacter.cardInitiator(3L, 3L, 2L);
        applyCardToCharacter.cardInitiator(4L, 4L, 3L);
        applyCardToCharacter.cardInitiator(1L, 3L, 4L);
        applyCardToCharacter.cardInitiator(1L, 4L, 4L);

    }
}
