package com.ssafy.apm.chat.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
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

  public InfluxDBClient getConnectionInfluxDBClient(){
    return InfluxDBClientFactory.create(url, token, org, bucket);
  }
}
