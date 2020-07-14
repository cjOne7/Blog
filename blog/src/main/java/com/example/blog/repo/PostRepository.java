package com.example.blog.repo;

import com.example.blog.models.Article;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Article, Long> {
}
