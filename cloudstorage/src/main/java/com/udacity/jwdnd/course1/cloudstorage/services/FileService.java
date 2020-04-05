package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    File upload(MultipartFile file, Integer userId);

    List<File> getAllFilesByUserId(int userId);

    File getById (Integer fileId);

    void delete(Integer fileId);

    MultipartFile download(Integer fileid);
}
