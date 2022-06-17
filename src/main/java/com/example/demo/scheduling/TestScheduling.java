package com.example.demo.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
public class TestScheduling {


  // Every first day of month
  @Scheduled(cron = "0 0 0 1 * ? ")
  public void refreshLeaderBoardMonthAgo(){

    log.info("refreshLeaderBoardMonthAgo");



  }



  // Every first day of week
  @Scheduled(cron = "0 0 0 ? * MON ")
  public void refreshLeaderBoardWeekAgo(){

    log.info("refreshLeaderBoardWeekAgo");


  }



  // Every first day
  @Scheduled(cron = "0 0 0 ? * * ")
  public void refreshLeaderBoardDayAgo(){

    log.info("refreshLeaderBoardDayAgo");

  }

  @Scheduled(cron = "0 0 0 ? * * ")
  public void refreshLeaderBoard(){
    log.info("refreshLeaderBoard");


  }

}
