package com.chervonnaya.dto;

import java.util.Set;

public class NotificationDTO {

    private String publisherFullName;
    private Set<UserDTO> subscribers;

    public NotificationDTO(String publisherFullName, Set<UserDTO> subscribers) {
        this.publisherFullName = publisherFullName;
        this.subscribers = subscribers;
    }

    public NotificationDTO() {

    }

    public String getPublisherFullName() {
        return publisherFullName;
    }

    public void setPublisherFullName(String publisherFullName) {
        this.publisherFullName = publisherFullName;
    }

    public Set<UserDTO> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<UserDTO> subscribers) {
        this.subscribers = subscribers;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
            "publisherFullName='" + publisherFullName + '\'' +
            ", subscribers=" + subscribers +
            '}';
    }
}
