package havis.net.rest.io;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import havis.device.io.Configuration;
import havis.device.io.Direction;
import havis.device.io.IOConfiguration;
import havis.device.io.IODevice;
import havis.device.io.KeepAliveConfiguration;
import havis.device.io.State;
import havis.device.io.Type;
import havis.device.io.exception.ConnectionException;
import havis.device.io.exception.ImplementationException;
import havis.device.io.exception.ParameterException;
import havis.net.rest.shared.Resource;

@Path("io")
public class IODeviceService extends Resource {

	private IOConsumerREST io;

	public IODeviceService(IODevice device) {
		this.io = new IOConsumerREST(device);
	}

	@PermitAll
	@GET
	@Path("configuration")
	public List<String> getConfiguration() {
		List<String> result = new ArrayList<String>();
		for (havis.device.io.Type type : havis.device.io.Type.values()) {
			result.add(type.toString());
		}
		return result;
	}

	@RolesAllowed("admin")
	@DELETE
	@Path("configuration")
	public void deleteConfiguration() throws ImplementationException,
			ConnectionException {
		io.getOpenController().resetConfiguration();
	}

	@PermitAll
	@GET
	@Path("configuration/keepalive")
	public KeepAliveConfiguration getKeepAliveConfiguration()
			throws ConnectionException, ParameterException,
			ImplementationException {
		return (KeepAliveConfiguration) io.getOpenController()
				.getConfiguration(Type.KEEP_ALIVE, (short) 0).get(0);
	}

	@RolesAllowed("admin")
	@PUT
	@Path("configuration/keepalive")
	public void setKeepAliveConfiguration(KeepAliveConfiguration configuration)
			throws ConnectionException, ParameterException,
			ImplementationException {
		List<Configuration> value = new ArrayList<Configuration>();
		value.add(configuration);
		io.getOpenController().setConfiguration(value);
	}

	@PermitAll
	@GET
	@Path("configuration/io-configuration")
	public List<IOConfiguration> getIOConfigurations()
			throws ConnectionException, ParameterException,
			ImplementationException {
		List<IOConfiguration> result = new ArrayList<IOConfiguration>();
		for (Configuration configuration : io.getOpenController()
				.getConfiguration(Type.IO, (short) 0)) {
			IOConfiguration ioConf = (IOConfiguration) configuration;
			if (ioConf.getState() == null) {
				ioConf.setState(State.LOW);
			}
			result.add((IOConfiguration) configuration);
		}
		return result;
	}

	private IOConfiguration getIOConfiguration(short id)
			throws ConnectionException, ParameterException,
			ImplementationException {
		List<Configuration> result = io.getOpenController().getConfiguration(
				Type.IO, id);
		if (!result.isEmpty()) {
			return ((IOConfiguration) result.get(0));
		}
		throw new ImplementationException("Empty result!");
	}

	@PermitAll
	@GET
	@Path("configuration/io-configuration/{id}/initialstate")
	public State getIOInitialState(@PathParam("id") short id)
			throws ConnectionException, ParameterException,
			ImplementationException {
		return getIOConfiguration(id).getInitialState();
	}

	@RolesAllowed("admin")
	@PUT
	@Path("configuration/io-configuration/{id}/initialstate")
	public void setIOInitialState(@PathParam("id") short id, State state)
			throws ConnectionException, ParameterException,
			ImplementationException {
		IOConfiguration conf = getIOConfiguration(id);
		conf.setInitialState(state);
		List<Configuration> confList = new ArrayList<Configuration>();
		confList.add(conf);
		io.getOpenController().setConfiguration(confList);
	}

	@PermitAll
	@GET
	@Path("configuration/io-configuration/{id}/state")
	public State getIOState(@PathParam("id") short id)
			throws ConnectionException, ParameterException,
			ImplementationException {
		return getIOConfiguration(id).getState();
	}

	@PermitAll
	@PUT
	@Path("configuration/io-configuration/{id}/state")
	public void setIOState(@PathParam("id") short id, State state)
			throws ConnectionException, ParameterException,
			ImplementationException {
		IOConfiguration conf = getIOConfiguration(id);
		conf.setState(state);
		List<Configuration> confList = new ArrayList<Configuration>();
		confList.add(conf);
		io.getOpenController().setConfiguration(confList);
	}

	@PermitAll
	@GET
	@Path("configuration/io-configuration/{id}/direction")
	public Direction getIODirection(@PathParam("id") short id)
			throws ConnectionException, ParameterException,
			ImplementationException {
		return getIOConfiguration(id).getDirection();
	}

	@RolesAllowed("admin")
	@PUT
	@Path("configuration/io-configuration/{id}/direction")
	public void setIODirection(@PathParam("id") short id, Direction direction)
			throws ConnectionException, ParameterException,
			ImplementationException {
		IOConfiguration conf = getIOConfiguration(id);
		conf.setDirection(direction);

		if (direction == Direction.INPUT) {
			conf.setEnable(true);
			conf.setState(State.UNKNOWN);
		} else {
			conf.setEnable(false);
			conf.setState(State.LOW);
		}

		List<Configuration> confList = new ArrayList<Configuration>();
		confList.add(conf);
		io.getOpenController().setConfiguration(confList);
	}
}
