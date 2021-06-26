package org.dieschnittstelle.ess.jrs;

import org.dieschnittstelle.ess.entities.crm.AbstractTouchpoint;
import org.dieschnittstelle.ess.entities.crm.StationaryTouchpoint;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/touchpoints")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface ITouchpointCRUDService {

	// Get / touchpoints
	@GET
	List<AbstractTouchpoint> readAllTouchpoints();

	// GET / touchpoints /:id
	@GET
	@Path("/{touchpointId}")
	AbstractTouchpoint readTouchpoint(@PathParam("touchpointId") long id);

	// POST / touchpoints
	@POST
	AbstractTouchpoint createTouchpoint(AbstractTouchpoint touchpoint);

	// DELETE/ touchpoints / :id
	@DELETE
	@Path("/{touchpointId}")
	boolean deleteTouchpoint(@PathParam("touchpointId") long id);


//	@PUT
//	@Path("/{id}")
//	AbstractTouchpoint updateTouchpoint(@PathParam("id") long id ,AbstractTouchpoint tp);

	/*
	 * TODO JRS1: add a new annotated method for using the updateTouchpoint functionality of TouchpointCRUDExecutor and implement it
	 */

}
