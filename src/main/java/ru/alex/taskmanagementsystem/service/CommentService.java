package ru.alex.taskmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.alex.taskmanagementsystem.dto.comment.CommentCreateEditDto;
import ru.alex.taskmanagementsystem.dto.comment.CommentReadDto;
import ru.alex.taskmanagementsystem.entity.Comment;
import ru.alex.taskmanagementsystem.mapper.CommentCreateEditMapper;
import ru.alex.taskmanagementsystem.mapper.CommentReadMapper;
import ru.alex.taskmanagementsystem.repository.CommentRepository;
import ru.alex.taskmanagementsystem.repository.UserRepository;
import ru.alex.taskmanagementsystem.util.JwtUtil;

import java.util.Optional;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    public CommentReadDto post(CommentCreateEditDto commentDto){
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

    public CommentReadDto edit(Integer id, String updatedText) {
        Optional<Comment> foundComment = commentRepository.findById(id);
        checkCommentAuthorAndAuthorizedUser(foundComment);
        assert foundComment.isPresent();
        Comment comment = foundComment.get();
        comment.setText(updatedText);
        return commentReadMapper.toDto(commentRepository.save(comment));
    }

    public void delete(Integer id) {
        Optional<Comment> foundComment = commentRepository.findById(id);
        checkCommentAuthorAndAuthorizedUser(foundComment);
        assert foundComment.isPresent();
        commentRepository.deleteById(foundComment.get().getId());
    }

    private void checkCommentAuthorAndAuthorizedUser(Optional<Comment> foundComment) {
        userRepository.findByEmail(JwtUtil.getEmailByJwtToken()).ifPresent(user -> {
            if (foundComment.isEmpty()) {
                throw new ResponseStatusException(NOT_FOUND);
            } else {
                if (!foundComment.get().getUser().getId().equals(user.getId())) {
                    throw new ResponseStatusException(FORBIDDEN);
                }
            }
        });
    }
}
