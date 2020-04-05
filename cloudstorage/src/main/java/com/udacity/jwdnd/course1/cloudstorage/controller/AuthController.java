package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper usersMapper;

    @Autowired
    private NotesMapper notesMapper;

    @RequestMapping(path = "/authenticate", method = RequestMethod.GET)
    public String authenticate(Principal principal) {
        return "home";
    }

    @RequestMapping("/login-perform")
    public ModelAndView login(@ModelAttribute("SpringWeb") User user, HttpSession session, ModelMap model) {

        if(user.getUsername() != null) {
            System.err.println(user.getUsername());
            User savedUser = usersMapper.getByUsername(user);

            if (ObjectUtils.isEmpty(savedUser)) {
                model.addAttribute("invalidLogin", true);
                return new ModelAndView("login", model);
            }

            if (BCrypt.checkpw(user.getPassword(), savedUser.getPassword())) {
                session.setAttribute("user", savedUser);
                return new ModelAndView("redirect:/login", model);
            }
        }

        model.addAttribute("invalidLogin", true);
        return new ModelAndView("login", model);
    }

    @RequestMapping("/logout.do")
    public ModelAndView logout(HttpSession session, ModelMap model) {
        session.setAttribute("user", null);
        model.addAttribute("logoutSuccess", true);
        return new ModelAndView("login", model);
    }

    @RequestMapping(path = "/register-perform", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("SpringWeb") User user, HttpServletResponse res, ModelMap modelMap) {

        if (user == null) {
            res.setStatus(400);
        }
        user = userService.register(user);
        modelMap.addAttribute("signupSuccess", true);
        return new ModelAndView("signup", modelMap);
    }

}