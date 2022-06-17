package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dto.TestDTO;
import com.example.demo.service.mqservice.MqProducerService;
import com.example.demo.utils.DateUtil;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/test")
public class TestController {


  private final MqProducerService mqProducerService;

//  @Autowired
//  private KafkaTemplate<Object, Object> kafkaTemplate;

//  @Autowired
//  private Producer<Object, Object> producer;

//  @GetMapping(value = "/hello")
//  public String helloWord(@RequestParam(required = false, value = "test_params") String testParam){
//    System.out.println("Hello word!" + testParam);
//    HashMap<String, Object> params = new HashMap<>();
//    TestDTO testDTO = new TestDTO();
//    testDTO.setName("dohv");
//    testDTO.setAddress("Thuong Tin");
//
//    List<TestDTO> list = new ArrayList<>();
//    list.add(testDTO);
//    params.put("testDTO", JSON.toJSONString(list));
//    params.put("time", DateUtil.format(LocalDateTime.now()));
//    mqProducerService.rankUserFollowDirectMqSend(params);
//    return "Hello-word";
//  }

  @GetMapping(value = "/hello-v3")
  public String helloWord(HttpServletRequest servletRequest){


    TestDTO test = new TestDTO();
    test.setEnv("fbang_server");
    test.setAddress("");
    test.setName("dohv");
    Map<String, String> mapHeader = getRequestHeadersInMap(servletRequest);
    System.out.println("Remote IP: "+servletRequest.getRemoteAddr());
    System.out.println("x-forwarded-for "+ mapHeader.get("x-forwarded-for"));
    System.out.println("x-real-ip " +mapHeader.get("x-real-ip"));
    System.out.println("user-agent " +mapHeader.get("user-agent"));
//    kafkaTemplate.send("fbang-server-test-create-topic","test_key", JSON.toJSONString(test));
//    List<LinkedHashMap<String, Object>> data = new LinkedList<>();
//    LinkedHashMap<String, Object> eventData = new LinkedHashMap<>();
//    eventData.put("test", JSON.toJSONString(test));
//    data.add(eventData);
//    System.out.println("Path info:  "+servletRequest.getPathInfo());
//    System.out.println("Request URI"+ servletRequest.getRequestURI());
//    System.out.println("Request url"+ servletRequest.getRequestURL());
//    mqProducerService.liveStreamStatisticsMqSend(data);

    //servletRequest.

    //System.out.println(servletRequest.getHeaders("User-Agent").toString());




    return test.toString();
  }
  public enum EnumTest{
    TEST("test");
    final String value;

    EnumTest(String value) {
      this.value = value;
    }
    final static Map<String, Object> CONSTANTS = new HashMap<>();

    static {
      for (EnumTest c : values()) {
        CONSTANTS.put(c.name(), c.value);
      }
    }
  }

  private Map<String, String> getRequestHeadersInMap(HttpServletRequest request) {

    Map<String, String> result = new HashMap<>();

    Enumeration headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String key = (String) headerNames.nextElement();
      String value = request.getHeader(key);
      result.put(key, value);
    }

    return result;
  }



}
