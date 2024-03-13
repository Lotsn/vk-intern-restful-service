package ru.dmitriispiridonov.restfulservice.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import ru.dmitriispiridonov.restfulservice.dto.api.post.Comment;
import ru.dmitriispiridonov.restfulservice.dto.api.post.Post;

@Service
@AllArgsConstructor
public class PostService {

    private final RestClient restClient;

    @Cacheable(value = "Posts")
    public List<Post> findAll() {
        return restClient.get().uri("/posts").retrieve().body(new ParameterizedTypeReference<List<Post>>() {});
    }

    public Post findById(Integer id) {
        return restClient.get().uri("/posts/{id}", id).retrieve().body(Post.class);
    }

    @CacheEvict(value = "Posts", allEntries = true)
    public Post create(Post post) {
        return restClient
                .post()
                .uri("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(post)
                .retrieve()
                .body(Post.class);
    }

    @CacheEvict(value = "Posts", allEntries = true)
    public Post update(Integer id, Post post) {
        return restClient
                .put()
                .uri("/posts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(post)
                .retrieve()
                .body(Post.class);
    }

    @CacheEvict(value = "Posts", allEntries = true)
    public void delete(Integer id) {
        restClient.delete().uri("/posts/{id}", id).retrieve().toBodilessEntity();
    }

    public List<Comment> findAllComments(Integer id) {
        return restClient
                .get()
                .uri("/posts/{id}/comments", id)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Comment>>() {});
    }

    public List<Comment> findAllCommentsWithParam(Integer postId) {
        return restClient
                .get()
                .uri(UriComponentsBuilder.fromPath("/comments")
                        .queryParam("postId", postId)
                        .toUriString())
                .retrieve()
                .body(new ParameterizedTypeReference<List<Comment>>() {});
    }
}
