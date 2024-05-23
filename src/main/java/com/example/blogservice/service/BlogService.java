package com.example.blogservice.service;

import com.example.blogservice.model.Blog;
import com.example.blogservice.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public Blog saveBlog(MultipartFile file, String title, String summary, String image, String author, String[] keywords) throws IOException {
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setSummary(summary);
        blog.setImage(image);
        blog.setAuthor(author);
        blog.setCreatedAt(new Date());
        blog.setKeywords(List.of(keywords));
        blog.setEnabled(true);

        // Parse the content of the MD file into text content type
        Blog.Content textContent = new Blog.Content();
        textContent.setType("text");
        textContent.setData(new String(file.getBytes()));

        // Add the content to the blog
        List<Blog.Content> contents = new ArrayList<>();
        contents.add(textContent);

        // Assuming an image content example
        Blog.Content imageContent = new Blog.Content();
        imageContent.setType("image");
        imageContent.setData(image); // use the image file name or URL

        contents.add(imageContent);
        blog.setContent(contents);

        return blogRepository.save(blog);
    }

    public List<Blog> getBlogsByTitle(String title) {
        return blogRepository.findByTitle(title);
    }
}
