package com.chervonnaya.dto;

public class LikeDTO {
    private String publisherFullName;
    private String subscriberFullName;
    private String publisherEmail;
    private String publicationText;

    public LikeDTO(String publisherFullName, String subscriberFullName, String publisherEmail, String publicationText) {
        this.publisherFullName = publisherFullName;
        this.subscriberFullName = subscriberFullName;
        this.publisherEmail = publisherEmail;
        this.publicationText = publicationText;
    }

    public LikeDTO() {
    }

    public String getPublisherFullName() {
        return publisherFullName;
    }

    public void setPublisherFullName(String publisherFullName) {
        this.publisherFullName = publisherFullName;
    }

    public String getSubscriberFullName() {
        return subscriberFullName;
    }

    public void setSubscriberFullName(String subscriberFullName) {
        this.subscriberFullName = subscriberFullName;
    }

    public String getPublisherEmail() {
        return publisherEmail;
    }

    public void setPublisherEmail(String publisherEmail) {
        this.publisherEmail = publisherEmail;
    }

    public String getPublicationText() {
        return publicationText;
    }

    public void setPublicationText(String publicationText) {
        this.publicationText = publicationText;
    }

    @Override
    public String toString() {
        return "LikeDTO{" +
            "publisherFullName='" + publisherFullName + '\'' +
            ", subscriberFullName='" + subscriberFullName + '\'' +
            ", publisherEmail=" + publisherEmail +
            ", publicationText='" + publicationText + '\'' +
            '}';
    }
}
