package ru.alex.taskmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alex.taskmanagementsystem.dto.UserDto;
import ru.alex.taskmanagementsystem.dto.UserRegisterDto;
import ru.alex.taskmanagementsystem.http.controller.UserRegisterMapper;
import ru.alex.taskmanagementsystem.repository.UserRepository;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRegisterMapper userRegisterMapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserRegisterMapper userRegisterMapper) {
        this.userRepository = userRepository;
        this.userRegisterMapper = userRegisterMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(user -> new UserDto(
                        user.getId(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getName(),
                        Collections.singletonList(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }

    public void register(UserRegisterDto user){
        userRepository.save(userRegisterMapper.toEntity(user));
    }
}
