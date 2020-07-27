package com.jiuwei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.jiuwei.dao"})
public class CarCheckinApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarCheckinApplication.class, args);
	}

}
