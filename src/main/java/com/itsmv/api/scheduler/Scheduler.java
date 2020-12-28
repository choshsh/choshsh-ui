package com.itsmv.api.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

	@Value("${spring.profiles}")
	private String profiles;

	/*
	 * cron 설정: 초(0-59) 분(0-59) 시간(0-23) 일(1-31) 월(1-12) 요일(0-7)
	 */

	/* 월~금 10:30에 실행 */
	@Scheduled(cron = "0 30 10 * * 1-5")
	public void diSchedulerMorning() {
		if (profiles.equalsIgnoreCase("prod")) {
			try {
				System.out.println("Scheduler is working.");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
