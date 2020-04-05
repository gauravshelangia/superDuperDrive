package com.udacity.jwdnd.course1.cloudstorage.controller;

import javax.servlet.http.HttpSession;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
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

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    private NotesService notesService;

    @Autowired
    private CredentialService credentialService;

    @RequestMapping(value = {"/", "/login", "/home"})
    public String login(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (session.getAttribute("user") == null) {
            return "login";
        }
        User savedUser = userService.getByUserName(user.getUserName());

        model.addAttribute("files", fileService.getAllFilesByUserId(savedUser.getUserid()));
        model.addAttribute("credentials", credentialService.getAllByUserid(savedUser.getUserid()));
        model.addAttribute("notes", notesService.getAllByUserid(savedUser.getUserid()));
        return "home";
    }

    @RequestMapping(value = {"/home"})
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (session.getAttribute("user") == null) {
            model.addAttribute("notLoggedIn", true);
            return "login";
        }
        User savedUser = userService.getByUserName(user.getUserName());

        model.addAttribute("files", fileService.getAllFilesByUserId(savedUser.getUserid()));
        model.addAttribute("credentials", credentialService.getAllByUserid(savedUser.getUserid()));
        model.addAttribute("notes", notesService.getAllByUserid(savedUser.getUserid()));
        return "home";
    }

    @RequestMapping("/register")
    public String register() {
        return "/signup";
    }

}