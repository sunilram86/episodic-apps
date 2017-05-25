package com.example.episodicevents;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@JsonPropertyOrder({ "id", "type","userId","showId","episodeId","createdAt"})
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Play.class, name = "play"),
        @JsonSubTypes.Type(value = Pause.class, name = "pause"),
        @JsonSubTypes.Type(value = FastForward.class, name = "fastForward"),
        @JsonSubTypes.Type(value = Rewind.class, name = "rewind"),
        @JsonSubTypes.Type(value = Progress.class, name = "progress"),
        @JsonSubTypes.Type(value = Scrub.class, name = "scrub"),


})
public class Event {

    @Id
    private String id;
    private String type;
    private Integer userId;
    private Integer showId;
    private Integer episodeId;
    private String createdAt;

}



