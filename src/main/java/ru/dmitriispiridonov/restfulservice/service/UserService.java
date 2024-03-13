package ru.dmitriispiridonov.restfulservice.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.dmitriispiridonov.restfulservice.dto.api.album.Album;
import ru.dmitriispiridonov.restfulservice.dto.api.post.Post;
import ru.dmitriispiridonov.restfulservice.dto.api.user.Todo;
import ru.dmitriispiridonov.restfulservice.dto.api.user.User;

@Service
@AllArgsConstructor
public class UserService {
    private final RestClient restClient;

    @Cacheable(value = "users")
    public List<User> findAll() {
        return restClient.get().uri("/users").retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public User findUserById(Integer id) {
        return restClient.get().uri("/users/{id}", id).retrieve().body(User.class);
    }

    public List<Album> findAllUserAlbum(Integer id) {
        return restClient.get().uri("/users/{id}/albums", id).retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public List<Todo> findAllUserTodo(Integer id) {
        return restClient.get().uri("/users/{id}/todos", id).retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public List<Post> findAllUserPost(Integer id) {
        return restClient.get().uri("/users/{id}/posts", id).retrieve().body(new ParameterizedTypeReference<>() {});
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        return restClient
                .post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(user)
                .retrieve()
                .body(User.class);
    }

    @CacheEvict(value = "users", allEntries = true)
    public User update(Integer id, User user) {
        return restClient
                .post()
                .uri("/users/{id", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(user)
                .retrieve()
                .body(User.class);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(Integer id) {
        restClient.delete().uri("/users/{id}", id).retrieve().toBodilessEntity();
    }
}
