	package com.example.demo.model.conf;

import javax.persistence.*;

@Entity
@Table(name = "temperature")
public class Temperature {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "temperature")
    private float temperature;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

	public Temperature(int id, float temperature) {		
		this.id = id;
		this.temperature = temperature;
	}

	public Temperature() {
		
	}
}
