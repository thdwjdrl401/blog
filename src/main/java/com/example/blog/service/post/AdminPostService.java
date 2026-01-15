package com.example.blog.service.post;

import com.example.blog.domain.category.Category;
import com.example.blog.domain.post.Post;
import com.example.blog.domain.post.PostRepository;
import com.example.blog.domain.post.PostStatus;
import com.example.blog.domain.tag.Tag;
import com.example.blog.domain.user.User;
import com.example.blog.domain.user.UserRepository;
import com.example.blog.service.category.TagCategoryService;
import com.example.blog.web.dto.PostRequest;
import com.example.blog.web.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminPostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagCategoryService tagCategoryService;

    public PostResponse createPost(PostRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Category category = tagCategoryService.getOrCreateCategory(request.getCategoryName());
        Set<Tag> tags = tagCategoryService.getOrCreateTags(request.getTagNames());

        String slug = request.getSlug();
        if (slug == null || slug.isBlank()) {
            slug = UUID.randomUUID().toString(); // Simple fallback
        }

        Post post = Post.builder()
                .title(request.getTitle())
                .contentMd(request.getContentMd())
                .excerpt(request.getExcerpt())
                .coverImageUrl(request.getCoverImageUrl())
                .slug(slug)
                .status(request.getStatus() != null ? request.getStatus() : PostStatus.DRAFT)
                .user(user)
                .category(category)
                .build();

        post.setTags(tags);

        return new PostResponse(postRepository.save(post));
    }

    public PostResponse updatePost(Long id, PostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Category category = tagCategoryService.getOrCreateCategory(request.getCategoryName());
        Set<Tag> tags = tagCategoryService.getOrCreateTags(request.getTagNames());

        post.update(
                request.getTitle(),
                request.getContentMd(),
                request.getExcerpt(),
                request.getCoverImageUrl(),
                request.getSlug(),
                request.getStatus(),
                category);
        post.setTags(tags);

        return new PostResponse(post);
    }

    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return new PostResponse(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
