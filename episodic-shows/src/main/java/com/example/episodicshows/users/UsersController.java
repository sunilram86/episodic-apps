package com.example.episodicshows.users;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    private final UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user)
    {
        return this.userRepository.save(user);
    }

    @GetMapping("/users")
    public Iterable<User> getUser()
    {

        return this.userRepository.findAll();

    }
}
