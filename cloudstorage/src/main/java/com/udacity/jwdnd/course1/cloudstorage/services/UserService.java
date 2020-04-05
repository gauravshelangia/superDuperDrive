package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     * Create new user
     * @param user
     * @return
     */
    User register(User user);

    User getByUserName(String userName);

    User getByUserid(Integer userid);
}