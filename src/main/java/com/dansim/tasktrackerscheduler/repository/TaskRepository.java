package com.dansim.tasktrackerscheduler.repository;

import com.dansim.tasktrackerscheduler.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {
}
