package vn.edu.hcmuaf.apiNews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.apiNews.entity.News;
import vn.edu.hcmuaf.apiNews.model.dto.NewsDto;
import vn.edu.hcmuaf.apiNews.model.dto.UpdateNews;
import vn.edu.hcmuaf.apiNews.service.CategoryService;
import vn.edu.hcmuaf.apiNews.service.NewsService;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CategoryService categoryService;

    // get all news
    @GetMapping
    public List<NewsDto> getAllNews() {
        return newsService.getAllNews();
    }

    // get all news
    @GetMapping("/all")
    public List<NewsDto> getNewsAll() {
        return newsService.getNewsAll();
    }


    // get all news hidden
    @GetMapping("/hidden")
    public List<NewsDto> getAllNewsHidden() {
        return newsService.getAllNewsHidden();
    }

    // get news by id
    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable long id) {
        NewsDto news = newsService.getNewsById(id);
        return news != null ? ResponseEntity.ok(news) : ResponseEntity.notFound().build();
    }

    // get news by category
    @GetMapping("/cate/{id}")
    public ResponseEntity<List<NewsDto>> getNewsByCategory(@PathVariable long id) {
        List<NewsDto> news = categoryService.getNewsByCategory(id);
        return news != null ? ResponseEntity.ok(news) : ResponseEntity.notFound().build();
    }

    // create news
    @PostMapping
    public ResponseEntity<?> createNews(@RequestBody UpdateNews updateNews) {
        String createdNews = newsService.createNews(updateNews);
        return new ResponseEntity<>(createdNews, HttpStatus.CREATED);
    }

    // update news
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNews(@PathVariable long id, @RequestBody UpdateNews updateNews) {
        String updatedNews = newsService.updateNews(id, updateNews);
        return new ResponseEntity<>(updatedNews ,HttpStatus.OK);
    }

    // update news hidden
    @PutMapping("/hidden/{id}")
    public ResponseEntity<String> updateNewsHidden(@PathVariable long id) {
        newsService.updateNewsHidden(id);
        return ResponseEntity.ok("hidden news!");
    }

    // delete news
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable long id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }

    // get news flash
    @GetMapping("/flash")
    public List<NewsDto> getNewsFlash() {
        return newsService.getNewsFlash();
    }

    // get news flash by category
    @GetMapping("/flash/{id}")
    public List<NewsDto> getNewsFlashByCategory(@PathVariable long id) {
        return newsService.getNewsFlashByCategory(id);
    }
}

