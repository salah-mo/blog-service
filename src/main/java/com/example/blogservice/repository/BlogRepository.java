package com.example.blogservice.repository;

import com.example.blogservice.model.Blog;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface BlogRepository extends MongoRepository<Blog, String> {
    List<Blog> findByTitle(String title);
}
