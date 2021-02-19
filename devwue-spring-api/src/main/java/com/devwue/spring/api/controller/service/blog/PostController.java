package com.devwue.spring.api.controller.service.blog;

import com.devwue.spring.api.dto.request.PostCreateRequest;
import com.devwue.spring.api.dto.request.PostSearchRequest;
import com.devwue.spring.api.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public Object detail(@PathVariable String postNo) throws Exception {
        return blogService.findPostByPostNo(postNo);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public Object create(@RequestBody PostCreateRequest request) {
        return blogService.createPost(request);
    }

    @GetMapping("/search")
    public Object search(@RequestParam("name") String name,
                         @RequestParam(name="page", defaultValue = "0") int page,
                         @RequestParam(name="size", defaultValue = "10") int size,
                         @RequestParam(name="sort", defaultValue = "id") String sort) {
        PostSearchRequest request = new PostSearchRequest();
        request.setName(name);
        return blogService.searchPost(request, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)));
    }

    @GetMapping("/group")
    public Object group() {
        return blogService.getSummaryType();
    }
}
