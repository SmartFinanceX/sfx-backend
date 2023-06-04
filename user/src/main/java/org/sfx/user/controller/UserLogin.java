package org.sfx.user.controller;

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
    @PreAuthorize("hasAuthority('p1')")
    @GetMapping("/p1")
    public String GetP1() {
        return "p1";
    }
    @PreAuthorize("hasAuthority('p2')")
    @GetMapping("/p2")
    public String GetP2() {
        return "p2";
    }
}
