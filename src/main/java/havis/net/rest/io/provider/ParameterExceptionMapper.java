package havis.net.rest.io.provider;

import havis.device.io.exception.ParameterException;
import havis.net.rest.shared.data.SerializableValue;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ParameterExceptionMapper implements
		ExceptionMapper<ParameterException> {

	@Override
	public Response toResponse(ParameterException ex) {
		return Response.status(Response.Status.BAD_REQUEST)
				.entity(new SerializableValue<String>(ex.getMessage()))
				.type(MediaType.APPLICATION_JSON).build();
	}

}
