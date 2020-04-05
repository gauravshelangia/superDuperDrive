package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesMapper notesMapper;

    @Override
    public Integer save(Notes note, Integer userid) {
        return notesMapper.saveNote(note, userid);
    }

    @Override
    public Integer update(Notes note) {
        return  notesMapper.updateNote(note);
    }

    @Override
    public Notes getById(Integer noteid) {
        return notesMapper.findNoteById(noteid);
    }

    @Override
    public void deleteById(Integer noteid) {
        notesMapper.deleteNote(noteid);
    }

    @Override
    public List<Notes> getAllByUserid(Integer userid) {
        return notesMapper.findNoteByUserId(userid);
    }


}
