package com.example.game.service;

import com.example.game.dto.roomDto.request.RoomEnterRequestDto;
import com.example.game.dto.roomDto.request.RoomRequestDto;
import com.example.game.dto.roomDto.response.EnterUserResponseDto;
import com.example.game.dto.roomDto.response.RoomResponseDto;
import com.example.game.exception.UserExceptionType;
import com.example.game.exception.UserException;
import com.example.game.model.room.EnterUser;
import com.example.game.model.room.Room;
import com.example.game.model.user.User;
import com.example.game.repository.room.EnterUserRepository;
import com.example.game.repository.room.RoomRepository;
import com.example.game.repository.room.RoomSpecification;
import com.example.game.repository.user.UserRepository;
import com.example.game.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final EnterUserRepository enterUserRepository;
    private final UserRepository userRepository;

    //방 생성
    public RoomResponseDto createRoom(RoomRequestDto requestDto, User user){

        if (roomRepository.findByTitle(requestDto.getTitle()) != null) {
            throw new IllegalArgumentException("이미 존재하는 방 이름입니다.");
        }

        if (requestDto.getTitle() == null || requestDto.getTitle().equals(" ")) {
            throw new IllegalArgumentException("방 이름을 입력해주세요.");
        }

        if (requestDto.getBattleType() == null) {
            throw new IllegalArgumentException("대전형태를 선택해주세요.");
        }

        int maxUser = requestDto.getMaxUser();
        if(maxUser < 2){
            throw new IllegalArgumentException("인원수를 선택해주세요");
        }

        Room room = Room.create(requestDto, user, maxUser);
        Room createRoom = roomRepository.save(room);
        String title = createRoom.getTitle();
        String roomId = createRoom.getRoomId();
        Long userCount = 0L;
        String battleType = createRoom.getBattleType();
        LocalDateTime createAt = createRoom.getCreatedAt();

        return new RoomResponseDto(title, roomId, userCount, maxUser, battleType, createAt, user);
    }

    //방 진입
    public List<EnterUserResponseDto> enterRoom(RoomEnterRequestDto roomEnterRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        String roomId = roomEnterRequestDto.getRoomId();
        // 받은 룸id로 내가 입장할 받을 찾음
        Room room = roomRepository.findByroomId(roomId).orElseThrow(
                () -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));

        // 들어갈 방에서 유저를 찾음
        EnterUser enterCheck = enterUserRepository.findByRoomAndUser(room, user);

//        // 내가 입장할 방이 추방당한 방인지 확인
//        BanUser banUserCheck = banUserRepository.findByRoomAndUser(room, user);
//        if (banUserCheck != null) {
//            throw new UserException(UserExceptionType.BAN_USER_ROOM);
//        }


        // 해당 방에 입장 되어있는 경우 (이미 입장한 방인경우)
        if (enterCheck != null) {
            throw new UserException(UserExceptionType.HAS_ENTER_ROOM);
        }
        //들어갈 방을 찾음? (들어온 유저가 몇명인지 체크 ?) (동일한 룸이 몇개인지 확인하여 리스트에 담아 몇명인지를 확인)
        List<EnterUser> enterUserSize = enterUserRepository.findByRoom(room);

        //방을 입장할때마다 몇명이 있는지 확인하는 로직(입장인원 초과 확인)
        int maxUser = room.getMaxUser();
        if (enterUserSize.size() > 0) {
            if (maxUser < enterUserSize.size() + 1) {
                throw new UserException(UserExceptionType.ENTER_MAX_USER);
            }
        }

        // 나가기 처리가 되지않아 내가 아직 특정방에 남아있는상태라면
        EnterUser enterUserCheck = enterUserRepository.findAllByUser(user);
        if (enterUserCheck != null) {
            enterUserRepository.delete(enterUserCheck);
        }

        Long userCount = room.getUserCount() + 1;
        //유저카운터 증가
        room.setUserCount(userCount);
        roomRepository.save(room);

        //방에 입장시 유저 한명이되는꼴
        EnterUser enterUser = new EnterUser(user, room);
        enterUserRepository.save(enterUser);

        // 방에 입장한 사람들을 리스트에 담음
        List<EnterUser> enterUsers = enterUserRepository.findByRoom(room);
        List<EnterUserResponseDto> enterRoomUsers = new ArrayList<>();
        for (EnterUser enterUser2 : enterUsers) {
            enterRoomUsers.add(new EnterUserResponseDto(
                    //방에 입장한 유저의 이름
                    enterUser2.getUser().getNickname()
            ));
        }
        return enterRoomUsers;
    }

    //방 나가기
    @Transactional
    public void quitRoom(String roomId, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        //내가입장한 방을 찾음
        Room room = roomRepository.findByroomId(roomId).orElseThrow(()-> new IllegalArgumentException("해당 방이 존재하지 않습니다."));
        //방에서 내가 입장했던 스터디룸을 찾은다음
        EnterUser enterUser =  enterUserRepository.findByRoomAndUser(room, user);
        // 그 기록을 지워서 내가 들어가있던 상태를 나간 상태로 만든다.
        enterUserRepository.delete(enterUser);
        //내가 방을 나갔으니, room의 유저 카운터를 -1 해준다
        Long userCount = room.getUserCount() - 1;
        //유저카운터 감소
        room.setUserCount(userCount);
        roomRepository.save(room);

        //userCount 체크하여 0이면 다 나간것으로 간주하여 방 폭파
        if (room.getUserCount() == 0) {
            roomRepository.delete(room);
        }
    }

    // 스터디 목록 페이지 전체 화상 채팅방 조회
    public List<Room> allReadRoom() {
        return roomRepository.findAllByOrderByCreatedAtDesc();
    }
//
//    //메인페이지 상위 8개 화상 채팅방 조회
//    @Transactional
//    public List<Room> mainPageReadRoom() {
//        return roomRepository.findTop8ByOrderByCreatedAtDesc();
//    }

    // 스터디 목록 페이징처리
    @Transactional
    public Page<Room> getPageRoom(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return roomRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    // 스터디목록 페이지 조회
    @Transactional
    public Page<Room> getTagRoom(int page, int size, String sortBy, String battleType, String keyword) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Room> spec = (root, query, criteriaBuilder) -> null;
        if(battleType != null)
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("battleType"), battleType));
        if (!keyword.equals("null"))
            spec = spec.and(RoomSpecification.equalTitle(keyword));
        return roomRepository.findAll(spec, pageable);
    }

    // 스터디룸의 입장 유저정보 조회
    public List<EnterUser> enterUsers(String roomId) {
        Room room = roomRepository.findByRoomId(roomId);
        return enterUserRepository.findByRoom(room);
    }

    // 스터디룸 검색 기능
    public Page<Room> roomSearch(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        return roomRepository.findAllByTitleContainingIgnoreCaseOrderByCreatedAtDesc(pageable, keyword);
    }
}
