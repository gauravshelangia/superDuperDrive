package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

import java.util.List;

public interface CredentialService {

    Integer save(Credential credential, Integer userid);

    Integer update(Credential credential);

    Credential getById(Integer credentialid);

    void deleteById(Integer credentialid);

    List<Credential> getAllByUserid(Integer userid);

    String getDecryptedPW(Integer credentialid);
}
