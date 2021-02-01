package havis.net.rest.io;

import org.junit.Assert;
import org.junit.Test;

import havis.device.io.IODevice;
import havis.device.io.StateEvent;
import havis.device.io.exception.ConnectionException;
import havis.device.io.exception.ImplementationException;
import mockit.Mocked;
import mockit.Verifications;

public class IOConsumerRESTTest {

	//private IOConsumerREST ioc = new IOConsumerREST();
	
//	@SuppressWarnings("serial")
//	@Test
//	public void connectionAttempted(@Mocked final IODevice iod) throws ConnectionException, ImplementationException {
//		final IOConsumerREST ioc = new IOConsumerREST();
//		ioc.setIODevice(iod);
//		
//		ioc.connectionAttempted();
//		
//		new Verifications() {{
//			iod.closeConnection();
//			times = 0;
//		}};
//		
//		ioc.getOpenController();
//		
//		new Expectations() {{
//			iod.closeConnection();
//			result = new ImplementationException() {};
//		}};
//		
//		ioc.connectionAttempted();
//
//		new Expectations() {{
//			iod.closeConnection();
//			result = new ConnectionException() {};
//		}};
//
//		ioc.connectionAttempted();
//		
//		new Verifications() {{
//			iod.closeConnection();
//			times = 1;
//		}};
//		
//		
//	}

	@Test
	public void getOpenController(@Mocked final IODevice iod) throws ImplementationException, ConnectionException {
		final IOConsumerREST ioc = new IOConsumerREST(iod);
		
		// Try to get opened controller without IODevice set.
		// Must fail.
		try {
			ioc.getOpenController();
			Assert.fail();
		} catch (ConnectionException e) {
			
		}
		
		// Get opened controller after setting IODevice.
		// Must open connection.
		ioc.getOpenController();

		new Verifications() {{
			iod.openConnection(ioc, anyInt);
			times = 1;
		}};
		
		
	}

	@Test
	public void keepAlive(@Mocked final IODevice iod) {
		IOConsumerREST ioc = new IOConsumerREST(iod);
		ioc.keepAlive();
	}

//	@Test
//	public void setIODevice(@Mocked final IODevice iod1, @Mocked final IODevice iod2) throws ConnectionException, ImplementationException {
//		final IOConsumerREST ioc = new IOConsumerREST();
//		
//		ioc.setIODevice(iod1);
//		
//		IODevice actual = ioc.getOpenController();
//		
//		new Verifications() {{
//			iod1.openConnection(ioc, anyInt);
//			times = 1;
//		}};
//		
//		Assert.assertEquals(iod1, actual);
//		
//		ioc.setIODevice(iod1);
//		
////		new Expectations() {{
////			iod1.closeConnection();
////			result = new ConnectionException() {};
////		}};
//
//		ioc.setIODevice(iod2);
//		
//	}

	@Test
	public void stateChanged(@Mocked final IODevice iod, final @Mocked StateEvent e) {
		IOConsumerREST ioc = new IOConsumerREST(iod);
		ioc.stateChanged(e);
		
		new Verifications() {{
			e.getState();
			times = 1;
		}};
	}
}
