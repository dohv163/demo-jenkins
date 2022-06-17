package com.example.demo.service.mqservice;

import com.example.demo.config.rabbitmq.LiveStreamStatisticsRabbitMqConfig;
import com.example.demo.config.rabbitmq.RankUserRabbitMqConfig;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqProducerService {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  /**
   *
   * @param liveStreamStatistics
   */
  public void liveStreamStatisticsMqSend(List<LinkedHashMap<String, Object>> liveStreamStatistics){
    this.rabbitTemplate.convertAndSend(LiveStreamStatisticsRabbitMqConfig.directExchange, LiveStreamStatisticsRabbitMqConfig.directRouter, liveStreamStatistics);

  }

  /**
   *
   * @param message
   * @param delayTime
   */
  public void liveStreamStatisticsReMqSend(Message message, int delayTime){
    MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
      @Override
      public Message postProcessMessage(Message message) throws AmqpException {
        //message.getMessageProperties().setExpiration(String.valueOf(delayTime));
        message.getMessageProperties().setDelay(delayTime);
        message.getMessageProperties().setContentEncoding("UTF-8");
        return message;
      }
    };
    this.rabbitTemplate.convertAndSend(LiveStreamStatisticsRabbitMqConfig.directExchange, LiveStreamStatisticsRabbitMqConfig.directRouter,message, messagePostProcessor);

  }

  /**
   *  Rank user fan-out
   */
  public void rankUserFollowDirectMqSend(List<LinkedHashMap<String, Object>> params){
    this.rabbitTemplate.convertAndSend(RankUserRabbitMqConfig.directExchange, RankUserRabbitMqConfig.topFollowRouter, params);
  }

  public void rankUserDonateDirectMqSend(HashMap<String, Object> params){
    MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
      @Override
      public Message postProcessMessage(Message message) throws AmqpException {
        message.getMessageProperties().setDelay(500000);
        message.getMessageProperties().setContentEncoding("UTF-8");
        return message;
      }
    };
    this.rabbitTemplate.convertAndSend(RankUserRabbitMqConfig.directExchange, RankUserRabbitMqConfig.topDonateRouter, params,messagePostProcessor);
  }

  public void rankCCVUserMqSend(HashMap<String, Object> params){
    this.rabbitTemplate.convertAndSend(RankUserRabbitMqConfig.directExchange, RankUserRabbitMqConfig.topCCVRouter, params);

  }

}
