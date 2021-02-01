package havis.net.rest.io.provider;

import havis.device.io.exception.ImplementationException;
import havis.net.rest.shared.data.SerializableValue;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ImplementationExceptionMapper implements
		ExceptionMapper<ImplementationException> {

	@Override
	public Response toResponse(ImplementationException ex) {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(new SerializableValue<String>(ex.getMessage()))
				.type(MediaType.APPLICATION_JSON).build();
	}
}
