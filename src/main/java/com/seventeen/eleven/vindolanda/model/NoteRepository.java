package com.seventeen.eleven.vindolanda.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Note findByName(String name);

}
