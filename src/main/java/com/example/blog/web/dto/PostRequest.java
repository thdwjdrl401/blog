package com.example.blog.web.dto;

import com.example.blog.domain.post.PostStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String contentMd;

    private String excerpt;

    private String coverImageUrl;

    private String slug;

    private PostStatus status; // DRAFT or PUBLISHED

    private String categoryName;

    private List<String> tagNames;
}
