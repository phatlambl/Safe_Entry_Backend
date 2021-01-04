package com.example.demo.controller.temperature;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {
	
	private final String timeSchedule="4 16 * * * ?";

	@Scheduled( )
	public void sendReport() {
		System.out.println("hello" );
	}
	
}
