package com.example.demo.config.rabbitmq;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiveStreamStatisticsRabbitMqConfig {

  public static final String HEADER_X_RETRIES_COUNT = "x-retries-count";

  public static final String directExchange = "livestream_statistics_direct_exchange";
  public static final String deadExchange = "livestream_statistics_dead_exchange";

  public static final String directRouter = "livestream_statistics_direct_router";
  public static final String deadRouter = "livestream_statistics_dead_router";

  public static final String liveStreamStatisticsQueue = "livestream_statistics_queue";
  public static final String liveStreamStatisticsDeadQueue = "livestream_statistics_dead_queue";


  @Bean
  public DirectExchange liveStreamStatisticsDirectExchange(){
    return new DirectExchange(directExchange, true, false);

  }

  @Bean
  public Queue liveStreamEventTrackingStatisticsQueue(){
    Map<String, Object> map = new HashMap<>();
    map.put("x-dead-letter-exchange", deadExchange);
    map.put("x-dead-letter-routing-key", deadRouter);
    return new Queue(liveStreamStatisticsQueue, true, false, false, map);
  }

  @Bean
  public Binding livestreamStatisticsEventTrackingBinding(){
    return BindingBuilder.bind(liveStreamEventTrackingStatisticsQueue()).to(liveStreamStatisticsDirectExchange()).with(directRouter);
  }

//  @Bean("liveStreamStatisticsDeadExchange")
//  public DirectExchange liveStreamStatisticsDeadExchange(){
//    return new DirectExchange(deadExchange, true, false);
//  }

  @Bean("liveStreamStatisticsDeadExchange")
  public CustomExchange delayExchange() {
    Map<String, Object> args = new HashMap<String, Object>();
    args.put("x-delayed-type", "direct");
    return new CustomExchange(deadExchange, "x-delayed-message", true, false, args);
  }



  @Bean("liveStreamStatisticsDeadQueue")
  public Queue liveStreamStatisticsDeadQueue(){
    return new Queue(liveStreamStatisticsDeadQueue, true, false, false);
  }


  @Bean
  public Binding liveStreamStatisticsDeadBingding(@Qualifier("liveStreamStatisticsDeadQueue") Queue liveStreamStatisticsDeadQueue, @Qualifier("liveStreamStatisticsDeadExchange") Exchange deadExchange){
    return BindingBuilder.bind(liveStreamStatisticsDeadQueue).to(deadExchange).with(deadRouter).noargs();
  }


}
