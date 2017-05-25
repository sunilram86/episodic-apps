package com.example.episodicshows.viewings;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ViewingRepository extends CrudRepository<Viewing, Long> {


    List<Viewing> findByUserIdOrderByUpdatedAtDesc(Long userId);

    Viewing findByUserIdAndShowId(Long userID , Long showID );


}
