package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CredentialServiceImpl implements CredentialService {

    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private HashService hashService;

    @Autowired
    private UserService userService;

    @Override
    public Integer save(Credential credential, Integer userid) {
        String skeleton = RandomStringUtils.random(16, true, true);
        credential.setSkeleton(skeleton);
        String encryptPW = encryptionService.encryptValue(credential.getPassword(), skeleton);
        credential.setPassword(encryptPW);
        return credentialMapper.addCredential(credential, userid);
    }

    @Override
    public Integer update(Credential credential) {
        String skeleton = RandomStringUtils.random(16, true, true);
        credential.setSkeleton(skeleton);
        String encryptPW = encryptionService.encryptValue(credential.getPassword(), skeleton);
        credential.setPassword(encryptPW);
        return credentialMapper.update(credential);
    }

    @Override
    public Credential getById(Integer credentialid) {
        return credentialMapper.findById(credentialid);
    }

    @Override
    public void deleteById(Integer credentialid) {
        credentialMapper.delete(credentialid);
    }

    @Override
    public List<Credential> getAllByUserid(Integer userid) {
        return credentialMapper.findAllByUserid(userid);
    }

    @Override
    public String getDecryptedPW(Integer credentialid) {
        Credential credential = getById(credentialid);
        return encryptionService.decryptValue(credential.getPassword(), credential.getSkeleton());
    }

}
