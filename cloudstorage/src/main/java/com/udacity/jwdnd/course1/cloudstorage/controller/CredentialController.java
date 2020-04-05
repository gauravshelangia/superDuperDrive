package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class CredentialController {
    @Autowired
    private CredentialService credentialService;

    @RequestMapping(value = "/add-credential", method = RequestMethod.POST)
    public ModelAndView addCredential(@ModelAttribute("SpringWeb") Credential credential, HttpSession session, ModelMap model){
        User user = (User) session.getAttribute("user");
        if (!ObjectUtils.isEmpty(credential.getCredentialid())){
            credentialService.update(credential);
        } else {
            credentialService.save(credential, user.getUserid());
        }
        model.addAttribute("credAdded","Credential save/update successfully!!");
        return new ModelAndView("redirect:/", model);
    }

    @RequestMapping(value = "/delete-credential/{credentialid}")
    public ModelAndView deleteCredential(@PathVariable("credentialid") Integer credentialid, HttpSession session,
                                         ModelMap model){
        User user = (User) session.getAttribute("user");
        credentialService.deleteById(credentialid);
        model.addAttribute("notes", credentialService.getAllByUserid(user.getUserid()));
        model.addAttribute("credentialDeleted", "Notes Deleted successfully!!");
        return new ModelAndView("redirect:/", model);
    }

    @RequestMapping(value = "/decrypt-credential/{credentialid}")
    @ResponseBody
    public List<String> decryptCredential(@PathVariable("credentialid") Integer credentialid, HttpSession session,
                                          ModelMap model){
        return Arrays.asList(credentialService.getDecryptedPW(credentialid));
    }

}
