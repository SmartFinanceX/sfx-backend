package org.sfx.search.controller;

import org.sfx.api.domain.ResponseResult;
import org.sfx.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/_search")
public class SearchController {
    @Autowired
    SearchService searchService;
    @GetMapping("_ticker/{ticker}")
    ResponseResult searchByTicker(@PathVariable String ticker) {
        return searchService.searchByTicker(ticker);
    }
    @GetMapping("_all/{keyWord}")
    ResponseResult searchByKeyWord(@PathVariable String keyWord) {
        return searchService.searchByKeyWord(keyWord);
    }
    @GetMapping("_all/{keyWord}/{page}")
    ResponseResult searchByKeyWord(@PathVariable String keyWord, @PathVariable int page) {
        return searchService.searchByKeyWord(keyWord,page,10);
    }
}
