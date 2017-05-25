package com.example.episodicshows.viewings;

import com.example.episodicshows.episodes.Episode;
import com.example.episodicshows.episodes.EpisodeRepository;
import com.example.episodicshows.shows.Show;
import com.example.episodicshows.shows.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.text.View;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ViewingsController {


    @Autowired ViewingService viewingService;

    @PatchMapping("/users/{id}/viewings")
    public void patchViewing(@PathVariable Long id, @RequestBody Viewing viewing) {
        viewingService.patchviewingService(id, viewing);
    }


    @GetMapping("/users/{id}/recently-watched")
    public List<ViewingResponse> getViewing(@PathVariable Long id) {

        return viewingService.getViewingResponses(id);
    }


}

