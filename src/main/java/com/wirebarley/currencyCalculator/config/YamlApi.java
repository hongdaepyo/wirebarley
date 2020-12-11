package com.wirebarley.currencyCalculator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.wirebarley.currencyCalculator.util.YamlPropertySourceFactory;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "api-info")
@PropertySource(value = "classpath:api.yml", factory = YamlPropertySourceFactory.class)
@Data
public class YamlApi {
	private String accesskey;
	private String baseurl;
	private String endpoint;
}
