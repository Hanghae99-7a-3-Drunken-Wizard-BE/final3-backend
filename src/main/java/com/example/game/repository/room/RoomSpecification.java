package com.example.game.repository.room;

import com.example.game.model.room.Room;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RoomSpecification {

    public static Specification<Room> equalTitle(String keyword) {
        return new Specification<Room>() {
            @Override
            public Predicate toPredicate(Root<Room> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("title"),"%" + keyword + "%");
            }
        };
    }

    public static Specification<Room> equalBattleType(String battleType) {
        return new Specification<Room>() {
            @Override
            public Predicate toPredicate(Root<Room> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("battleType"), battleType);
            }
        };
    }
}
