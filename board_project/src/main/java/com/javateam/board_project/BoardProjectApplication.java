package com.javateam.board_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// 추가
@EnableJpaAuditing
@EntityScan(
        basePackageClasses = {Jsr310JpaConverters.class},  // basePackageClasses에 지정
        basePackages = {"com.javateam.board_project.domain"})
@SpringBootApplication
public class BoardProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardProjectApplication.class, args);
	}

}
