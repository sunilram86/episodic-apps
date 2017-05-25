package com.example.episodicshows.users;

import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity (name ="users")
@Data
public class User {

    @Id
    @GeneratedValue
    private long id;

    private String email;

}
