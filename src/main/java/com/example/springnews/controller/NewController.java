package com.example.springnews.controller;

import com.example.springnews.model.News;
import com.example.springnews.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class NewController {
    @Autowired
    NewsRepository newsRepository;

    @GetMapping("/newsmain")
    public ModelAndView list() {
        List<News> list = newsRepository.findAll();
        ModelAndView mav = new ModelAndView();
        if (!list.isEmpty()) {
            mav.addObject("list", list);
        } else {
            mav.addObject("msg", "추출된 결과가 없어요");
        }
        mav.setViewName("newsView");
        return mav;
    }

    @GetMapping(value ="/news/one", produces = "application/json; charset=utf-8")
    @ResponseBody
    @Transactional
    public News one(int id) {
        try {
            News oldvo = newsRepository.findById(id).get();
            oldvo.setCnt(oldvo.getCnt());
        } catch(Exception e) {
            System.out.println("오류발생");
        }
        return newsRepository.findById(id).get();
    }

    @GetMapping("/news/delete")
    @Transactional
    public ModelAndView delete(int id) {
        ModelAndView mav = new ModelAndView();
        try {
            newsRepository.deleteById(id);
            mav.addObject("list", newsRepository.findAll());
        } catch(Exception e) {
            mav.addObject("msg", "삭제를 처리하는 동안 오류 발생");
        }
        mav.setViewName("newsView");
        return mav;
    }

    @GetMapping("/news/search")
    public ModelAndView search(String content) {
        List<News> list = newsRepository.findByContentContains(content);
        ModelAndView mav = new ModelAndView();
        if (!list.isEmpty()) {
            mav.addObject("list", list);
            mav.addObject("button", "메인화면으로");
        } else {
            mav.addObject("msg", "추출된 결과가 없어요");
        }
        mav.setViewName("newsView");
        return mav;
    }

    @GetMapping("/news/writer")
    public ModelAndView writerSearch(String writer) {
        List<News> list = newsRepository.findByWriterContains(writer);
        ModelAndView mav = new ModelAndView();
        if (!list.isEmpty()) {
            mav.addObject("list", list);
        } else {
            mav.addObject("msg", "추출된 결과가 없어요");
        }
        mav.setViewName("newsView");
        return mav;
    }

    @PostMapping("/news/insert")
    @Transactional
    public ModelAndView insert(News vo) {
        System.out.println(vo);
        ModelAndView mav = new ModelAndView();
        try {
            newsRepository.save(vo);
            mav.addObject("list", newsRepository.findAll());
        } catch(Exception e) {
            mav.addObject("msg", "글 작성을 처리하는 동안 오류 발생");
        }
        mav.setViewName("newsView");
        return mav;
    }

    @PostMapping("/news/update")
    @Transactional
    public ModelAndView update(News vo) {
        ModelAndView mav = new ModelAndView();
        try {
            News oldvo = newsRepository.findById(vo.getId()).get();
            oldvo.setWriter(vo.getWriter());
            oldvo.setTitle(vo.getTitle());
            oldvo.setContent(vo.getContent());
            mav.addObject("list", newsRepository.findAll());
        } catch(Exception e) {
            mav.addObject("msg", "글 작성을 수정하는 동안 오류 발생");
        }
        mav.setViewName("newsView");
        return mav;
    }
}
