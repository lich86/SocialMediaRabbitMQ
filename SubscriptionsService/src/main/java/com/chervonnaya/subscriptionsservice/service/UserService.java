package com.chervonnaya.subscriptionsservice.service;


import com.chervonnaya.dto.UserDTO;
import com.chervonnaya.subscriptionsservice.model.User;
import com.chervonnaya.subscriptionsservice.repository.UserRepository;
import com.chervonnaya.subscriptionsservice.service.mappers.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public User getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @Transactional
    public User save(UserDTO dto) {
        try {
            User user = repository.save(mapper.map(dto));
            log.info("Saved user, id: {}", user.getId());
            return user;
        } catch (Exception ex) {
            log.error("Unable to save new user");
            throw new RuntimeException("Unable to save new user", ex);
        }
    }

    @Transactional
    public User update(UserDTO dto, Long id) {
        repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        try {
            User user = repository.save(mapper.map(dto));
            log.info("Updated user, id: {}", user.getId());
            return user;
        } catch (Exception ex) {
            log.error("Unable to update user, id: {}", id);
            throw new RuntimeException(String.format("Unable to update user, id: %d", id), ex);
        }
    }

}
