package com.example.contractsystem.Repository;

import com.example.contractsystem.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    Notification findNotificationById(Integer id);

    List<Notification> findByUserId(Integer userId);

    List<Notification> findByUserIdAndIsReadFalse(Integer userId);
}
