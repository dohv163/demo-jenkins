//package com.example.demo.service.mqservice;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//public class KafkaConsumerService {
//
//  @KafkaListener(topics = {"fbang.server_stream_comment"}, groupId = "ftech.fbang.local.group")
//  public void test(String content){
//
//    log.info("received content =:"+ content);
//
//  }
//
//
//
//  @KafkaListener(topics = {"fbang.server_streamer_open_giveaway"}, groupId = "ftech.fbang.local.group")
//  public void test1(String content){
//
//    log.info("received content =:"+ content);
//
//  }
//
//
//}
