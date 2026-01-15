package com.example.blog.web;

import com.example.blog.service.post.PublicPostService;
import com.example.blog.web.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicPostController {

    private final PublicPostService publicPostService;

    @GetMapping("/posts")
    public ResponseEntity<Page<PostResponse>> getPosts(
            @PageableDefault(sort = "publishedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(publicPostService.getPosts(pageable));
    }

    @GetMapping("/posts/{slug}")
    public ResponseEntity<PostResponse> getPost(@PathVariable String slug) {
        return ResponseEntity.ok(publicPostService.getPostBySlug(slug));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PostResponse>> search(
            @RequestParam String q,
            @PageableDefault(sort = "publishedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(publicPostService.searchPosts(q, pageable));
    }

    @GetMapping("/categories/{categoryName}/posts")
    public ResponseEntity<Page<PostResponse>> getPostsByCategory(
            @PathVariable String categoryName,
            @PageableDefault(sort = "publishedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(publicPostService.getPostsByCategory(categoryName, pageable));
    }

    @GetMapping("/tags/{tagName}/posts")
    public ResponseEntity<Page<PostResponse>> getPostsByTag(
            @PathVariable String tagName,
            @PageableDefault(sort = "publishedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(publicPostService.getPostsByTag(tagName, pageable));
    }
}
