package com.udacity.jwdnd.course1.cloudstorage.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Data;

import org.springframework.security.core.GrantedAuthority;

@Data
public class User {

    private int userid;
    private String userName;
    private String salt;
    private String password;
    private String firstName;
    private String lastName;

    private String role;
    private List <File> files;
    private List <Notes> notes;

    public Collection<GrantedAuthority> getAuthorities() {
        //make everyone ROLE_USER
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        GrantedAuthority grantedAuthority = new GrantedAuthority() {
            //anonymous inner type
            public String getAuthority() {
                return "ROLE_USER";
            }
        };
        grantedAuthorities.add(grantedAuthority);
        return grantedAuthorities;
    }


}
