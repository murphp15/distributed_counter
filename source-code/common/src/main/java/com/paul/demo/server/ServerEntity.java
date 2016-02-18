package com.paul.demo.server;

import java.io.Serializable;

import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.context.annotation.Configuration;

import com.google.common.base.Preconditions;
import com.paul.demo.common.SharedLabels;

@XmlRootElement
@Configuration
public class ServerEntity implements Serializable {

	private static final long serialVersionUID = 50L;
	@Id
	private String serverUrl;
	private String clientFacingCounterUrl;
	
	public ServerEntity() {

	}
	public ServerEntity(String baseServerUrl) {
		this.serverUrl = Preconditions.checkNotNull(baseServerUrl,"serverUrl");
		this.clientFacingCounterUrl =  this.serverUrl + SharedLabels.CLIENT_FACING_COUNTER;
	}
	
	public String getServerUrl() {
		return serverUrl;
	}

	@Override
	public int hashCode() {
		// performance gain.
		return serverUrl.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServerEntity other = (ServerEntity) obj;
		if (serverUrl == null) {
			if (other.serverUrl != null)
				return false;
		} else if (!serverUrl.equals(other.serverUrl))
			return false;
		return true;
	}
	
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	@Override
	public String toString() {
		return "ServerEntity [serverUrl=" + serverUrl + "]";
	}
	
	public String getClientFacingCounterUrl() {
		return clientFacingCounterUrl;
	}
	
	public void setClientFacingCounterUrl(String clientFacingCounterUrl) {
		this.clientFacingCounterUrl = clientFacingCounterUrl;
	}
	
	
}
