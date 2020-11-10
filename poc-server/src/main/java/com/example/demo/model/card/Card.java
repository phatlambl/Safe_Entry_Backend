package com.example.demo.model.card;

import javax.persistence.*;

@Entity
@Table(name = "card")
public class Card {
	
    @Id   
    @Column(name = "id")
    private String id;
    @Column(name = "type")
    private String type;

   
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
