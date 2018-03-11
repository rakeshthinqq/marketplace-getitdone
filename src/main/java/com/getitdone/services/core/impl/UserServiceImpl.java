package com.getitdone.services.core.impl;

import com.getitdone.services.core.IUserService;
import com.getitdone.services.domain.User;
import com.getitdone.services.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    UserRepository repository;

    @Override
    public String createUser(User user) {
        User savedUser = repository.save(user);
        return savedUser.getId();
    }

    @Override
    public User getUser(String id) {
        Optional<User> user = repository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        return null;
    }

    @Override
    public List<User> getAllUsers(int page, int size) {
//        Page<User> all = repository.findAll(PageRequest.of(page, size));
//        if(all != null) {
//            return all.getContent();
//        }
        List<User> all = repository.findAll();
        return all;
    }
}
