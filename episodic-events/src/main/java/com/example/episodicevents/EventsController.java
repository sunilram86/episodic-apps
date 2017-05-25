package com.example.episodicevents;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventsController {


    private final EventsRepository eventsRepository;

    private final RabbitTemplate rabbitTemplate;

    public EventsController(RabbitTemplate rabbitTemplate, EventsRepository eventsRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.eventsRepository =eventsRepository;
    }

    @PostMapping("/")
    public void createEvent(@RequestBody Event event) {
        this.eventsRepository.save(event);

        if(event instanceof Progress) {

            Progress p = (Progress)  event;

            ProgressMessage pm = new ProgressMessage();
            pm.setUserId(Long.valueOf(event.getUserId()));
            pm.setEpisodeId(Long.valueOf(event.getEpisodeId()));
            pm.setCreatedAt(event.getCreatedAt());
            pm.setOffset(p.getData().getOffset());

            rabbitTemplate.convertAndSend("episodic-exchange", "episodic-routing-key1", pm);
        }
    }


    @GetMapping("/recent")
    public List<Event> getEvents() {
        PageRequest request = new PageRequest(0, 20, new Sort(Sort.Direction.DESC, "createdAt"));
        return eventsRepository.findRecent(request);
    }
}
