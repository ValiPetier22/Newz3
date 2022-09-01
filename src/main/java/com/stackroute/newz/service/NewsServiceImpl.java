package com.stackroute.newz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.newz.model.News;
import com.stackroute.newz.repository.NewsRepository;
import com.stackroute.newz.service.NewsService;
import com.stackroute.newz.util.exception.NewsAlreadyExistsException;
import com.stackroute.newz.util.exception.NewsNotExistsException;

@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	NewsRepository newsRepository;

	public News addNews(News news) throws NewsAlreadyExistsException {
		News resultNews=newsRepository.getOne(news.getNewsId());
		if (resultNews==null) {
			return newsRepository.save(news);
		}
		throw new NewsAlreadyExistsException();
	}

	public News getNews(int newsId) throws NewsNotExistsException {
		Optional<News> existNews=newsRepository.findById(newsId);
		if (existNews.isEmpty()){
			throw new NewsNotExistsException();
		}
		else {
			return existNews.get();
		}
	}

	public List<News> getAllNews() {
		return newsRepository.findAll();
	}

	public News updateNews(News news) throws NewsNotExistsException {
		News existNews=newsRepository.getOne(news.getNewsId());
		if (existNews==null){
			throw new NewsNotExistsException();
		}
		else {
			return newsRepository.saveAndFlush(news);
		}
	}

	public void deleteNews(int newsId) throws NewsNotExistsException {
	News existNews=newsRepository.getOne(newsId);
	if (existNews==null){
		throw new NewsNotExistsException();
	}
	else {
		newsRepository.deleteById(newsId);
	}

	}

}
