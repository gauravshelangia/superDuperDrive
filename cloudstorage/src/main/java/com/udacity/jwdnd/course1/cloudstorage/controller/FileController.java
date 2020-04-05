package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/file-upload", method = RequestMethod.POST)
    public ModelAndView upload(@ModelAttribute("SpringWeb") MultipartFile fileUpload, HttpSession session, ModelMap model){
        User user = (User) session.getAttribute("user");
        // validate file upload request
        if (fileUpload.isEmpty()){
            model.addAttribute("fileUploadEmpty", true);
            return new ModelAndView("redirect:/", model);
        }

        fileService.upload(fileUpload, user.getUserid());
        model.addAttribute("fileUploadSuccess", true);
        model.addAttribute("files",fileService.getAllFilesByUserId(user.getUserid()));
        return new ModelAndView("redirect:/", model);
    }

    @RequestMapping(value = "/file-delete/{fileid}")
    public ModelAndView delete(HttpSession session, ModelMap modelMap, @PathVariable("fileid") Integer fileid){
        fileService.delete(fileid);
        User user = (User) session.getAttribute("user");
        modelMap.addAttribute("files", fileService.getAllFilesByUserId(user.getUserid()));
        return new ModelAndView("home", modelMap);
    }

    @RequestMapping(value = "/file/view/{fileid}")
    public void showFile( HttpServletResponse response, @PathVariable("fileid") Integer fileid) throws IOException {
        File file = fileService.getById(fileid);
        IOUtils.copy(new ByteArrayInputStream(file.getFiledata()), response.getOutputStream());
    }

    @RequestMapping(value = "/file/download/{fileid}")
    public ResponseEntity<Resource> downloadFile(HttpServletRequest request, @PathVariable("fileid") Integer fileid){
        File file = fileService.getById(fileid);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        ByteArrayResource resource = new ByteArrayResource(file.getFiledata());

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.getFilesize())
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .body(resource);
    }
}
