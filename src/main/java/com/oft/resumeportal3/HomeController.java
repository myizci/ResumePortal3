package com.oft.resumeportal3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller

public class HomeController {

    @GetMapping("/welcome")
    public String welcome(){
        return "/welcome";
    }

    @GetMapping({"/login", "/"})
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login2(){
        return "welcome";
    }
}
