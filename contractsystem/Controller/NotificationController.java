package com.example.contractsystem.Controller;

import com.example.contractsystem.ApiResponse.ApiResponse;
import com.example.contractsystem.Model.Notification;
import com.example.contractsystem.Service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/get")
    public ResponseEntity getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @PostMapping("/add")
    public ResponseEntity addNotification(@RequestBody @Valid Notification notification) {
        notificationService.addNotification(notification);
        return ResponseEntity.status(200).body(new ApiResponse("notification added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateNotification(@PathVariable Integer id,@RequestBody @Valid Notification notification) {
        notificationService.updateNotification(id, notification);
        return ResponseEntity.status(200).body(new ApiResponse("notification updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteNotification(@PathVariable Integer id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.status(200).body(new ApiResponse("notification deleted successfully"));
    }



    @GetMapping("/get-notifications-by-user/{userId}")
    public ResponseEntity getNotificationsByUserId(@PathVariable Integer userId) {
        List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/get-unread-notifications/{userId}")
    public ResponseEntity getUnreadNotifications(@PathVariable Integer userId) {
        List<Notification> unreadNotifications = notificationService.getUnreadNotificationsByUserId(userId);
        return ResponseEntity.status(200).body(unreadNotifications);
    }

    @PutMapping("/mark-notification-as-read/{notificationId}")
    public ResponseEntity markNotificationAsRead(@PathVariable Integer notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.status(200).body(new ApiResponse("Notification marked as read"));
    }

    @PostMapping("/send-periodic-notifications/{userId}")
    public ResponseEntity sendPeriodicNotifications(@PathVariable Integer userId){
        notificationService.sendPeriodicNotifications(userId);
        return ResponseEntity.status(200).body(new ApiResponse("Sending periodic notifications done successfully"));
    }

}
