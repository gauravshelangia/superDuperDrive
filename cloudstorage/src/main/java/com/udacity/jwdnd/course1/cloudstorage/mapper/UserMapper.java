package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from users")
    List<User> findAll();


    @Select("select * from users where users.userid = #{id}")
    User findOne(@Param("id") Integer id);

    @Select("select * from users where users.username = #{userName}")
    User findByUsername(String userName);


    @Insert("insert into users (username,password,firstname,lastname) values (#{user.userName},#{user.password},#{user.firstName},#{user.lastName})")
    Integer register(@Param("user") User user);

}