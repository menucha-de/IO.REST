package havis.net.rest.io.provider;

import havis.device.io.exception.ConnectionException;
import havis.net.rest.shared.data.SerializableValue;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConnectionExceptionMapper implements
		ExceptionMapper<ConnectionException> {

	@Override
	public Response toResponse(ConnectionException ex) {
		return Response.status(Response.Status.SERVICE_UNAVAILABLE)
				.entity(new SerializableValue<String>(ex.getMessage()))
				.type(MediaType.APPLICATION_JSON).build();
	}
}
