package com.nuc.zp.mongdb.dao;

import com.nuc.zp.mongdb.domain.User;

public interface UserRepository {
    public void saveUser(User user);
    public User findUserByUserName(String userName);
    public long updateUser(User user);
    public void deleteUserById(Long id);
}

