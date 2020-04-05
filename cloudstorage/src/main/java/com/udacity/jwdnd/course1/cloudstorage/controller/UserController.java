package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper usersMapper;
    @Autowired
    private NotesMapper notesMapper;

    @GetMapping("/all")
    public List<User> getAll() {

        List<User> users = usersMapper.findAll();
        System.err.println("*****************" + users);
        return usersMapper.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Integer id) {
        User user = usersMapper.findOne(id);
        user.setNotes(notesMapper.findNoteByUserId(id));
        return user;
    }

}
