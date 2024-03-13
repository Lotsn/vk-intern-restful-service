package ru.dmitriispiridonov.restfulservice.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.dmitriispiridonov.restfulservice.dto.api.album.Album;
import ru.dmitriispiridonov.restfulservice.dto.api.album.Photo;
import ru.dmitriispiridonov.restfulservice.service.AlbumService;

@RestController
@RequestMapping("/api/albums")
@AllArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    List<Album> findAll() {
        return albumService.findAll();
    }

    @GetMapping("/{id}")
    Album findAlbumById(@PathVariable Integer id) {
        return albumService.findById(id);
    }

    @GetMapping("/{id}/photos")
    List<Photo> findAllPhoto(@PathVariable Integer id) {
        return albumService.findAllPhoto(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Album create(@RequestBody Album album) {
        return albumService.create(album);
    }

    @PutMapping("/{id}")
    Album update(@PathVariable Integer id, @RequestBody Album album) {
        return albumService.update(id, album);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Integer id) {
        albumService.delete(id);
    }
}
