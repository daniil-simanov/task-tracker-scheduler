package com.dansim.tasktrackerscheduler.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic topic(){
        return new NewTopic("emailTopic",1,(short) 1);
    }

}
