package com.paul.demo.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.paul.demo.server.ServerEntity;


@XmlRootElement
public class ServerList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<ServerEntity> items;

	public ServerList() {
		this.items = new ArrayList<>();
	}

	public ServerList(List<ServerEntity> items) {
		this.items = items;
	}

	public List<ServerEntity> getItems() {
		return items;
	}

	public void setItems(List<ServerEntity> items) {
		this.items = items;
	}
}
