package com.example.episodicevents;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface EventsRepository extends MongoRepository<Event, Long> {

    @Query("{}")
    List<Event> findRecent(PageRequest pageRequest);
}
