package com.example.game.websocket;

import com.example.game.Game.h2Package.Game;
import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.Game.gameDataDto.ObjectBuilder;
import com.example.game.Game.gameDataDto.request.CardSelectRequestDto;
import com.example.game.Game.gameDataDto.request.UseCardDto;
import com.example.game.Game.gameDataDto.subDataDto.DiscardDto;
import com.example.game.Game.h2Package.GameRoom;
import com.example.game.Game.repository.GameRepository;
import com.example.game.Game.repository.GameRoomRepository;
import com.example.game.Game.service.GameStarter;
import com.example.game.Game.turn.ActionTurn;
import com.example.game.Game.turn.EndGame;
import com.example.game.Game.turn.EndTurn;
import com.example.game.Game.turn.PreTurn;
import com.example.game.dto.request.SwitchingPositionRequestDto;
import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class GameController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final GameStarter gameStarter;
    private final GameRepository gameRepository;
    private final GameRoomRepository gameRoomRepository;
    private final GameRoomService gameRoomService;
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
        if (GameMessage.MessageType.USESPECIAL.equals(message.getType())) {
            System.out.println("여기에 들어오나" + message.getType());
            useSpecial(message);
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

    @MessageMapping("/wroom/{roomId}")
    public void roomMessageProxy(@Payload GameMessage message) throws JsonProcessingException {
        System.out.println("여기에 들어오나 메시지매핑 메서드");
        if (GameMessage.MessageType.JOIN.equals(message.getType())) {
            System.out.println("여기에 들어오나" + message.getType());
            join(message);
        }

        if (GameMessage.MessageType.UPDATE.equals(message.getType())) {
            System.out.println("여기에 들어오나" + message.getType());
            update(message);
        }
        if (GameMessage.MessageType.READY.equals(message.getType())) {
            System.out.println("여기에 들어오나" + message.getType());
            ready(message);
        }

        if (GameMessage.MessageType.SWITCHING.equals(message.getType())) {
            System.out.println("여기에 들어오나" + message.getType());
            switchingPosition(message);
        }
    }

    private void join(GameMessage message) throws JsonProcessingException {
        String roomId = message.getRoomId();
        GameRoom room = gameRoomRepository.findByRoomId(roomId);

        String userListMessage = jsonStringBuilder.gameRoomResponseDtoJsonBuilder(room);
        GameMessage gameMessage = new GameMessage();
        gameMessage.setRoomId(roomId);
        gameMessage.setSender(message.getSender());
        gameMessage.setContent(userListMessage);
        gameMessage.setType(GameMessage.MessageType.UPDATE);
        messagingTemplate.convertAndSend("/sub/wroom/" + roomId, gameMessage);
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
        String messageContent = preTurn.cardDrawInitiator(message.getSender());
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
        System.out.println("여기에 들어오나 셀렉트 메서드");
        System.out.println(message.getContent() + " 이상 콘텐츠로 들어오는 스트링 내용");
        CardSelectRequestDto requestDto = objectBuilder.drawnCards(message.getContent());
        String messageContent = preTurn.cardDrawResponse(message.getSender(), requestDto);
        GameMessage gameMessage = new GameMessage();
        if (messageContent.contains("endDraw")) {
            gameMessage.setType(GameMessage.MessageType.ENDDRAW);
        } else {gameMessage.setType(GameMessage.MessageType.SELECT);}
        gameMessage.setRoomId(message.getRoomId());
        gameMessage.setSender(message.getSender());
        gameMessage.setContent(messageContent);
        System.out.println(messageContent);
        System.out.println(message.getRoomId());
        System.out.println(message.getSender());
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
        if (messageContent.equals("마나부족") || messageContent.equals("침묵됨")) {
            String newMessageContent = jsonStringBuilder.cardUseFailDtoJsonBuilder(false);
            gameMessage.setContent(newMessageContent);
            gameMessage.setType(GameMessage.MessageType.USEFAIL);
            messagingTemplate.convertAndSend("/sub/game/" + message.getRoomId(), gameMessage);
        } else {
            gameMessage.setContent(messageContent);
            gameMessage.setType(GameMessage.MessageType.USECARD);
            messagingTemplate.convertAndSend("/sub/game/" + message.getRoomId(), gameMessage);
        }
    }

    private void useSpecial(GameMessage message) {
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
        String messageContent = endTurn.EndTurnCheck(message.getSender());
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

    private void update(GameMessage message) throws JsonProcessingException {
        String roomId = message.getRoomId();
        GameRoom room = gameRoomRepository.findByRoomId(roomId);
        if (room != null) {
            String userListMessage = jsonStringBuilder.gameRoomResponseDtoJsonBuilder(room);
            GameMessage gameMessage = new GameMessage();
            gameMessage.setRoomId(roomId);
            gameMessage.setSender(message.getSender());
            gameMessage.setContent(userListMessage);
            gameMessage.setType(GameMessage.MessageType.UPDATE);
            messagingTemplate.convertAndSend("/sub/wroom/" + roomId, gameMessage);
        }
    }

    private void ready(GameMessage message) throws JsonProcessingException {
        String roomId = message.getRoomId();
        GameRoom room = gameRoomRepository.findByRoomId(roomId);
        if (room != null) {
            if (room.getPlayer1() != null) {
                if (room.getPlayer1().equals(message.getSender()) ||
                        room.getPlayer1().equals(message.getSender() * -1)) {
                    room.setPlayer1(room.getPlayer1() * -1);
                }
            }
            if (room.getPlayer2() != null) {
                if (room.getPlayer2().equals(message.getSender()) ||
                        room.getPlayer2().equals(message.getSender() * -1)) {
                    room.setPlayer2(room.getPlayer2() * -1);
                }
            }
            if (room.getPlayer3() != null) {
                if (room.getPlayer3().equals(message.getSender()) ||
                        room.getPlayer3().equals(message.getSender() * -1)) {
                    room.setPlayer3(room.getPlayer3() * -1);
                }
            }
            if (room.getPlayer4() != null) {
                if (room.getPlayer4().equals(message.getSender()) ||
                        room.getPlayer4().equals(message.getSender() * -1)) {
                    room.setPlayer4(room.getPlayer4() * -1);
                }
            }
            gameRoomRepository.save(room);
            String userListMessage = jsonStringBuilder.gameRoomResponseDtoJsonBuilder(room);
            GameMessage gameMessage = new GameMessage();
            gameMessage.setRoomId(roomId);
            gameMessage.setSender(message.getSender());
            gameMessage.setContent(userListMessage);
            gameMessage.setType(GameMessage.MessageType.UPDATE);
            messagingTemplate.convertAndSend("/sub/wroom/" + roomId, gameMessage);
        }
    }

    private void switchingPosition(GameMessage message) throws JsonProcessingException {
        System.out.println("여기에 들어오나 스위칭 포지션");
        String roomId = message.getRoomId();
        GameRoom room = gameRoomRepository.findByRoomId(roomId);
        SwitchingPositionRequestDto requestDto = objectBuilder.switching(message.getContent());
        GameMessage gameMessage = new GameMessage();
        gameMessage.setRoomId(roomId);
        gameMessage.setSender(message.getSender());
        gameMessage.setContent(gameRoomService.switchingPosition(room, requestDto));
        gameMessage.setType(GameMessage.MessageType.UPDATE);
        messagingTemplate.convertAndSend("/sub/wroom/" + roomId, gameMessage);
    }


//
//    private void leave(GameMessage message) throws JsonProcessingException {
//        String roomId = message.getRoomId();
//        User user = userRepository.findById(message.getSender()).orElseThrow(
//                ()-> new NullPointerException("유저 없음"));
//        GameRoom gameRoom = gameRoomRepository.findByRoomId(roomId);
//        user.setRoomId(null);
//        userRepository.save(user);
//        List<User> userList = userRepository.findByRoomId(message.getRoomId());
//        String userListMessage = jsonStringBuilder.gameRoomResponseDtoJsonBuilder(
//                roomId, gameRoom.getRoomName(), userList);
//        GameMessage gameMessage = new GameMessage();
//        gameMessage.setRoomId(roomId);
//        gameMessage.setContent(userListMessage);
//        gameMessage.setType(GameMessage.MessageType.UPDATE);
//        messagingTemplate.convertAndSend("/sub/game/" + roomId, gameMessage);
//        if (userList.size() == 0) {
//            gameRoomRepository.delete(gameRoom);
//        }
//    }

}