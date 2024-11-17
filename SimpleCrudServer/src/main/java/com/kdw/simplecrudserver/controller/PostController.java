package com.kdw.simplecrudserver.controller;

import com.kdw.simplecrudserver.controller.dto.req.UpdatePostRequest;
import com.kdw.simplecrudserver.controller.dto.req.UploadPostRequest;
import com.kdw.simplecrudserver.service.PostService;
import com.kdw.simplecrudserver.service.dto.req.CreatePostServiceRequest;
import com.kdw.simplecrudserver.service.dto.req.UpdatePostServiceRequest;
import com.kdw.simplecrudserver.service.dto.res.ReadPostServiceResponse;
import com.kdw.simplecrudserver.service.dto.res.ReadPostSliceServiceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<ReadPostServiceResponse> findPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.readPostById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable Long id) {
        postService.deletePostById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePostById(@PathVariable Long id, @RequestBody @Valid UpdatePostRequest updatePostRequest) {
        postService.updatePost(UpdatePostServiceRequest.builder()
                .targetId(id)
                .title(updatePostRequest.getTitle())
                .content(updatePostRequest.getContent())
            .build());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ReadPostSliceServiceResponse> readPostSlice(@RequestParam(defaultValue = "0") Integer offset,
                                                                      @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(postService.readPosts(offset, limit));
    }

    @PostMapping
    public ResponseEntity<Void> uploadPost(@RequestBody @Valid final UploadPostRequest uploadPostRequest) {
        Long id = postService.createPost(CreatePostServiceRequest.builder()
                .title(uploadPostRequest.getTitle())
                .content(uploadPostRequest.getContent())
                .author(uploadPostRequest.getAuthor())
            .build());
        return ResponseEntity.created(URI.create("/posts/" + id)).build();
    }
}
