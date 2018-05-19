package com.hunqian.model;


import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class TestJsonModel {

	@JSONField(serialize=false)
	private int id;
	private String name;
	@JSONField(format="yyyy-mm-dd hh:mm:ss")
	private Date time;

	public TestJsonModel() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
