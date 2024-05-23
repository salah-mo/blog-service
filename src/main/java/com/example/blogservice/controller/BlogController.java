package com.example.blogservice.controller;

import com.example.blogservice.model.Blog;
import com.example.blogservice.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/upload")
    public Blog uploadBlog(@RequestParam("file") MultipartFile file,
                           @RequestParam("title") String title,
                           @RequestParam("summary") String summary,
                           @RequestParam("image") String image,
                           @RequestParam("author") String author,
                           @RequestParam("keywords") String[] keywords) throws IOException {
        return blogService.saveBlog(file, title, summary, image, author, keywords);
    }

    @GetMapping("/title/{title}")
    public List<Blog> getBlogsByTitle(@PathVariable String title) {
        return blogService.getBlogsByTitle(title);
    }
}
