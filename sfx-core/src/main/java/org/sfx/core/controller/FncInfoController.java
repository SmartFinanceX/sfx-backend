package org.sfx.core.controller;

import org.sfx.core.domain.ResponseResult;
import org.sfx.core.service.FinaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/_fnc")
public class FncInfoController {
    @Autowired
    FinaInfoService finaInfoService;
    @GetMapping("/{ticker}/{category}")
    ResponseResult getFinanceReportInfo(@PathVariable String ticker,@PathVariable Short category) {
        return finaInfoService.getFinanceReportInfo(ticker,category);
    }
}
