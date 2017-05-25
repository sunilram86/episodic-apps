package com.example.episodicshows.viewings;


import com.example.episodicshows.episodes.Episode;
import com.example.episodicshows.episodes.EpisodeRepository;
import com.example.episodicshows.shows.Show;
import com.example.episodicshows.shows.ShowRepository;
import com.example.episodicshows.users.User;
import com.example.episodicshows.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ViewingService {

    @Autowired
    ViewingRepository viewingRepository;

    @Autowired
    EpisodeRepository episodeRepository;

    @Autowired
    ShowRepository showRepository;

    @Autowired
    UserRepository userRepository;

    public void patchviewingService(@PathVariable Long id, @RequestBody Viewing viewing) {
        viewing.setUserId(id);

        Episode episode = episodeRepository.findById(viewing.getEpisodeId());

        Long showID = episode.getShowId();

        if (showID != null) {

            viewing.setShowId(showID);
            Viewing viewingDetails = viewingRepository.findByUserIdAndShowId(id, showID);

            if (viewingDetails != null) {
                viewingDetails.setTimecode(viewing.getTimecode());
                viewingDetails.setUpdatedAt(viewing.getUpdatedAt());
                viewingDetails.setEpisodeId(viewing.getEpisodeId());
                viewingRepository.save(viewingDetails);
            } else {
                viewingRepository.save(viewing);
            }
        }
    }
    public List<ViewingResponse> getViewingResponses(@PathVariable Long id) {
        List<Viewing> viewingDetails = viewingRepository.findByUserIdOrderByUpdatedAtDesc(id);

        return viewingDetails.stream().map(activity -> {
            Long showId = activity.getShowId();
            Show show = showRepository.findOne(showId);
            String showName = show.getName();
            Long episodeId = activity.getEpisodeId();
            Episode episode = episodeRepository.findById(episodeId);

            Integer seasonNumber = episode.getSeasonNumber();
            Integer episodeNumber = episode.getEpisodeNumber();

            String title = "S" + seasonNumber + " " + "E" + episodeNumber;
            String updatedAt = String.valueOf(activity.getUpdatedAt());
            Integer timecode = activity.getTimecode();

            show.setId(showId);
            show.setName(showName);

            ViewingResponse viewingResponse = new ViewingResponse();
            viewingResponse.setShow(show);

            episode.setEpisodeNumber(episodeNumber);
            episode.setSeasonNumber(seasonNumber);
            episode.setId(episodeId);
            episode.setTitle(title);

            viewingResponse.setEpisode(episode);
            viewingResponse.setUpdatedAt(updatedAt);
            viewingResponse.setTimecode(timecode);

            return viewingResponse;
        }).collect(Collectors.toList());
    }

    public Boolean queryRepositories(Long epsisodeId, Long userId)
    {
        Episode episode = this.episodeRepository.findById(epsisodeId);
        User user = this.userRepository.findById(userId);

        if(episode != null && user !=null)
        {
            return true;
        }

        return false;
    }

}
