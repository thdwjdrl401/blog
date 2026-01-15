package com.example.blog.web;

import com.example.blog.domain.category.Category;
import com.example.blog.domain.tag.Tag;
import com.example.blog.service.category.TagCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicMetaController {

    private final TagCategoryService tagCategoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(tagCategoryService.getAllCategories());
    }

    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getTags() {
        return ResponseEntity.ok(tagCategoryService.getAllTags());
    }
}
