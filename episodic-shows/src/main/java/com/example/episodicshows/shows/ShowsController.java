package com.example.episodicshows.shows;

import com.example.episodicshows.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShowsController {

    @Autowired
    ShowRepository showRepository;

    @PostMapping("/shows")
    public Show getUsers(@RequestBody Show show) {
        return this.showRepository.save(show);
    }


    @GetMapping("/shows")
    public Iterable<Show> getShow() {

        return this.showRepository.findAll();

    }


}
