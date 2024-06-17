package com.dansim.tasktrackerscheduler.service;

import com.dansim.tasktrackerscheduler.dto.EmailDTO;
import com.dansim.tasktrackerscheduler.model.Task;
import com.dansim.tasktrackerscheduler.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailFactory {

    public EmailDTO createEmail(User user){
        return  EmailDTO
                .builder()
                .recipientAddress(user.getEmail())
                .title(createEmailTitle(user))
                .text(createEmailText(user))
                .build();
    }

    private String createEmailTitle(User user){
        return "Отчет о проделанной работе пользователем " +
                user.getEmail() +
                " за " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));

    }
    private String createEmailText(User user) {
        StringBuilder emailText = new StringBuilder();
        addUncompletedTasksInfo(emailText, user.getTasks());
        addCompletedTasksInfo(emailText, user.getTasks());
        return emailText.toString();
    }
    private void addCompletedTasksInfo(StringBuilder emailText, List<Task> tasks) {
        List<String> completedTaskPerDayHeaders = tasks.stream()
                .filter(task -> ChronoUnit.HOURS.between(task.getModified(), LocalDateTime.now()) < 24)
                .filter(Task::isCompleted)
                .map(Task::getHeader)
                .toList();

        int completedTaskPerDayCount = completedTaskPerDayHeaders.size();
        if (completedTaskPerDayCount > 0) {
            emailText.append("Количество выполненных задач за сегодня: \n").append(completedTaskPerDayCount);
            completedTaskPerDayHeaders.forEach(header -> emailText.append("— ").append(header).append("\n"));
        }
    }

    private void addUncompletedTasksInfo(StringBuilder emailText, List<Task> tasks) {
        List<String> uncompletedTaskHeaders = tasks.stream()
                .filter(task -> !task.isCompleted())
                .map(Task::getHeader)
                .toList();

        int uncompletedTaskCount = uncompletedTaskHeaders.size();
        if (uncompletedTaskCount > 0) {
            emailText.append("Общее количество невыполненных задач: \n").append(uncompletedTaskCount);
            uncompletedTaskHeaders.stream().limit(5).forEach(header -> emailText.append("— ").append(header).append("\n"));
            emailText.append("\n");
        }

    }
}
