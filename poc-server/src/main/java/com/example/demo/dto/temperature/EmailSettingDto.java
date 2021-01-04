package com.example.demo.dto.temperature;

public class EmailSettingDto {
	
	private int id;
	private String email;
	private boolean alert;
	private boolean report;
	private Long timeReport;
	private String timezone;
	private String frequency;
	private String dayOfWeek;
	private String time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isAlert() {
		return alert;
	}
	public void setAlert(boolean alert) {
		this.alert = alert;
	}
	public boolean isReport() {
		return report;
	}
	public void setReport(boolean report) {
		this.report = report;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public Long getTimeReport() {
		return timeReport;
	}
	public void setTimeReport(Long timeReport) {
		this.timeReport = timeReport;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
