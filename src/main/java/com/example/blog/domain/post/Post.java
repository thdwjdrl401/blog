package com.example.blog.domain.post;

import com.example.blog.domain.category.Category;
import com.example.blog.domain.tag.Tag;
import com.example.blog.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contentMd;

    @Column(length = 500)
    private String excerpt;

    @Column(length = 255)
    private String coverImageUrl;

    @Column(unique = true, length = 255)
    private String slug;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PostStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime publishedAt;

    @Builder
    public Post(String title, String contentMd, String excerpt, String coverImageUrl, String slug, PostStatus status,
            User user, Category category) {
        this.title = title;
        this.contentMd = contentMd;
        this.excerpt = excerpt;
        this.coverImageUrl = coverImageUrl;
        this.slug = slug;
        this.status = status;
        this.user = user;
        this.category = category;
    }

    public void update(String title, String contentMd, String excerpt, String coverImageUrl, String slug,
            PostStatus status, Category category) {
        this.title = title;
        this.contentMd = contentMd;
        this.excerpt = excerpt;
        this.coverImageUrl = coverImageUrl;
        this.slug = slug;
        this.status = status;
        this.category = category;

        if (this.status == PostStatus.PUBLISHED && publishedAt == null) {
            this.publishedAt = LocalDateTime.now();
        }
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
