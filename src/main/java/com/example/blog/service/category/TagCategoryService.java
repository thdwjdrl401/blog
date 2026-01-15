package com.example.blog.service.category;

import com.example.blog.domain.category.Category;
import com.example.blog.domain.category.CategoryRepository;
import com.example.blog.domain.tag.Tag;
import com.example.blog.domain.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class TagCategoryService {

    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public Category getOrCreateCategory(String name) {
        if (name == null || name.isBlank())
            return null;
        return categoryRepository.findByName(name)
                .orElseGet(() -> categoryRepository.save(new Category(name)));
    }

    public Set<Tag> getOrCreateTags(List<String> tagNames) {
        Set<Tag> tags = new HashSet<>();
        if (tagNames == null || tagNames.isEmpty())
            return tags;

        for (String name : tagNames) {
            Tag tag = tagRepository.findByName(name)
                    .orElseGet(() -> tagRepository.save(new Tag(name)));
            tags.add(tag);
        }
        return tags;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Category createCategory(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new IllegalArgumentException("Category already exists");
        }
        return categoryRepository.save(new Category(name));
    }

    public Category updateCategory(Long id, String name) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        if (!category.getName().equals(name) && categoryRepository.existsByName(name)) {
            throw new IllegalArgumentException("Category name already exists");
        }
        category.updateName(name);
        return category;
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Tag createTag(String name) {
        if (tagRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Tag already exists");
        }
        return tagRepository.save(new Tag(name));
    }

    public Tag updateTag(Long id, String name) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found"));
        if (!tag.getName().equals(name) && tagRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Tag name already exists");
        }
        tag.updateName(name);
        return tag;
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
