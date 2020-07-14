package com.example.blog.controllers;

import com.example.blog.models.Article;
import com.example.blog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Article> articles = postRepository.findAll();//select * from DB
        model.addAttribute("articles", articles);
        return "blog/blog_main";
    }

    @GetMapping("/blog/add")
    public String blogAdd() {
        return "blog/blog_add";
    }

    @PostMapping("/blog/add")
//Имя у параметра которое с аннотацией должно совпадать с тем, что указано в html разметке в поле ' name = "..." '
    public String blogPostAdd(Model model, @RequestParam String title, @RequestParam String anons, @RequestParam String fullText) {
        Article article = new Article(title, anons, fullText);
        postRepository.save(article);//insert into DB
        return "redirect:/blog";
    }

    @GetMapping("/blog/{articleId}")
    public String articleDetails(Model model, @PathVariable(value = "articleId") Long articleId) {
        if (postRepository.existsById(articleId)){
            Article article = postRepository.findById(articleId).get();
            model.addAttribute("article", article);
            return "blog/blog_details";
        } else {
            return "redirect:/blog";
        }
    }

    @GetMapping("/blog/{articleId}/edit")
    public String articleEdit(Model model, @PathVariable(value = "articleId") Long articleId) {
        if (postRepository.existsById(articleId)){
            Article article = postRepository.findById(articleId).get();
            model.addAttribute("article", article);
            return "blog/blog_edit";
        } else {
            return "redirect:/blog";
        }
    }

    @PostMapping("/blog/{articleId}/edit")
//Имя у параметра которое с аннотацией должно совпадать с тем, что указано в html разметке в поле ' name = "..." '
    public String blogPostEdit(@RequestParam String title,
                               @RequestParam String anons,
                               @RequestParam String fullText,
                               @PathVariable(value = "articleId") Long articleId) {
        Article article = postRepository.findById(articleId).get();
        article.setAnons(anons);
        article.setTitle(title);
        article.setFullText(fullText);
        postRepository.save(article);//update object in DB
        return "redirect:/blog";
    }

    @PostMapping("/blog/{articleId}")
    public String blogPostDelete(Model model,
                               @PathVariable(value = "articleId") Long articleId) {
        Article article = postRepository.findById(articleId).get();
        postRepository.delete(article);//delete object in DB
        return "redirect:/blog";
    }
}
