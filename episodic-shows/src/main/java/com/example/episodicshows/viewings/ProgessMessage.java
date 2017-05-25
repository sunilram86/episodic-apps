package com.example.episodicshows.viewings;

import lombok.Data;

import java.util.Date;

@Data
public class ProgessMessage {

    private Long userId;
    private Long episodeId;
    private Date createdAt;
    private int offset;


}
