package havis.net.rest.io.async;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.MethodCallback;

import havis.device.io.Direction;
import havis.device.io.IOConfiguration;
import havis.device.io.KeepAliveConfiguration;
import havis.device.io.State;
import havis.net.rest.shared.data.ServiceAsync;

@Path("../rest/io")
public interface IODeviceServiceAsync extends ServiceAsync {
	
	@OPTIONS
	@Path("configuration")
	void optionsConfiguration(MethodCallback<Void> callback);
	
	@GET
	@Path("configuration")
	void getConfiguration(MethodCallback<List<String>> callback);
	
	@DELETE
	@Path("configuration")
	void deleteConfiguration(MethodCallback<Void> callback);
	
	@OPTIONS
	@Path("configuration/keepalive")
	void optionsKeepAliveConfiguration(MethodCallback<Void> callback);

	@GET
	@Path("configuration/keepalive")	
	void getKeepAliveConfiguration(MethodCallback<KeepAliveConfiguration> callback);
	
	@PUT
	@Path("configuration/keepalive")
	void setKeepAliveConfiguration(KeepAliveConfiguration configuration, MethodCallback<Void> callback);
	
	@OPTIONS
	@Path("configuration/io-configuration")
	void optionsIOConfigurations(MethodCallback<Void> callback);
	
	@GET
	@Path("configuration/io-configuration")	
	void getIOConfigurations(MethodCallback<List<IOConfiguration>> callback);

	@OPTIONS
	@Path("configuration/io-configuration/{id}/state")
	void optionsIOState(MethodCallback<Void> callback);

	@GET
	@Path("configuration/io-configuration/{id}/state")		
	void getIOState(@PathParam("id") short id, MethodCallback<State> callback);
	
	@PUT
	@Path("configuration/io-configuration/{id}/state")
	void setIOState(@PathParam("id") short id, State state, MethodCallback<Void> callback);

	@OPTIONS
	@Path("configuration/io-configuration/{id}/initialstate")
	void optionsIOInitialState(MethodCallback<Void> callback);

	@GET
	@Path("configuration/io-configuration/{id}/initialstate")		
	void getIOInitialState(@PathParam("id") short id, MethodCallback<State> callback);
	
	@PUT
	@Path("configuration/io-configuration/{id}/initialstate")
	void setIOInitialState(@PathParam("id") short id, State state, MethodCallback<Void> callback);

	@OPTIONS
	@Path("configuration/io-configuration/{id}/direction")
	void optionsIODirection(MethodCallback<Void> callback);

	@GET
	@Path("configuration/io-configuration/{id}/direction")		
	void getIODirection(@PathParam("id") short id, MethodCallback<Direction> callback);
	
	@PUT
	@Path("configuration/io-configuration/{id}/direction")
	void setIODirection(@PathParam("id") short id, Direction direction, MethodCallback<Void> callback);
}
