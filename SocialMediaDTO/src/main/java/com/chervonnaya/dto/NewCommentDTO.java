package com.chervonnaya.dto;

public class NewCommentDTO {
    private String publisherFullName;
    private String subscriberFullName;
    private String publisherEmail;
    private String publicationText;
    private String commentText;

    public NewCommentDTO(String publisherFullName, String subscriberFullName, String publisherEmail, String publicationText, String commentText) {
        this.publisherFullName = publisherFullName;
        this.subscriberFullName = subscriberFullName;
        this.publisherEmail = publisherEmail;
        this.publicationText = publicationText;
        this.commentText = commentText;
    }

    public NewCommentDTO() {
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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
            "publisherFullName='" + publisherFullName + '\'' +
            ", subscriberFullName='" + subscriberFullName + '\'' +
            ", publisherEmail=" + publisherEmail +
            ", publicationText='" + publicationText + '\'' +
            ", commentText='" + commentText + '\'' +
            '}';
    }
}
