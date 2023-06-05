package org.sfx.core.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/test")
    @PreAuthorize("hasAnyAuthority('test')")
    public String testEcho() {
        return "Has permission TEST";
    }

}
