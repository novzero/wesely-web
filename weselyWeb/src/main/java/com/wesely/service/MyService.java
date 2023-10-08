package com.wesely.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class MyService {
	
	@Scheduled(fixedRate = 1000)
	public void say() {
		System.out.println("나는 1초마다 실행된다.........");
	}
	@Scheduled(fixedRate = 5000)
	public void say2() {
		System.out.println("나는 5초마다 실행된다-----------------------------------");
	}
}
