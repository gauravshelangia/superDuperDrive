package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.File;

import java.util.List;

public interface FileService {

    File upload(File file);

    List<File> getAllFilesByUserId(int userId);

    File getById (int fileId);

    void delete(int fileId);
}
