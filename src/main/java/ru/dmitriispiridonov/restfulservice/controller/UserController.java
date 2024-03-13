package ru.dmitriispiridonov.restfulservice.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.dmitriispiridonov.restfulservice.dto.api.album.Album;
import ru.dmitriispiridonov.restfulservice.dto.api.post.Post;
import ru.dmitriispiridonov.restfulservice.dto.api.user.Todo;
import ru.dmitriispiridonov.restfulservice.dto.api.user.User;
import ru.dmitriispiridonov.restfulservice.service.UserService;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasAnyAuthority('ROLE_USERS') || hasAnyAuthority('ROLE_ADMIN')")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    User findUserById(@PathVariable Integer id) {
        return userService.findUserById(id);
    }

    @GetMapping("/{id}/albums")
    List<Album> findAllUserAlbum(@PathVariable Integer id) {
        return userService.findAllUserAlbum(id);
    }

    @GetMapping("/{id}/todos")
    List<Todo> findAllUserTodo(@PathVariable Integer id) {
        return userService.findAllUserTodo(id);
    }

    @GetMapping("/{id}/posts")
    List<Post> findAllUserPost(@PathVariable Integer id) {
        return userService.findAllUserPost(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    User create(@RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    User update(@PathVariable Integer id, @RequestBody User user) {
        return userService.update(id, user);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Integer id) {
        userService.delete(id);
    }
}
