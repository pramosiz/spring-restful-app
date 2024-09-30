package com.tutorial.userservice.repository.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.userservice.repository.domains.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
