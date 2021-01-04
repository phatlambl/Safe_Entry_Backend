package com.example.demo.dto.temperature;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TemperatureConfigDto {	
	
	@NotNull	
    private float temperature;
    
	@NotNull
	@NotEmpty
    private Email[] emails;

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

	public Email[] getEmails() {
		return emails;
	}

	public void setEmails(Email[] emails) {
		this.emails = emails;
	}

}
