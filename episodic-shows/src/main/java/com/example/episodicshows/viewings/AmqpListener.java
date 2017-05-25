package com.example.episodicshows.viewings;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class AmqpListener implements RabbitListenerConfigurer {

    private final ViewingService viewingService;

    public AmqpListener(ViewingService viewingService) {
        this.viewingService = viewingService;
    }

    @RabbitListener(queues = "episodic-progress")
    @Transactional
    public void receiveMessage(final ProgessMessage message)  {
        System.out.println("************************************************");

        Long epsisodeId = message.getEpisodeId();
        Long userId = message.getUserId();
        Date createdAt = message.getCreatedAt();

        Viewing viewing = new Viewing();
        viewing.setEpisodeId(epsisodeId);
        viewing.setUserId(userId);
        viewing.setTimecode(message.getOffset());
        viewing.setUpdatedAt(createdAt);

        Boolean value=viewingService.queryRepositories(epsisodeId,userId);

        if (value){
            viewingService.patchviewingService(userId, viewing);
        }

        System.out.println(message.toString());
        System.out.println("************************************************");


    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() { // <-- 2
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {  // <-- 3
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

}