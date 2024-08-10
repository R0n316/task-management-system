package ru.alex.taskmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alex.taskmanagementsystem.dto.AuthDto;
import ru.alex.taskmanagementsystem.dto.UserDto;
import ru.alex.taskmanagementsystem.mapper.AuthMapper;
import ru.alex.taskmanagementsystem.repository.UserRepository;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthMapper authMapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       AuthMapper authMapper) {
        this.userRepository = userRepository;
        this.authMapper = authMapper;
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

    public void register(AuthDto user){
        userRepository.save(authMapper.toEntity(user));
    }
}
