package com.example.demo.repository.card;

import com.example.demo.model.card.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCardRepository extends JpaRepository<UserCard, Integer> {

}
