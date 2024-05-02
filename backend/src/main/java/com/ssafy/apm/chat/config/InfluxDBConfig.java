package com.ssafy.apm.chat.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConfig {
  @Value("${spring.influx.url}")
  private String url;
  @Value("${spring.influx.token}")
  private char[] token;
  @Value("${spring.influx.org}")
  private String org;
  @Value("${spring.influx.bucket}")
  private String bucket;
  @Value("${spring.influx.user}")
  private String user;
  @Value("${spring.influx.password}")
  private String password;


  @Bean
  public InfluxDBClient influxDBClient(){
    return InfluxDBClientFactory.create(url, token, org, bucket);
  }

  @Bean
  public InfluxDB influxDB(){
    return InfluxDBFactory.connect(url, user, password);
  }
}
