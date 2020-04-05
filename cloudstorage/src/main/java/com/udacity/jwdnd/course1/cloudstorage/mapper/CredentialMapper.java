package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("select * from credentials where credentials.credentialid = #{id}")
    Credential findById(@Param("id") Integer id);

    @Select("select * from credentials where credentials.userid = #{userid}")
    List<Credential> findAllByUserid(@Param("userid") Integer userid);

    @Insert("insert into credentials(url,username,skeleton,password, userid ) VALUES (#{credential.url}, #{credential.username},#{credential.skeleton},#{credential.password}, #{userid})")
    Integer addCredential(@Param("credential") Credential credential, @Param("userid") Integer userid);

    @Update("UPDATE credentials SET url = #{credential.url}, username = #{credential.username}, skeleton = #{credential.skeleton}, password = #{credential.password} WHERE credentialid = #{credential.credentialid}")
    Integer update(@Param("credential") Credential credential);

    @Delete("DELETE FROM credentials WHERE credentialid = #{credentialid}")
    Integer delete(@Param("credentialid") Integer credentialid);

}