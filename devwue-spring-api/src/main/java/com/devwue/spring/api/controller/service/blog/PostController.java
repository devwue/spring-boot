package com.devwue.spring.api.controller.service.blog;

import com.devwue.spring.api.dto.request.PostCreateRequest;
import com.devwue.spring.api.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    private final BlogService blogService;

    @Autowired
    public PostController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("")
    public Object index() {
        return blogService.findByName("test");
    }
    @GetMapping("/{postNo}")
    @ResponseStatus(HttpStatus.OK)
    public Object index(@PathVariable String postNo) throws Exception {
        return blogService.findPostByPostNo(postNo);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public Object create(@RequestBody PostCreateRequest request) {
        return blogService.createPost(request);
    }
}
