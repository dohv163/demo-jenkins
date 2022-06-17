package com.example.demo.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RankUserRabbitMqConfig {

  public static final String directExchange = "rank_user_direct_exchange";

  public static final String topFollowQueue = "top_follow_queue_test";
  public static final String topFollowRouter = "top_follow_router_test";

  public static final String topDonateQueue = "top_donate_queue";
  public static final String topDonateRouter = "top_donate_router";

  public static final String topCCVQueue = "top_ccv_queue";
  public static final String topCCVRouter = "top_ccv_router";



  @Bean
  public DirectExchange rankUserDirectExchange(){
    return new DirectExchange(directExchange, true, false);
  }

  @Bean
  public Queue topCCVQueue() {
    return new Queue(topCCVQueue, true, false, false);
  }

  @Bean
  public Binding topCCVBinding() {
    return BindingBuilder.bind(topCCVQueue()).to(rankUserDirectExchange()).with(topCCVRouter);
  }

  @Bean
  public Queue topFollowQueue() {
    return new Queue(topFollowQueue, true, false, false);
  }

  @Bean
  public Binding topFollowBinding() {
    return BindingBuilder.bind(topFollowQueue()).to(rankUserDirectExchange()).with(topFollowRouter);
  }

  @Bean
  public Queue topDonateQueue() {
    return new Queue(topDonateQueue, true, false, false);
  }

  @Bean
  public Binding topDonateBinding() {
    return BindingBuilder.bind(topDonateQueue()).to(rankUserDirectExchange()).with(topDonateRouter);
  }
}
