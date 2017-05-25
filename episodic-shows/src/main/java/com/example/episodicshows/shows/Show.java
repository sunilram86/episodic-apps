package com.example.episodicshows.shows;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name ="shows")
@Data
public class Show {

    @Id
    @GeneratedValue
    private long id;

    private String name;
}
