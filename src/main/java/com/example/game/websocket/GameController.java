package com.example.game.websocket;

import com.example.game.Game.Game;
import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.Game.gameDataDto.ObjectBuilder;
import com.example.game.Game.gameDataDto.request.CardSelectRequestDto;
import com.example.game.Game.gameDataDto.request.UseCardDto;
import com.example.game.Game.gameDataDto.subDataDto.DiscardDto;
import com.example.game.Game.repository.GameRepository;
import com.example.game.Game.service.GameStarter;
import com.example.game.Game.turn.ActionTurn;
import com.example.game.Game.turn.EndGame;
import com.example.game.Game.turn.EndTurn;
import com.example.game.Game.turn.PreTurn;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@RequiredArgsConstructor
@Controller
public class GameController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final GameStarter gameStarter;
    private final GameRepository gameRepository;
    private final JsonStringBuilder jsonStringBuilder;
    private final ObjectBuilder objectBuilder;
    private final PreTurn preTurn;
    private final ActionTurn actionTurn;
    private final EndTurn endTurn;
    private final EndGame endGame;

    @MessageMapping("/game/{roomId}")
    public void gameMessageProxy(@Payload GameMessage message) throws JsonProcessingException {
        System.out.println("여기에 들어오나 메시지매핑 메서드");
        if (GameMessage.MessageType.START.equals(message.getType())) {
            System.out.println("여기에 들어오나" + message.getType());
            gameStarter(message);
        }
        if (GameMessage.MessageType.PRECHECK.equals(message.getType())) {
            System.out.println("여기에 들어오나" + message.getType());
            precheck(message);
        }
        if (GameMessage.MessageType.DRAW.equals(message.getType())) {
            System.out.println("여기에 들어오나" + message.getType());
            draw(message);
        }
        if (GameMessage.MessageType.SELECT.equals(message.getType())) {
            System.out.println("여기에 들어오나" + message.getType());
            select(message);
        }
        if (GameMessage.MessageType.TURNCHECK.equals(message.getType())) {
            System.out.println("여기에 들어오나" + message.getType());
            turnCheck(message);
        }
        if (GameMessage.MessageType.USECARD.equals(message.getType())) {
            System.out.println("여기에 들어오나" + message.getType());
            useCard(message);
        }
        if (GameMessage.MessageType.DISCARD.equals(message.getType())) {
            System.out.println("여기에 들어오나" + message.getType());
            discard(message);
        }
        if (GameMessage.MessageType.ENDTURN.equals(message.getType())) {
            System.out.println("여기에 들어오나" + message.getType());
            endCheck(message);
        }
        if (GameMessage.MessageType.ENDGAME.equals(message.getType())) {
            System.out.println("여기에 들어오나" + message.getType());
            endGame(message);
        }
    }

    public void gameStarter(GameMessage message) throws JsonProcessingException {
        System.out.println("여기에 들어오나 게임스타터");
        gameStarter.createGameRoom(message.getRoomId());
        Game game = gameRepository.findByRoomId(message.getRoomId());
        String messageContent = jsonStringBuilder.gameStarter(game);
        GameMessage gameMessage = new GameMessage();
        gameMessage.setRoomId(message.getRoomId());
        gameMessage.setSender(message.getSender());
        gameMessage.setType(GameMessage.MessageType.START);
        gameMessage.setContent(messageContent);
        messagingTemplate.convertAndSend("/sub/game/" + message.getRoomId(), gameMessage);
    }

    private void precheck(GameMessage message) throws JsonProcessingException {
        String messageContent = preTurn.preturnStartCheck(message.getSender());
        GameMessage gameMessage = new GameMessage();
        gameMessage.setRoomId(message.getRoomId());
        gameMessage.setType(GameMessage.MessageType.PRECHECK);
        gameMessage.setSender(message.getSender());
        gameMessage.setContent(messageContent);
        messagingTemplate.convertAndSend("/sub/game/" + message.getRoomId(), gameMessage);
    }

    private void draw(GameMessage message)  throws JsonProcessingException {
        String messageContent = preTurn.cardDrawIntiator(message.getSender());
        GameMessage gameMessage = new GameMessage();
        if (messageContent.contains("endDraw")) {
            gameMessage.setType(GameMessage.MessageType.ENDDRAW);
        } else {gameMessage.setType(GameMessage.MessageType.DRAW);}
        gameMessage.setRoomId(message.getRoomId());
        gameMessage.setSender(message.getSender());
        gameMessage.setContent(messageContent);
        messagingTemplate.convertAndSend("/sub/game/" + message.getRoomId(), gameMessage);
    }

    private void select(GameMessage message) throws JsonProcessingException {
        CardSelectRequestDto requestDto = objectBuilder.drawnCards(message.getContent());
        String messageContent = preTurn.cardDrawResponse(message.getSender(), requestDto);
        GameMessage gameMessage = new GameMessage();
        if (messageContent.contains("endDraw")) {
            gameMessage.setType(GameMessage.MessageType.ENDDRAW);
        } else {gameMessage.setType(GameMessage.MessageType.SELECT);}
        gameMessage.setRoomId(message.getRoomId());
        gameMessage.setSender(message.getSender());
        gameMessage.setContent(messageContent);
        messagingTemplate.convertAndSend("/sub/game/" + message.getRoomId(), gameMessage);
    }

    private void turnCheck(GameMessage message) throws JsonProcessingException {
        String messageContent = preTurn.actionTurnCheck(message.getSender());
        GameMessage gameMessage = new GameMessage();
        gameMessage.setRoomId(message.getRoomId());
        gameMessage.setSender(message.getSender());
        gameMessage.setType(GameMessage.MessageType.TURNCHECK);
        gameMessage.setContent(messageContent);
        messagingTemplate.convertAndSend("/sub/game/" + message.getRoomId(), gameMessage);
    }

    private void useCard(GameMessage message) throws JsonProcessingException {
        UseCardDto dto = objectBuilder.cardUse(message.getContent());
        String messageContent = actionTurn.cardMoveProcess(message.getSender(), dto);
        GameMessage gameMessage = new GameMessage();
        gameMessage.setRoomId(message.getRoomId());
        gameMessage.setSender(message.getSender());
        gameMessage.setType(GameMessage.MessageType.USECARD);
        gameMessage.setContent(messageContent);
        messagingTemplate.convertAndSend("/sub/game/" + message.getRoomId(), gameMessage);
    }

    private void discard(GameMessage message) throws JsonProcessingException {
        DiscardDto dto = objectBuilder.discard(message.getContent());
        String messageContent = actionTurn.discard(message.getSender(), dto);
        GameMessage gameMessage = new GameMessage();
        gameMessage.setRoomId(message.getRoomId());
        gameMessage.setSender(message.getSender());
        gameMessage.setType(GameMessage.MessageType.DISCARD);
        gameMessage.setContent(messageContent);
        messagingTemplate.convertAndSend("/sub/game/" + message.getRoomId(), gameMessage);
    }

    private void endCheck(GameMessage message) throws JsonProcessingException {
        String messageContent = endTurn.EndTrunCheck(message.getSender());
        GameMessage gameMessage = new GameMessage();
        gameMessage.setRoomId(message.getRoomId());
        gameMessage.setSender(message.getSender());
        gameMessage.setType(GameMessage.MessageType.ENDTURN);
        gameMessage.setContent(messageContent);
        messagingTemplate.convertAndSend("/sub/game/" + message.getRoomId(), gameMessage);
    }

    private void endGame(GameMessage message) throws JsonProcessingException {
        String messageContent = endGame.gameEnd(message.getRoomId());
        GameMessage gameMessage = new GameMessage();
        gameMessage.setRoomId(message.getRoomId());
        gameMessage.setSender(message.getSender());
        gameMessage.setType(GameMessage.MessageType.ENDGAME);
        gameMessage.setContent(messageContent);
        messagingTemplate.convertAndSend("/sub/game/" + message.getRoomId(), gameMessage);
    }


}