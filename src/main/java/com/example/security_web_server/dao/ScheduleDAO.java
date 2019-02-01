package com.example.security_web_server.dao;

import com.example.security_web_server.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleDAO extends JpaRepository<Schedule, Integer> {
}
