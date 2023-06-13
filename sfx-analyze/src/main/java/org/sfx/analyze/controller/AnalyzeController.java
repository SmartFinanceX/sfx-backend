package org.sfx.analyze.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analyze")
public class AnalyzeController {

    @GetMapping("/test")
    public String AnalyzeControllerTest() {
        return "Success Get Analyze Server";
    }
}
