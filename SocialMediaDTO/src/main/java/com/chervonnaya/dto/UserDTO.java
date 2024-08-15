package com.chervonnaya.dto;

public class UserDTO {
    private Long id;
    private String email;
    private String fullName;

    public UserDTO(Long id, String email, String fullName) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
    }

    public UserDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "id=" + id +
            ", email=" + email +
            ", fullName=" + fullName +
            '}';
    }
}
