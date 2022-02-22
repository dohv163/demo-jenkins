package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/api/v1/test")
public class TestController {

  @GetMapping(name = "/hello")
  public String helloWord(){
    return "Hello-word";
  }

}
