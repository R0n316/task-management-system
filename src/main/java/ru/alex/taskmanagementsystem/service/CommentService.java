package ru.alex.taskmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.taskmanagementsystem.dto.CommentCreateEditDto;
import ru.alex.taskmanagementsystem.dto.CommentReadDto;
import ru.alex.taskmanagementsystem.entity.Comment;
import ru.alex.taskmanagementsystem.mapper.CommentCreateEditMapper;
import ru.alex.taskmanagementsystem.mapper.CommentReadMapper;
import ru.alex.taskmanagementsystem.repository.CommentRepository;
import ru.alex.taskmanagementsystem.repository.UserRepository;
import ru.alex.taskmanagementsystem.util.JwtUtil;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentCreateEditMapper commentCreateEditMapper;
    private final CommentReadMapper commentReadMapper;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          UserRepository userRepository,
                          CommentCreateEditMapper commentCreateEditMapper,
                          CommentReadMapper commentReadMapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.commentCreateEditMapper = commentCreateEditMapper;
        this.commentReadMapper = commentReadMapper;
    }

    public CommentReadDto postComment(CommentCreateEditDto commentDto){
        String email = JwtUtil.getEmailByJwtToken();
        return userRepository.findByEmail(email)
                .map(user -> {
                    Comment comment = commentCreateEditMapper.toEntity(commentDto);
                    comment.setUser(user);
                    Comment savedComment = commentRepository.save(comment);
                    return commentReadMapper.toDto(savedComment);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
