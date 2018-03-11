package com.getitdone.services.core;

import com.getitdone.services.domain.User;

import java.util.List;

public interface IUserService {
     String createUser(User user);
     User getUser(String id);
     List<User> getAllUsers(int page, int size);
}
