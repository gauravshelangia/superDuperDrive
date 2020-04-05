package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private FileServiceImpl fileService;


    public User register(User user) {
        String encryptedPW = encoder.encode(user.getPassword());
        user.setPassword(encryptedPW); // only persist encoded password
        userMapper.register(user);
        return user;
    }

    @Override
    public User getByUserName(String userName) {
        User user = userMapper.findByUsername(userName);
        return user;
    }

    @Override
    public User getByUserid(Integer userid) {
        return userMapper.findOne(userid);
    }
}
