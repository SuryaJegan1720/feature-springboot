package com.training.first.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.first.model.User;


public interface UserRepos extends JpaRepository<User, Integer> {

}
