package com.example.blog.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Public queries (Published only)
    Page<Post> findByStatus(PostStatus status, Pageable pageable);

    Optional<Post> findBySlugAndStatus(String slug, PostStatus status);

    @Query("SELECT p FROM Post p WHERE p.status = 'PUBLISHED' AND (p.title LIKE %:keyword% OR p.contentMd LIKE %:keyword%)")
    Page<Post> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    Page<Post> findByCategory_NameAndStatus(String categoryName, PostStatus status, Pageable pageable);

    Page<Post> findByTags_NameAndStatus(String tagName, PostStatus status, Pageable pageable);

    boolean existsBySlug(String slug);
}
