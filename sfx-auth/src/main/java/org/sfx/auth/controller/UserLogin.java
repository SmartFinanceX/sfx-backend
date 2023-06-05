package org.sfx.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("r")
public class UserLogin {

    @GetMapping("test")
    public String TestLogin() {
        return "Test Info";
    }
    @PreAuthorize("hasAuthority('test')")
    @GetMapping("/auth/test")
    public String GetP1() {
        
        return "认证成功";
    }
    @PreAuthorize("hasAuthority('p2')")
    @GetMapping("/p2")
    public String GetP2() {
        return "p2";
    }
}
