package com.example.episodicshows.viewings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;


@Entity (name ="viewings")
@Data
public class Viewing {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "show_id")

    private Long showId;

    @Column(name = "episode_id")
    private Long episodeId;

    private Date updatedAt;

    private int timecode;

}
