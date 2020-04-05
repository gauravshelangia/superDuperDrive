package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Override
    public File upload(MultipartFile multipartFile, Integer userId) {
        File file = new File();
        try {
            file.setContenttype(multipartFile.getContentType());
            file.setFiledata(multipartFile.getBytes());
            file.setFilename(multipartFile.getOriginalFilename());
            file.setFilesize(multipartFile.getSize());
            file.setUserid(userId);
        } catch (IOException e) {
            //TODO add proper  logging here
            e.printStackTrace();
        }
        fileMapper.save(file);
        System.out.println(file.getFilename());
        return file;
    }

    @Override
    public List<File> getAllFilesByUserId(int userId) {
        return fileMapper.findAllByUserId(userId);
    }

    @Override
    public File getById(Integer fileId) {
        return fileMapper.findById(fileId);
    }

    @Override
    public void delete(Integer fileId) {
        fileMapper.deleteById(fileId);
    }

    @Override
    public MultipartFile download(Integer fileid){
        return null;
    }


}
