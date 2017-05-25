package com.example.episodicevents;

import lombok.*;


@lombok.Data
public class ProgressMessage {
    private Long userId;
    private Long episodeId;
    private String createdAt;
    private int offset;
}
