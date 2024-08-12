package ru.alex.taskmanagementsystem.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alex.taskmanagementsystem.dto.CommentCreateEditDto;
import ru.alex.taskmanagementsystem.dto.CommentReadDto;
import ru.alex.taskmanagementsystem.service.CommentService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentReadDto> postComment(@RequestBody CommentCreateEditDto comment) {
        return new ResponseEntity<>(commentService.postComment(comment), CREATED);
    }
}
