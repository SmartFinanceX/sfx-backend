package org.sfx.core.controller;

import org.sfx.core.domain.ResponseResult;
import org.sfx.core.service.IncBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inc")
public class IncInfoController {
    @Autowired
    IncBasicService incBasicService;

    @GetMapping
    public ResponseResult getAll() {
        return incBasicService.selectAll();
    }

    @GetMapping("/ticker/{ticker}")
    public ResponseResult getByTicker(@PathVariable String ticker) {
        return incBasicService.selectByTicker(ticker);
    }
}
