package com.example.demo.dto.temperature;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TemperatureConfigDto {	
	
	@NotNull	
    private float temperature;
    
	@NotNull
	@NotEmpty
    private String emails;

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }
}
