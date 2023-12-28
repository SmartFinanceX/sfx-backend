package org.sfx.analyze.controller;

import org.sfx.analyze.service.AnalyzeService;
import org.sfx.api.domain.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/inc_analyze")
public class AnalyzeController {
    @Autowired
    AnalyzeService analyzeService;

    @GetMapping("/{ticker}/{idx}")
    public ResponseResult<ArrayList<String>> AnalyzeController(@PathVariable String ticker, @PathVariable int idx) {
        switch (idx) {
            case 1:
                return analyzeService.GetData1(ticker);
            case 2:
                return analyzeService.GetData2(ticker);
            case 3:
                return analyzeService.GetData3(ticker);
            case 4:
                return analyzeService.GetData4(ticker);
            default:
                return new ResponseResult<>(4303, "No Such Category");
        }
    }
}
