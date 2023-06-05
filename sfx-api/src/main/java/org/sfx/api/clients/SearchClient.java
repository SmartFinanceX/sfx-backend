package org.sfx.api.clients;

import org.sfx.api.domain.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("searchservice")
@RequestMapping("/_search")
public interface SearchClient {
    @GetMapping("_ticker/{ticker}")
    ResponseResult searchByTicker(@PathVariable String ticker);
    @GetMapping("_all/{keyWord}")
    ResponseResult searchByKeyWord(@PathVariable String keyWord);
    @GetMapping("_all/{keyWord}/{page}")
    ResponseResult searchByKeyWord(@PathVariable String keyWord, @PathVariable int page);
}
