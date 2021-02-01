package havis.net.rest.io;

import havis.device.io.Configuration;
import havis.device.io.Direction;
import havis.device.io.IOConfiguration;
import havis.device.io.IODevice;
import havis.device.io.KeepAliveConfiguration;
import havis.device.io.State;
import havis.device.io.Type;
import havis.device.io.exception.ConnectionException;
import havis.device.io.exception.IOException;
import havis.device.io.exception.ImplementationException;
import havis.device.io.exception.ParameterException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

import org.junit.Assert;
import org.junit.Test;

public class IODeviceServiceTest {

	@Mocked private IOConsumerREST consumer;
	@Mocked private IODevice iod;
	
	private IODeviceService service = new IODeviceService(iod);
	
	private void controllerIsValid() throws ConnectionException, ImplementationException {
		new Expectations() {{ consumer.getOpenController(); result = iod; }};
	}
	
	@SuppressWarnings("serial")
	private void controllerIsInvalid() throws ConnectionException, ImplementationException {
		new Expectations() {{ consumer.getOpenController(); result = new IOException() {}; }};
	}

	@Test
	public void getConfiguration() {
		List<String> expected = Arrays.asList("ALL", "IO", "KEEP_ALIVE");
		List<String> actual = service.getConfiguration();
		
		Assert.assertEquals(expected, actual);
	}
	
	@SuppressWarnings("serial")
	@Test
	public void deleteConfiguration() throws ConnectionException, ImplementationException {
		
		// Valid controller: Execute DELETE.
		controllerIsValid();
		service.deleteConfiguration();
		new Verifications() {{
			iod.resetConfiguration(); times = 1;
		}};

		// Execute DELETE with error
		new Expectations() {{
			iod.resetConfiguration(); result = new IOException() {};
		}};
		
		try {
			service.deleteConfiguration();
			Assert.fail();
		} catch (IOException e) {
			
		}

		// Invalid controller: Exception
		controllerIsInvalid();
		try {
			service.deleteConfiguration();
			Assert.fail();
		} catch (IOException e) {
			
		}
	}
	
	@SuppressWarnings("serial")
	@Test
	public void getKeepAliveConfiguration() throws ConnectionException, ParameterException, ImplementationException {

		controllerIsValid();
		final KeepAliveConfiguration expected = new KeepAliveConfiguration();
		new Expectations() {{
			iod.getConfiguration(Type.KEEP_ALIVE, (short) 0);
			result = Arrays.asList(expected);
		}};
		KeepAliveConfiguration actual = service.getKeepAliveConfiguration();
		Assert.assertEquals(expected, actual);
		new Verifications() {{
			iod.getConfiguration(Type.KEEP_ALIVE, (short) 0);
			times = 1;
		}};

		new Expectations() {{
			iod.getConfiguration(Type.KEEP_ALIVE, (short) 0);
			result = new IOException() {};
		}};
		try {
			actual = service.getKeepAliveConfiguration();
			Assert.fail();
		} catch (IOException e) {

		}

		controllerIsInvalid();
		try {
			actual = service.getKeepAliveConfiguration();
			Assert.fail();
		} catch (IOException e) {

		}
	}
	
	@Test
	public void setKeepAliveConfiguration(@Mocked final KeepAliveConfiguration keepAlive) throws ConnectionException, ImplementationException, ParameterException {
		controllerIsValid();

		service.setKeepAliveConfiguration(keepAlive);

		new Verifications() {{
			List<Configuration> keepAliveList = new ArrayList<Configuration>();
			keepAliveList.add(keepAlive);
			iod.setConfiguration(keepAliveList);
			times = 1;
		}};
		
	}
	
	@Test
	public void getIOConfigurations() throws ConnectionException, ParameterException, ImplementationException {

		final IOConfiguration ioConf = new IOConfiguration();
		controllerIsValid();
		
		List<IOConfiguration> expected = new ArrayList<IOConfiguration>();
		expected.add(ioConf);
		
		new Expectations() {{
			iod.getConfiguration(Type.IO, (short) 0);
			result = Arrays.asList(ioConf);
		}};
		
		service.getIOConfigurations();
		
		new Verifications() {{
			iod.getConfiguration(Type.IO, (short) 0);
			times = 1;
		}};
		
		List<IOConfiguration> actual = service.getIOConfigurations();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void getIOState() throws ConnectionException, ImplementationException, ParameterException {
		
		controllerIsValid();
		State expected = State.HIGH;
		new Expectations() {{
			iod.getConfiguration(Type.IO, anyShort);
			result = Arrays.asList(new IOConfiguration((short) 1, Direction.INPUT, State.HIGH, true));
		}};
		State actual = service.getIOState((short) 1);
		Assert.assertEquals(expected, actual);
		
		new Expectations() {{
			iod.getConfiguration(Type.IO, anyShort);
			result = new ArrayList<IOConfiguration>();
		}};
		
		try {
			actual = service.getIOState((short) 1);
			Assert.fail();
		} catch (ImplementationException e) {
			
		}
		
		controllerIsInvalid();
		try {
			actual = service.getIOState((short) 1);
			Assert.fail();
		} catch (IOException e) {
			
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void setIOState() throws ConnectionException, ImplementationException, ParameterException {
		controllerIsValid();
		State state = State.LOW;
		new Expectations() {{
			iod.getConfiguration(Type.IO, anyShort);
			result = Arrays.asList(new IOConfiguration((short) 1, Direction.INPUT, State.HIGH, true));
		}};
		
		service.setIOState((short) 1, state);
		
		new Verifications() {{
			iod.setConfiguration((List<Configuration>) any);
			times = 1;
		}};
	}
	
	@Test
	public void getIODirection() throws ConnectionException, ParameterException, ImplementationException {
		controllerIsValid();
		Direction expected = Direction.INPUT;
		new Expectations() {{
			iod.getConfiguration(Type.IO, anyShort);
			result = Arrays.asList(new IOConfiguration((short) 1, Direction.INPUT, State.HIGH, true));
		}};
		Direction actual = service.getIODirection((short) 1);
		Assert.assertEquals(expected, actual);
		
		new Expectations() {{
			iod.getConfiguration(Type.IO, anyShort);
			result = new ArrayList<IOConfiguration>();
		}};
		
		try {
			actual = service.getIODirection((short) 1);
			Assert.fail();
		} catch (ImplementationException e) {
			
		}
		
		controllerIsInvalid();
		try {
			actual = service.getIODirection((short) 1);
			Assert.fail();
		} catch (IOException e) {
			
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void setIODirection() throws ConnectionException, ParameterException, ImplementationException {
		controllerIsValid();
		Direction direction = Direction.OUTPUT;
		new Expectations() {{
			iod.getConfiguration(Type.IO, anyShort);
			result = Arrays.asList(new IOConfiguration((short) 1, Direction.INPUT, State.HIGH, true));
		}};
		
		service.setIODirection((short) 1, direction);
		
		new Verifications() {{
			iod.setConfiguration((List<Configuration>) any);
			times = 1;
		}};
	}
}
