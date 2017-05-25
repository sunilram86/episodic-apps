package com.example.episodicshows.episodes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Entity(name ="episodes")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Episode {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "show_id")
    @JsonIgnore
    private Long showId;

    @Column(name="season_number")
    private int seasonNumber;

    @Column(name="episode_number")
    private int episodeNumber;

    @Transient
    @JsonProperty("title")
    private String getTitle(){
        return "S" + this.seasonNumber + " " + "E" + this.episodeNumber;
    }

    @Transient
    @JsonIgnore
    private String title;
}
