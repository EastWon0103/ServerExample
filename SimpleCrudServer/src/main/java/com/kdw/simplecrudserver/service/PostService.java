package com.kdw.simplecrudserver.service;

import com.kdw.simplecrudserver.repository.Post;
import com.kdw.simplecrudserver.repository.PostRepository;
import com.kdw.simplecrudserver.service.dto.req.CreatePostServiceRequest;
import com.kdw.simplecrudserver.service.dto.req.UpdatePostServiceRequest;
import com.kdw.simplecrudserver.service.dto.res.ReadPostServiceResponse;
import com.kdw.simplecrudserver.service.dto.res.ReadPostSliceServiceResponse;
import com.kdw.simplecrudserver.util.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Long createPost(final CreatePostServiceRequest createPostServiceRequest) {
        return postRepository.save(Post.builder()
                .title(createPostServiceRequest.getTitle())
                .content(createPostServiceRequest.getContent())
                .author(createPostServiceRequest.getAuthor())
            .build()).getId();
    }

    public ReadPostServiceResponse readPostById(final Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new CustomException(404, "Can't find Post"));

        return ReadPostServiceResponse.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .author(post.getAuthor())
            .build();
    }

    public ReadPostSliceServiceResponse readPosts(int offset, int length) {
        Slice<Post> posts = postRepository.findPostsBy(PageRequest.of(offset, length));

        return ReadPostSliceServiceResponse.builder()
            .hasNext(posts.hasNext())
            .posts(posts.getContent().stream().map(
                post -> ReadPostServiceResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .author(post.getAuthor())
                    .build())
                .toList())
            .build();
    }

    @Transactional
    public void updatePost(final UpdatePostServiceRequest updatePostServiceRequest) {
        Post post = postRepository.findById(updatePostServiceRequest.getTargetId())
            .orElseThrow(() -> new CustomException(404, "Can't find Post"));

        if (StringUtils.hasText(updatePostServiceRequest.getTitle()))
            post.setTitle(updatePostServiceRequest.getTitle());

        if (StringUtils.hasText(updatePostServiceRequest.getContent()))
            post.setContent(updatePostServiceRequest.getContent());

        postRepository.save(post);
    }

    @Transactional
    public void deletePostById(final Long id) {
        postRepository.deleteById(id);
    }
}
