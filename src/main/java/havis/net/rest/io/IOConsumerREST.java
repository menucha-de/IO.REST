package havis.net.rest.io;

import havis.device.io.IOConsumer;
import havis.device.io.IODevice;
import havis.device.io.StateEvent;
import havis.device.io.exception.ConnectionException;
import havis.device.io.exception.ImplementationException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.naming.NamingException;

public class IOConsumerREST implements IOConsumer {

	private static final int TIME_OUT = 3000;

	private Lock lock = new ReentrantLock();
	private IODevice iod;
	private boolean connected;

	public IOConsumerREST(IODevice iod) {
		try {
			lock.lock();

			if (this.iod == iod)
				return;

			if (connected) {
				try {
					this.iod.closeConnection();
				} catch (ConnectionException | ImplementationException e) {
					e.printStackTrace();
				}
			}
			connected = false;
			this.iod = iod;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void stateChanged(StateEvent e) {
		System.out.println("State changed: " + e.getState());
	}

	@Override
	public void connectionAttempted() {
		try {
			lock.lock();

			if (connected) {
				connected = false;
				iod.closeConnection();
			}
		} catch (ConnectionException | ImplementationException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void keepAlive() {
		// TODO Auto-generated method stub

	}

	/**
	 * Get the IODevice
	 * 
	 * @return IODevice
	 * @throws ImplementationException
	 * @throws ConnectionException
	 * @throws NamingException
	 */
	public IODevice getOpenController() throws ConnectionException,
			ImplementationException {
		try {
			lock.lock();

			if (iod == null) {
				throw new ConnectionException(
						"No IO device instance available.");
			}

			if (!connected) {
				iod.openConnection(this, TIME_OUT);
				connected = true;
			}

			return iod;
		} finally {
			lock.unlock();
		}
	}

}
