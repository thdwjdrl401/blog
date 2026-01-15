package com.example.blog.web.dto;

import com.example.blog.domain.post.Post;
import com.example.blog.domain.post.PostStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String contentMd;
    private String excerpt;
    private String coverImageUrl;
    private String slug;
    private PostStatus status;
    private String author;
    private String category;
    private List<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime publishedAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contentMd = post.getContentMd();
        this.excerpt = post.getExcerpt();
        this.coverImageUrl = post.getCoverImageUrl();
        this.slug = post.getSlug();
        this.status = post.getStatus();
        this.author = post.getUser().getUsername();
        this.category = post.getCategory() != null ? post.getCategory().getName() : null;
        this.tags = post.getTags().stream().map(tag -> tag.getName()).collect(Collectors.toList());
        this.createdAt = post.getCreatedAt();
        this.publishedAt = post.getPublishedAt();
    }
}
