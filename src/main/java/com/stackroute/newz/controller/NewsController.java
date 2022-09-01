package com.stackroute.newz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.newz.model.News;
import com.stackroute.newz.service.NewsService;
import com.stackroute.newz.util.exception.NewsAlreadyExistsException;
import com.stackroute.newz.util.exception.NewsNotExistsException;

import javax.websocket.server.PathParam;

@RestController
public class NewsController {
	@Autowired
	NewsService newsService;

	@GetMapping("/api/v1/news")
	public ResponseEntity<List<News>> viewNews()
	{
		List<News> news=newsService.getAllNews();
		return new ResponseEntity<List<News>>(news,HttpStatus.OK);
	}

	@GetMapping("api/v1/news/{newsId}")
	public ResponseEntity<?> getNewsById(@PathVariable int newsId){
		try{
			return new ResponseEntity<News>(newsService.getNews(newsId),HttpStatus.OK);
		}catch (NewsNotExistsException e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("api/v1/news")
	public ResponseEntity<?> createNews(@RequestBody News news){
		try {
			return new ResponseEntity<News>(newsService.addNews(news),HttpStatus.CREATED);
		}catch (NewsAlreadyExistsException n){
			return new ResponseEntity<String>(n.getMessage(),HttpStatus.CONFLICT);
		}
	}

	@PutMapping("/api/v1/news/{newsId}")
	public ResponseEntity<?> updateNews(@RequestBody News news){
		try {
			return new ResponseEntity<News>(newsService.updateNews(news),HttpStatus.OK);
		}catch (NewsNotExistsException u){
			return new ResponseEntity<String>(u.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/api/v1/news/{newsId}")
	public ResponseEntity<?> deleteNewsById(@PathVariable int newsId){
		try{
			newsService.deleteNews(newsId);
			return new ResponseEntity<String>("Deleted successfully", HttpStatus.OK);
		}catch (NewsNotExistsException d){
			return new ResponseEntity<String>(d.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

}
