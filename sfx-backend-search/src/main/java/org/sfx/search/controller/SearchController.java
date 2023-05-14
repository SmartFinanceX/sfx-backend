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
}
