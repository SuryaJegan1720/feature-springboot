package com.training.first.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.first.model.User;
import com.training.first.repos.UserRepos;



@Service
public class UserService {

	@Autowired
	UserRepos userRepository;
	
	public List<User> listAllUser() {
		return userRepository.findAll();
	}
	
	public User getUser(Integer id) {
        return userRepository.findById(id).get();
    }
	
	public void saveUser(User user) {
        userRepository.save(user);
    }
	
	public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
	
	

}
