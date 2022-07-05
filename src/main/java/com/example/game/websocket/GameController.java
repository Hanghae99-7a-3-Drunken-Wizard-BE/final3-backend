package com.example.game.websocket;

import com.example.game.Game.Game;
import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.Game.gameDataDto.ObjectBuilder;
import com.example.game.Game.gameDataDto.request.CardSelectRequestDto;
import com.example.game.Game.gameDataDto.request.UseCardDto;
import com.example.game.Game.gameDataDto.subDataDto.DiscardDto;
import com.example.game.Game.service.GameStarter;
import com.example.game.Game.turn.ActionTurn;
import com.example.game.Game.turn.EndTurn;
import com.example.game.Game.turn.PreTurn;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class GameController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final GameStarter gameStarter;
    private final JsonStringBuilder jsonStringBuilder;
    private final ObjectBuilder objectBuilder;
    private final PreTurn preTurn;
    private final ActionTurn actionTurn;
    private final EndTurn endTurn;

    @MessageMapping("/chat/game/{roomId}")
    public void gameMessageProxy(@Payload GameMessage message) throws JsonProcessingException {
        if (GameMessage.MessageType.START.equals(message.getType())) {
            gameStarter(message);
        }
        if (GameMessage.MessageType.PRECHECK.equals(message.getType())) {
            precheck(message);
        }
        if (GameMessage.MessageType.DRAW.equals(message.getType())) {
            draw(message);
        }
        if (GameMessage.MessageType.SELECT.equals(message.getType())) {
            select(message);
        }
        if (GameMessage.MessageType.TURNCHECK.equals(message.getType())) {
            turnCheck(message);
        }
        if (GameMessage.MessageType.USECARD.equals(message.getType())) {
            useCard(message);
        }
        if (GameMessage.MessageType.DISCARD.equals(message.getType())) {
            discard(message);
        }
        if (GameMessage.MessageType.ENDCHECK.equals(message.getType())) {
            endCheck(message);
        }
        if (GameMessage.MessageType.ENDGAME.equals(message.getType())) {
            endGame(message);
        }
    }

    private void gameStarter(GameMessage message) throws JsonProcessingException {
        Game game = gameStarter.createGameRoom(message.getRoomId());
        String messageContent = jsonStringBuilder.gameStarter(game);
        GameMessage gameMessage = new GameMessage();
        gameMessage.setRoomId(message.getRoomId());
        gameMessage.setSender(message.getSender());
        gameMessage.setType(GameMessage.MessageType.START);
        gameMessage.setContent(messageContent);
        messagingTemplate.convertAndSend("/sub/chat/game/" + message.getRoomId(), gameMessage);
    }

    private void precheck(GameMessage message) throws JsonProcessingException {
        String messageContent = preTurn.preturnStartCheck(message.getSender());
        GameMessage gameMessage = new GameMessage();
        gameMessage.setRoomId(message.getRoomId());
        gameMessage.setType(GameMessage.MessageType.PRECHECK);
        gameMessage.setContent(messageContent);
        messagingTemplate.convertAndSend("/sub/chat/game/" + message.getRoomId(), gameMessage);
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
        messagingTemplate.convertAndSend("/sub/chat/game/" + message.getRoomId(), gameMessage);
    }

    private void select(GameMessage message) throws JsonProcessingException {
        CardSelectRequestDto requestDto = objectBuilder.drawnCards(message.getContent());
        String messageContent = preTurn.cardDrawResponse(requestDto);
        GameMessage gameMessage = new GameMessage();
        if (messageContent.contains("endDraw")) {
            gameMessage.setType(GameMessage.MessageType.ENDDRAW);
        } else {gameMessage.setType(GameMessage.MessageType.SELECT);}
        gameMessage.setRoomId(message.getRoomId());
        gameMessage.setSender(message.getSender());
        gameMessage.setContent(messageContent);
        messagingTemplate.convertAndSend("/sub/chat/game/" + message.getRoomId(), gameMessage);
    }

    private void turnCheck(GameMessage message) throws JsonProcessingException {
        String messageContent = preTurn.actionTurnCheck(message.getSender());
        GameMessage gameMessage = new GameMessage();
        gameMessage.setRoomId(message.getRoomId());
        gameMessage.setSender(message.getSender());
        gameMessage.setType(GameMessage.MessageType.TURNCHECK);
        gameMessage.setContent(messageContent);
        messagingTemplate.convertAndSend("/sub/chat/game/" + message.getRoomId(), gameMessage);
    }

    private void useCard(GameMessage message) throws JsonProcessingException {
        UseCardDto dto = objectBuilder.cardUse(message.getContent());
        String messageContent = actionTurn.cardMoveProcess(dto);
        GameMessage gameMessage = new GameMessage();
        gameMessage.setRoomId(message.getRoomId());
        gameMessage.setSender(message.getSender());
        gameMessage.setType(GameMessage.MessageType.USECARD);
        gameMessage.setContent(messageContent);
        messagingTemplate.convertAndSend("/sub/chat/game/" + message.getRoomId(), gameMessage);
    }

    private void discard(GameMessage message) throws JsonProcessingException {
        DiscardDto dto = objectBuilder.discard(message.getContent());
        String messageContent = actionTurn.discard(dto);
        GameMessage gameMessage = new GameMessage();
        gameMessage.setRoomId(message.getRoomId());
        gameMessage.setSender(message.getSender());
        gameMessage.setType(GameMessage.MessageType.DISCARD);
        gameMessage.setContent(messageContent);
        messagingTemplate.convertAndSend("/sub/chat/game/" + message.getRoomId(), gameMessage);
    }

    private void endCheck(GameMessage message) throws JsonProcessingException {
        String messageContent = endTurn.EndTrunCheck(message.getSender());
        GameMessage gameMessage = new GameMessage();
        gameMessage.setRoomId(message.getRoomId());
        gameMessage.setSender(message.getSender());
        gameMessage.setType(GameMessage.MessageType.ENDCHECK);
        gameMessage.setContent(messageContent);
        messagingTemplate.convertAndSend("/sub/chat/game/" + message.getRoomId(), gameMessage);
    }

    private void endGame(GameMessage message) {
    }


}