package com.example.demo.dto;

import com.example.demo.utils.ServletUtils;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TestDTO {

  @NotEmpty
  String name;

  @NotEmpty
  String address;

  String env;

  public void setName(String name){
    String lan = ServletUtils.getRequestLanguage();
    System.out.println("Language: "+ lan);
    this.name = lan + name;
  }


}
