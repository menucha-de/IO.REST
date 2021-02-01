package havis.net.rest.io.osgi;

import havis.device.io.IODevice;

import havis.net.rest.io.RESTApplication;

import java.util.logging.Logger;

import javax.ws.rs.core.Application;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

	Logger log = Logger.getLogger(Activator.class.getName());

	private ServiceRegistration<Application> registration;
	private ServiceTracker<IODevice, IODevice> tracker;

	@Override
	public void start(BundleContext context) throws Exception {

		tracker = new ServiceTracker<IODevice, IODevice>(context,
				IODevice.class, null) {
			@Override
			public IODevice addingService(ServiceReference<IODevice> reference) {
				IODevice service = super.addingService(reference);
				registration = context.registerService(Application.class, new RESTApplication(service), null);
				return service;
			}

			@Override
			public void removedService(ServiceReference<IODevice> reference,
					IODevice service) {
				registration.unregister();
				super.removedService(reference, service);
			}
		};
		tracker.open();

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		tracker.close();
	}
}