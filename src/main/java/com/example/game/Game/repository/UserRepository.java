package com.example.game.Game.repository;


import com.example.game.Game.player.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
