package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/test")
public class TestController {

  @GetMapping("/hello-word")
  public String helloWord(){

    return "Hello - Hoang Van Do";
  }


}
