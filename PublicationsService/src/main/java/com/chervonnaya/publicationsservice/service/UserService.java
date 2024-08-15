package com.chervonnaya.publicationsservice.service;

import com.chervonnaya.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class UserService {

    private final String USER_URL = "http://localhost:8080/api/user";

    private final RestTemplate restTemplate;

    public UserDTO getUserDTO(Long publisherId) {
        String url = USER_URL + "/" + publisherId;
        ResponseEntity<UserDTO> response = restTemplate.getForEntity(url, UserDTO.class);
        return response.getBody();
    }
}
