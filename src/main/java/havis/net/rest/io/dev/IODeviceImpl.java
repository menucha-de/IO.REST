package havis.net.rest.io.dev;

import java.util.ArrayList;
import java.util.List;

import havis.device.io.Configuration;
import havis.device.io.Direction;
import havis.device.io.IOConsumer;
import havis.device.io.IODevice;
import havis.device.io.KeepAliveConfiguration;
import havis.device.io.IOConfiguration;
import havis.device.io.State;
import havis.device.io.Type;
import havis.device.io.exception.ConnectionException;
import havis.device.io.exception.ImplementationException;
import havis.device.io.exception.ParameterException;

public class IODeviceImpl implements IODevice {
	
	private boolean connectionOpened = false;
	
	private KeepAliveConfiguration keepAliveConfiguration;
	private List<IOConfiguration> ioConfigurations;

	public IODeviceImpl() {
		keepAliveConfiguration = new KeepAliveConfiguration();
		keepAliveConfiguration.setEnable(false);
		keepAliveConfiguration.setInterval(30000);
		ioConfigurations = new ArrayList<IOConfiguration>();
		for (short i = 1; i <=10; ++i) {
			ioConfigurations.add(new IOConfiguration(i, Direction.INPUT, State.HIGH, true));
		}
	}
	
	@Override
	public void openConnection(IOConsumer consumer, int timeout)
			throws ConnectionException, ImplementationException {
		connectionOpened = true;
	}

	@Override
	public void closeConnection() throws ConnectionException,
			ImplementationException {
		connectionOpened = false;
	}

	@Override
	public List<Configuration> getConfiguration(Type type, short pin)
			throws ConnectionException, ParameterException,
			ImplementationException {
		if (connectionOpened) {
			ArrayList<Configuration> confs = new ArrayList<Configuration>();
			switch (type) {
			case ALL:
				confs.add(keepAliveConfiguration);
				confs.addAll(ioConfigurations);
				break;
			case IO:
				if (pin > 0) {
					confs.add(ioConfigurations.get(pin - 1));
				} else {
					confs.addAll(ioConfigurations);
				}
				break;
			case KEEP_ALIVE:
				confs.add(keepAliveConfiguration);
				break;
			default:
				break;
			}
			return confs;
		} else {
			throw new ConnectionException("Connection not opened.");
		}
	}

	@Override
	public void setConfiguration(List<Configuration> configuration)
			throws ConnectionException, ParameterException,
			ImplementationException {
		if (connectionOpened) {
		} else {
			throw new ConnectionException("Connection not opened.");
		}
	}

	@Override
	public void resetConfiguration() throws ImplementationException,
			ConnectionException, ImplementationException {
		if (connectionOpened) {
		} else {
			throw new ConnectionException("Connection not opened.");
		}
	}

}
