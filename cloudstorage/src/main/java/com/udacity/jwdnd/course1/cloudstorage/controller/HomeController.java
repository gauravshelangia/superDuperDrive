package com.udacity.jwdnd.course1.cloudstorage.controller;

import javax.servlet.http.HttpSession;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @RequestMapping(value = {"/", "/login"})
    public String home(HttpSession session, User user, Model model) {
        if (session.getAttribute("user") == null) {
            return "login";
        }
        return "home";
    }

    @RequestMapping("/register")
    public String register() {
        return "/signup";
    }

}