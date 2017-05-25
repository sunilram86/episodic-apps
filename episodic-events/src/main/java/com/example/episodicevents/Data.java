package com.example.episodicevents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@lombok.Data
public class Data {

    private int offset;
    private int startOffset;
    private int endOffset;
    private float speed;


}
