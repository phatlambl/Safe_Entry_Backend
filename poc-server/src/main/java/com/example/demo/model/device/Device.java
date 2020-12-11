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

    @Column(name = "floor")
    private String floor;
    
    @Column(name="room")
    private String room;
    
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

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public Device(String id, String location, String floor, String room) {		
		this.id = id;
		this.location = location;
		this.floor = floor;
		this.room = room;
	}

	public Device() {
		
	}    
}
