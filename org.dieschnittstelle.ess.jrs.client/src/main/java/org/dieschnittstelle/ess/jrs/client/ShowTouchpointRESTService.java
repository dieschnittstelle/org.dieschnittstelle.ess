package org.dieschnittstelle.ess.jrs.client;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.crm.Address;
import org.dieschnittstelle.ess.entities.crm.StationaryTouchpoint;
import org.dieschnittstelle.ess.jrs.ITouchpointCRUDService;
import org.dieschnittstelle.ess.utils.Utils;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import static org.dieschnittstelle.ess.utils.Utils.*;

public class ShowTouchpointRESTService {

	protected static Logger logger = org.apache.logging.log4j.LogManager
			.getLogger(ShowTouchpointRESTService.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// for demo purposes: control whether we are accessing the synchronous or the asynchronous service
		// note that, at the moment (20201211) there is an error related to polymorphic types when accessing
		// the service asynchronously
		boolean async = false;

		/*
		 * create a client for the web service passing the interface
		 * this uses the most recent resteasy client api rather than the deprecated ProxyFactory.create() method
		 */
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/api/" + (async ? "async/" : ""));
		ITouchpointCRUDService serviceProxy = target.proxy(ITouchpointCRUDService.class);

		show("serviceProxy: " + serviceProxy + " of class: " + serviceProxy.getClass());

		// 1) read out all touchpoints
		List<StationaryTouchpoint> touchpoints = (List)serviceProxy.readAllTouchpoints();
		logger.info("read touchpoints: " + touchpoints);

		// 2) delete the touchpoint after next console input
		if (touchpoints != null && touchpoints.size() > 0) {
			Utils.step();

			StationaryTouchpoint tp = touchpoints.get(0);
			serviceProxy.deleteTouchpoint(tp.getId());
			logger.info("deleted touchpoint: " + tp);
		}
		else {
			logger.warn("no touchpoints available for deletion...");
		}

		// 3) wait for input and create a new touchpoint
		Utils.step();

		Address addr = new Address("Luxemburger Strasse", "10", "13353",
				"Berlin");
		StationaryTouchpoint tp = new StationaryTouchpoint(-1,
				"BHT Verkaufsstand", addr);

		tp = (StationaryTouchpoint) serviceProxy.createTouchpoint(tp);
		logger.info("created touchpoint: " + tp);

		/*
		 * 4) wait for input and...
		 */
		Utils.step();
		// change the name
		tp.setName("BHT Mensa");

		/*
		 * UE JRS1: add a call to the update method, passing tp
		 */
		logger.info("renamed touchpoint with id " + tp.getId() + " to " + tp.getName());

		show("TestTouchpointRESTService: done.\n");

	}

}
