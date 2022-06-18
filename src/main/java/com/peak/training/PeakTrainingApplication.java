package com.peak.training;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
@EnableEncryptableProperties
@EnableCaching

@EnableFeignClients(basePackages = "com.peak.training.common.annotation.log")
public class PeakTrainingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeakTrainingApplication.class, args);
	}

}
