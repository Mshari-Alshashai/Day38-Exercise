package com.example.contractsystem.Service;

import com.example.contractsystem.ApiResponse.ApiException;
import com.example.contractsystem.Model.Notification;
import com.example.contractsystem.Model.Project;
import com.example.contractsystem.Repository.NotificationRepository;
import com.example.contractsystem.Repository.ProjectRepository;
import com.example.contractsystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    private final UserRepository userRepository;


    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public void addNotification(Notification notification) {
        if (userRepository.findUsersById(notification.getUserId())==null) throw new ApiException("User not found");

        notification.setTimestamp(LocalDateTime.now());
        notification.setIsRead(false);
        notificationRepository.save(notification);
    }

    public void updateNotification(Integer id, Notification notification) {
        if (notificationRepository.findNotificationById(id) == null) throw new ApiException("Notification not found");

        Notification oldNotification = notificationRepository.findNotificationById(id);

        oldNotification.setTimestamp(notification.getTimestamp());
        oldNotification.setIsRead(notification.getIsRead());
        oldNotification.setMessage(notification.getMessage());
        oldNotification.setUserId(notification.getUserId());
        notificationRepository.save(oldNotification);
    }

    public void deleteNotification(Integer id) {
        if (notificationRepository.findNotificationById(id) == null) throw new ApiException("Notification not found");
        notificationRepository.deleteById(id);
    }




    public List<Notification> getNotificationsByUserId(Integer userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getUnreadNotificationsByUserId(Integer userId) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }

    public void markNotificationAsRead(Integer notificationId) {
        Notification notification = notificationRepository.findNotificationById(notificationId);
        if (notification == null) throw new ApiException("Notification not found");

        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    public void sendPeriodicNotifications(Integer userId) {
        List<Project> projects = projectRepository.findAll();

        for (Project project : projects) {
            double progress = projectService.calculateProjectProgress(project.getId());

            if (progress <= 50) {
                Notification notification = new Notification(null, userId, "Project " + project.getName() + " is less than 50% complete.", LocalDateTime.now(), false);
                notificationRepository.save(notification);
            }
            if (progress >= 50) {
                Notification notification = new Notification(null, userId, "Project " + project.getName() + " is more than 50% complete.", LocalDateTime.now(), false);
                notificationRepository.save(notification);
            }
        }
    }

}
