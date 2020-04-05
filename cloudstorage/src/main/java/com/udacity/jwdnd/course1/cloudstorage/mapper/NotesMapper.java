package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Select("select * from notes where notes.noteid = #{id}")
    Notes findNoteById(@Param("id") Integer id);

    @Select("select * from notes where notes.userid = #{id}")
    List<Notes> findNoteByUserId(@Param("id") Integer id);


    @Insert("insert into notes(notetitle,notedescription, userid ) VALUES (#{note.notetitle}, #{note.notedescription}, #{userid})")
    Integer saveNote(@Param("note") Notes note, @Param("userid") Integer userid);

    @Update("UPDATE notes SET notetitle = #{note.notetitle}, notedescription = #{note.notedescription} WHERE noteid = #{note.noteid}")
    Integer updateNote(@Param("note") Notes note);

    @Delete("DELETE FROM notes WHERE noteid = #{noteid}")
    Integer deleteNote(@Param("noteid")Integer noteid);
}