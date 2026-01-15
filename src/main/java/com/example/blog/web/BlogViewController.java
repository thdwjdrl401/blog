package com.example.blog.web;

import com.example.blog.service.post.PublicPostService;
import com.example.blog.web.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class BlogViewController {

    private final PublicPostService publicPostService;

    @GetMapping("/")
    public String home(
            @PageableDefault(size = 20, sort = "publishedAt", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {
        Page<PostResponse> posts = publicPostService.getPosts(pageable);
        model.addAttribute("posts", posts);
        return "home";
    }

    @GetMapping("/posts/{slug}")
    public String post(@PathVariable("slug") String slug, Model model) {
        PostResponse post = publicPostService.getPostBySlug(slug);
        model.addAttribute("post", post);
        return "post";
    }
}
