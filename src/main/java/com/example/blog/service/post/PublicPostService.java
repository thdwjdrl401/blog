package com.example.blog.service.post;

import com.example.blog.domain.post.Post;
import com.example.blog.domain.post.PostRepository;
import com.example.blog.domain.post.PostStatus;
import com.example.blog.web.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicPostService {

    private final PostRepository postRepository;

    public Page<PostResponse> getPosts(Pageable pageable) {
        return postRepository.findByStatus(PostStatus.PUBLISHED, pageable)
                .map(PostResponse::new);
    }

    public PostResponse getPostBySlug(String slug) {
        Post post = postRepository.findBySlugAndStatus(slug, PostStatus.PUBLISHED)
                .orElseThrow(() -> new IllegalArgumentException("Post not found or not published"));
        return new PostResponse(post);
    }

    public Page<PostResponse> searchPosts(String keyword, Pageable pageable) {
        return postRepository.searchByKeyword(keyword, pageable)
                .map(PostResponse::new);
    }

    public Page<PostResponse> getPostsByCategory(String categoryName, Pageable pageable) {
        return postRepository.findByCategory_NameAndStatus(categoryName, PostStatus.PUBLISHED, pageable)
                .map(PostResponse::new);
    }

    public Page<PostResponse> getPostsByTag(String tagName, Pageable pageable) {
        return postRepository.findByTags_NameAndStatus(tagName, PostStatus.PUBLISHED, pageable)
                .map(PostResponse::new);
    }
}
