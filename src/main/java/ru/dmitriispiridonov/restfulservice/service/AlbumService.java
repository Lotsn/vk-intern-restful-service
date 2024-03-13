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
import ru.dmitriispiridonov.restfulservice.dto.api.album.Photo;

@Service
@AllArgsConstructor
public class AlbumService {

    private final RestClient restClient;

    @Cacheable(value = "albums")
    public List<Album> findAll() {
        return restClient.get().uri("/albums").retrieve().body(new ParameterizedTypeReference<List<Album>>() {});
    }

    public Album findById(Integer id) {
        return restClient.get().uri("/albums/{id}", id).retrieve().body(Album.class);
    }

    @CacheEvict(value = "albums", allEntries = true)
    public Album create(Album album) {
        return restClient
                .post()
                .uri("/albums")
                .contentType(MediaType.APPLICATION_JSON)
                .body(album)
                .retrieve()
                .body(Album.class);
    }

    @CacheEvict(value = "albums", allEntries = true)
    public Album update(Integer id, Album album) {
        return restClient
                .put()
                .uri("/albums/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(album)
                .retrieve()
                .body(Album.class);
    }

    @CacheEvict(value = "albums", allEntries = true)
    public void delete(Integer id) {
        restClient.delete().uri("/albums/{id}", id).retrieve().toBodilessEntity();
    }

    public List<Photo> findAllPhoto(Integer id) {
        return restClient
                .get()
                .uri("/albums/{id}/photos", id)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Photo>>() {});
    }
}
