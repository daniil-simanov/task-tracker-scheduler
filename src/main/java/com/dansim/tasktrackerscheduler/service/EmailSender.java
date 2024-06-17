package com.dansim.tasktrackerscheduler.service;

import com.dansim.tasktrackerscheduler.dto.EmailDTO;
import com.dansim.tasktrackerscheduler.model.User;
import com.dansim.tasktrackerscheduler.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailSender {
    private final KafkaTemplate<String, EmailDTO> kafkaTemplate;
    private final UserRepository userRepository;
    private final EmailFactory emailFactory;

    @Scheduled(cron = "0 0 0 * * *")
    public void sendEmail(){
        List<User> users = userRepository.findAll();
        for (User user : users){
            EmailDTO emailDTO = emailFactory.createEmail(user);
            kafkaTemplate.send("emailTopic",emailDTO);
        }
    }
}
