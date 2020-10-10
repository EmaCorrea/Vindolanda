package com.seventeen.eleven.vindolanda.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Note")
public class Note {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    private String text;

}
