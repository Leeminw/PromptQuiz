package com.ssafy.apm.chat.config;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConfig {
  @Value("${spring.influx.org}")
  private String org;
  @Value("${spring.influx.bucket}")
  private String bucket;
  @Value("${spring.influx.url}")
  private String url;
  @Value("${spring.influx.token}")
  private char[] token;

  @Bean
  public InfluxDBClient influxDBClient(){
    return InfluxDBClientFactory.create(url, token, org, bucket);
  }
}
