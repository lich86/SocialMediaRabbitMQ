package com.chervonnaya.subscriptionsservice.repository;

import com.chervonnaya.subscriptionsservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
