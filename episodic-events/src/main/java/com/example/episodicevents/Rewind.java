package com.example.episodicevents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rewind extends Event{

    private Data data;

    public Rewind(String id,String type, int userId, int showId, int episodeId, String createdAt, Data data) {
        super(id,type,userId, showId, episodeId,createdAt);
        this.data = data;
    }
}
