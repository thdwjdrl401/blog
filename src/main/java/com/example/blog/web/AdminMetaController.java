package com.example.blog.web;

import com.example.blog.domain.category.Category;
import com.example.blog.domain.tag.Tag;
import com.example.blog.service.category.TagCategoryService;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminMetaController {

    private final TagCategoryService tagCategoryService;

    // Categories
    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody MetaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tagCategoryService.createCategory(request.getName()));
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody MetaRequest request) {
        return ResponseEntity.ok(tagCategoryService.updateCategory(id, request.getName()));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        tagCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    // Tags
    @PostMapping("/tags")
    public ResponseEntity<Tag> createTag(@RequestBody MetaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tagCategoryService.createTag(request.getName()));
    }

    @PutMapping("/tags/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @RequestBody MetaRequest request) {
        return ResponseEntity.ok(tagCategoryService.updateTag(id, request.getName()));
    }

    @DeleteMapping("/tags/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagCategoryService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

    @Data
    public static class MetaRequest {
        @NotBlank
        private String name;
    }
}
