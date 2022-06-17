package com.example.demo.service.mqservice;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.rabbitmq.LiveStreamStatisticsRabbitMqConfig;
import com.example.demo.config.rabbitmq.RankUserRabbitMqConfig;
import com.example.demo.dto.TestDTO;
import com.example.demo.utils.DateUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqConsumerService {

  private static final int MAX_RETRIES_COUNT = 3;

  private static final int TIME_WAITING_RE_SEND_DEAD_MESSAGE =  5 * 1000;

//  @Autowired
//  LiveStreamStatisticsService liveStreamStatisticsService;

  @Autowired
  private MqProducerService mqProducerService;

//  @Autowired
//  private KafkaTemplate<Object, Object> kafkaTemplate;

  @RabbitHandler
  @RabbitListener(queues = LiveStreamStatisticsRabbitMqConfig.liveStreamStatisticsQueue)
  public void sendLiveStreamStatisticsForEventTracking(Message message) throws Exception {
    log.info("Message info {}", message.toString());
    //ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(message.getBody()));
   // List<LinkedHashMap<String, Object>> liveStreamStatistics = (List<LinkedHashMap<String, Object>>) ois.readObject();
    if (true){
      log.info("Push Statistic Live Stream Event Tracking false");
      throw new Exception("Push Statistic Live Stream Event Tracking false");
    }
  }

  @RabbitHandler
  @RabbitListener(queues = LiveStreamStatisticsRabbitMqConfig.liveStreamStatisticsDeadQueue)
  public void reSendLiveStreamStatisticsForEventTracking(Message failedMessage) throws Exception {

    Integer retriesCnt = (Integer) failedMessage.getMessageProperties().getHeaders().get(LiveStreamStatisticsRabbitMqConfig.HEADER_X_RETRIES_COUNT);
    if (retriesCnt == null)
      retriesCnt = 1;
    if (retriesCnt > MAX_RETRIES_COUNT) {

      log.info("Discarding messageeeeeeeeeeeeeeeeeeeeeeeee");
      return;

//      throw new Exception("Message over time retry ");
    }
    log.info("Retrying message for the {} time", retriesCnt);
    failedMessage.getMessageProperties().getHeaders().put(LiveStreamStatisticsRabbitMqConfig.HEADER_X_RETRIES_COUNT, ++retriesCnt);
    //failedMessage.getMessageProperties().setDelay(retriesCnt * TIME_WAITING_RE_SEND_DEAD_MESSAGE);
    log.info("fail message info {}",failedMessage.toString());
    mqProducerService.liveStreamStatisticsReMqSend(failedMessage,retriesCnt * TIME_WAITING_RE_SEND_DEAD_MESSAGE);
  }

  @RabbitHandler
  @RabbitListener(queues = RankUserRabbitMqConfig.topDonateQueue)
  public void handleQueueDonate(HashMap<String, Object> params){
    log.info("Consumer handleQueueDonate of user {}");

  }

  @RabbitHandler
  @RabbitListener(queues = RankUserRabbitMqConfig.topFollowQueue)
  public void handleQueueFollow(List<LinkedHashMap<String, Object>> params) throws IOException, ClassNotFoundException{
    //log.info("Consumer handleQueueFollow of user {}", DateUtil.parse(String.valueOf(params.get("time"))));

    //List<LinkedHashMap<String, Object>> eventSocketList = (List<LinkedHashMap<String, Object>>) ois.readObject();
    System.out.println(params.get(0).get("test"));

  }


  @RabbitListener(queues = RankUserRabbitMqConfig.topCCVQueue)
  @RabbitHandler
  public void topUserCCV(Message message) throws IOException, ClassNotFoundException {

    //Integer anchorId = (Integer) params.get(ANCHOR_ID_MQ_PARAM);
    log.info("Consumer CCV of user {}");



  }


//  @KafkaListener(topics = "fbang_test_topic", groupId = "99999")
//  public void process(String content) {
//    System.out.println(content);
//  }


}
