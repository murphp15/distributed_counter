package com.paul.demo.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.paul.demo.server.ServerEntity;
@XmlRootElement
public class CurrentCountForServer implements Serializable {

	private static final long serialVersionUID = 1L;
	private ServerEntity server;
	private int newValue;
	
	public CurrentCountForServer(ServerEntity server,int newValue){
		this.server = server;
		this.newValue = newValue;
	}

	public CurrentCountForServer() {
		super();
	}
	
	public void setNewValue(int newValue) {
		this.newValue = newValue;
	}
	public void setServer(ServerEntity server) {
		this.server = server;
	}
	public int getNewValue() {
		return newValue;
	}
	public ServerEntity getServer() {
		return server;
	}
}
