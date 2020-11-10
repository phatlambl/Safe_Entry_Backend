package com.example.demo.model.device;


import javax.persistence.*;

@Entity
@Table(name = "device")
public class Device {
	
    @Id    
    @Column(name = "id")
    private String id;
    
    @Column(name = "location")
    private String location;  

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

	public Device(String id, String location) {		
		this.id = id;
		this.location = location;
	}

	public Device() {
		
	}
	
	
    
    
}
