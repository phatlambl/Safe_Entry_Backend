package com.example.demo.model.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.example.demo.model.device.DeviceLog;


@Entity
@Table(name = "user")
public class User {	
	
    @Id   
    @Column(name = "id")
    private String id;
    
    @Column(name = "username")   
    private String username;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "name")
    private String name;   
    
    @Column(name ="face_id")
    private String faceId;
    
    @Transient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade={CascadeType.REFRESH, CascadeType.MERGE})
	private transient List<DeviceLog> listDeviceLogs;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public List<DeviceLog> getListDeviceLogs() {
		return listDeviceLogs;
	}

	public void setListDeviceLogs(List<DeviceLog> listDeviceLogs) {
		this.listDeviceLogs = listDeviceLogs;
	}	
	
	public String getFaceId() {
		return faceId;
	}

	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}

	public User(String id, String username, String password, String email, String name) {		
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
	}
	
	public User(String id, String name) {		
		this.id = id;
		this.name = name;
	}
	
	
	public User(String id, String name, String email) {		
		this.id = id;
		this.email = email;
		this.name = name;
	}

	
	public User(String id,  String name, String email, String faceId) {		
		this.id = id;
		this.email = email;
		this.name = name;
		this.faceId = faceId;
	}

	public User() {		
	}
	

    
    
}
