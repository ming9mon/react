package com.react.backend.react.controller;

import com.react.backend.react.model.Testaa;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestaaaController {

    @PostMapping("/testaa")
    public String testaa(@RequestBody Testaa test) {
        System.out.println(test.getName());
        return test.getName();
    }

    @PostMapping("/abcd")
    public String abcd(@RequestBody Testaa test) {
        return "abcd";
    }
}
