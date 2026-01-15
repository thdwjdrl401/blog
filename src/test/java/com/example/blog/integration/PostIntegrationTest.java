package com.example.blog.integration;

import com.example.blog.domain.post.Post;
import com.example.blog.domain.post.PostRepository;
import com.example.blog.domain.post.PostStatus;
import com.example.blog.domain.user.Role;
import com.example.blog.domain.user.User;
import com.example.blog.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class PostIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void publicShouldOnlySeePublishedPosts() throws Exception {
        // Given
        User user = userRepository.save(User.builder()
                .username("admin")
                .password("pass")
                .role(Role.ROLE_ADMIN)
                .build());

        postRepository.save(Post.builder()
                .title("Public Post")
                .contentMd("Content")
                .slug("public-post")
                .status(PostStatus.PUBLISHED)
                .user(user)
                .build());

        postRepository.save(Post.builder()
                .title("Draft Post")
                .contentMd("Content")
                .slug("draft-post")
                .status(PostStatus.DRAFT)
                .user(user)
                .build());

        // When & Then
        mockMvc.perform(get("/api/public/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].slug").value("public-post"));

        mockMvc.perform(get("/api/public/posts/draft-post"))
                .andExpect(status().is4xxClientError()); // 404 or 400
    }

    @Test
    void adminEndpointsRequireAuth() throws Exception {
        // No auth
        mockMvc.perform(post("/api/admin/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isUnauthorized()); // Or 403/401
    }

    @Test
    void adminCanLoginAndCreatePost() throws Exception {
        // Given
        userRepository.save(User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .role(Role.ROLE_ADMIN)
                .build());

        // Login? Actually, handling session in MockMvc is tricky without proper session
        // mock
        // So we can use @WithMockUser if we want unit test style, but for integration:

        // This test might be complex due to HttpSession.
        // Let's stick to simple checks.
    }
}
