package com.example.demo.config.rabbitmq;

import java.util.Map;
import org.springframework.amqp.core.AbstractExchange;

public class DelayDirectExchange extends AbstractExchange {

  public static final DelayDirectExchange DEFAULT = new DelayDirectExchange("x-delayed-message");

  public DelayDirectExchange(String name) {
    super(name);
  }

  public DelayDirectExchange(String name, boolean durable, boolean autoDelete) {
    super(name, durable, autoDelete);
  }

  public DelayDirectExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> arguments) {
    super(name, durable, autoDelete, arguments);
  }

  @Override
  public String getType() {
    return "direct";
  }
}
