package org.sfx.core.controller;

import org.sfx.core.domain.ResponseResult;
import org.sfx.core.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index_")
@Deprecated
public class IndexController {
    @Autowired
    SearchService searchService;

    /**
     * 刷新所有的公司基础信息
     * @return
     */
    @PutMapping
    public ResponseResult flushAllIndex()
    {
        return searchService.flushAllIndex();
    }

}
