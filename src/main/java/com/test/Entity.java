package com.test;

import javax.persistence.Id;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Unindexed;

public class Entity {
	
	static {
		ObjectifyService.register(Entity.class);
	}
	
	private @Id Long id;
	private @Unindexed String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
