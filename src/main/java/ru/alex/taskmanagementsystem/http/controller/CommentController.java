package ru.alex.taskmanagementsystem.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.taskmanagementsystem.dto.comment.CommentCreateEditDto;
import ru.alex.taskmanagementsystem.dto.comment.CommentReadDto;
import ru.alex.taskmanagementsystem.service.CommentService;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentReadDto> post(@RequestBody CommentCreateEditDto comment) {
        return new ResponseEntity<>(commentService.post(comment), CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentReadDto> edit(@PathVariable("id") Integer id, @RequestBody String text) {
        return new ResponseEntity<>(commentService.edit(id,text),OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        commentService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
