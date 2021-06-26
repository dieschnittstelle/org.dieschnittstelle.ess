package org.dieschnittstelle.ess.jrs;

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.GenericCRUDExecutor;
import org.dieschnittstelle.ess.entities.crm.AbstractTouchpoint;
import org.dieschnittstelle.ess.entities.crm.StationaryTouchpoint;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/*
 * this class is a wrapper around the synchronous implementation that demonstrates an async service implementation
 */
@Path("async/touchpoints")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class TouchpointCRUDServiceImplAsync {

	protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(TouchpointCRUDServiceImplAsync.class);

	private ITouchpointCRUDService service;

	// default constructor to satisfy IDEA...
	public TouchpointCRUDServiceImplAsync() {

	}

	/**
	 * here we will be passed the context parameters by the resteasy framework
	 */
	public TouchpointCRUDServiceImplAsync(@Context ServletContext servletContext, @Context HttpServletRequest request) {
//		this.service = new TouchpointCRUDServiceImpl(servletContext,request);
	}

	@GET
	public void readAllTouchpoints(@Suspended AsyncResponse response) {
		logger.info("readAllTouchpoints()");
		new Thread(()-> {
			sleep(1000);
			response.resume(service.readAllTouchpoints());
		}).start();
	}

	@POST
	public void createTouchpoint(StationaryTouchpoint touchpoint,@Suspended AsyncResponse response) {
		logger.info("createTouchpoint()");
		new Thread(()->{
			sleep(1000);
			response.resume(service.createTouchpoint(touchpoint));
		}).start();
	}

	@DELETE
	@Path("/{id}")
	public void deleteTouchpoint(@PathParam("id") long id,@Suspended AsyncResponse response) {
		logger.info("deleteTouchpoint()");
		new Thread(()->{
			sleep(1000);
			response.resume(service.deleteTouchpoint(id));
		}).start();
	}

	@GET
	@Path("/{id}")
	public void readTouchpoint(@PathParam("id") long id,@Suspended AsyncResponse response) {
		logger.info("readTouchpoint()");
		new Thread(()->{
			sleep(1000);
			response.resume(service.readTouchpoint(id));
		}).start();
	}

	private void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
