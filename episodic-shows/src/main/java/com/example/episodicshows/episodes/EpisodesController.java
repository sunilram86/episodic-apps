package com.example.episodicshows.episodes;

import com.example.episodicshows.shows.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EpisodesController {

    @Autowired EpisodeRepository episodeRepository;

    @PostMapping("/shows/{id}/episodes")
    public Episode postEpisodes(@PathVariable Long id , @RequestBody Episode episode)
    {
        episode.setShowId(id);
        return this.episodeRepository.save(episode);
    }

    @GetMapping("/shows/{id}/episodes")
    public List<Episode> getEpisodes(@PathVariable Long id) {

        return this.episodeRepository.findByShowId(id);


    }
}

