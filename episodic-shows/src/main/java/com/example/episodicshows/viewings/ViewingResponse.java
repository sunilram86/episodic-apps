package com.example.episodicshows.viewings;


import com.example.episodicshows.episodes.Episode;
import com.example.episodicshows.shows.Show;
import lombok.Data;

import java.util.Date;

@Data
public class ViewingResponse {

    private Show show;

    private Episode episode;

    private String updatedAt;

    private int timecode;

}
