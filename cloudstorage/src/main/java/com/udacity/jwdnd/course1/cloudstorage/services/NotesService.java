package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;

import java.util.List;

public interface NotesService {

    Integer save(Notes note, Integer userid);

    Integer update(Notes note);

    Notes getById(Integer noteid);

    void deleteById(Integer noteid);

    List<Notes> getAllByUserid(Integer userid);
}

