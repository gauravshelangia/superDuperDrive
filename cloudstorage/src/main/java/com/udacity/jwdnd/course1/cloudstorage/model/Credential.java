package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

@Data
public class Credential {

    private int id;
    private String url;
    private String username;
    private String key;
    private String password;

}