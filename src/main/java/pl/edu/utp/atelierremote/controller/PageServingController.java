package pl.edu.utp.atelierremote.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageServingController {

    @GetMapping("/")
    public String serveSite() {
        return "index.html";
    }

    @RequestMapping("/**/{path:[^\\.]+}")
    public String forward() {
        return "forward:/";
    }

}
