package ru.dmitriispiridonov.restfulservice.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.dmitriispiridonov.restfulservice.dto.api.post.Comment;
import ru.dmitriispiridonov.restfulservice.dto.api.post.Post;
import ru.dmitriispiridonov.restfulservice.service.PostService;

@RestController
@RequestMapping("/api/posts")
@PreAuthorize("hasAnyAuthority('ROLE_POSTS') || hasAnyAuthority('ROLE_ADMIN')")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    List<Post> findAll() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    Post findPostById(@PathVariable Integer id) {
        return postService.findById(id);
    }

    @GetMapping("/{id}/comments")
    List<Comment> findAllComments(@PathVariable Integer id) {
        return postService.findAllComments(id);
    }

    @GetMapping("/comments")
    List<Comment> findAllCommentsWithParam(@RequestParam Integer postId) {
        return postService.findAllCommentsWithParam(postId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @PutMapping
    Post update(@PathVariable Integer id, @RequestBody Post post) {
        return postService.update(id, post);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Integer id) {
        postService.delete(id);
    }
}
