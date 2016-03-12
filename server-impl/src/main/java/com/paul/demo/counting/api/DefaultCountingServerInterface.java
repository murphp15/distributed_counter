package com.paul.demo.counting.api;

import static com.paul.rest.utilities.ServerUtilities.createLocaUrl;

import javax.servlet.ServletContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mkyong.rest.GossipSpreaderImpl;
import com.paul.demo.common.CurrentCountForServer;
import com.paul.demo.common.SharedLabels;
import com.paul.demo.server.ServerEntity;
import com.paul.rest.utilities.ServerUtilities;

@Component
public class DefaultCountingServerInterface implements CountingServerInterface {

	private static final Log LOG = LogFactory.getLog(GossipSpreaderImpl.class);
	@Autowired private  ServletContext servletContext;

	
	private WebTarget getWebTarget(ServerEntity entity) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		return client.target(entity.getServerUrl() +  SharedLabels.DISTRIBUTED_COUNTER);
	}

	@Override
	public String getLocalUrl() {
		return ServerUtilities.createLocaUrl(servletContext);
	}

	@Override
	public int getYourCountFromOtherServer(ServerEntity serverEntity) {
		String countAsString = getWebTarget(serverEntity).request().accept(MediaType.TEXT_PLAIN).get(String.class);
		return Integer.parseInt(countAsString);
	}

	@Override
	public void publishUpdatesToOtherServer(ServerEntity entity, int currentCount) {
		LOG.info(createLocaUrl(servletContext)
				+" is updating the value on " +  entity.getServerUrl() 
				+ "to " + currentCount );
		postUpdatedValue(getWebTarget(entity), new CurrentCountForServer(
				new ServerEntity(createLocaUrl(servletContext)),currentCount));
	}

	private static void postUpdatedValue(WebTarget target,CurrentCountForServer currentCountForServer) {
		Entity<?> entity = Entity.entity(currentCountForServer, MediaType.APPLICATION_JSON);
		Response postResponse = 
				target.request().post(entity);
		if(postResponse.getStatus()!=200 && postResponse.getStatus()!=204){
			throw new RuntimeException("erro");
		}
	}

}