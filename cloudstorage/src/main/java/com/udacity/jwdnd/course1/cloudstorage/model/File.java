package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

@Data
public class File {

    private int fileid;
    private String filename;
    private String contenttype;
    private Long filesize;
    private byte[] filedata;
    private Integer userid;

}