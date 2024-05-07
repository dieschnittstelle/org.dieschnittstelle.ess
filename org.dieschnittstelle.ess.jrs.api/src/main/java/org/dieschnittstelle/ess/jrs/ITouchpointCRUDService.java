package org.dieschnittstelle.ess.jrs;

import org.dieschnittstelle.ess.entities.crm.StationaryTouchpoint;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;

import java.util.List;

@Path("/touchpoints")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface ITouchpointCRUDService {

	@GET
	List<StationaryTouchpoint> readAllTouchpoints();

	@GET
	@Path("/{touchpointId}")
	StationaryTouchpoint readTouchpoint(@PathParam("touchpointId") long id);

	@POST
	StationaryTouchpoint createTouchpoint(StationaryTouchpoint touchpoint);

	@DELETE
	@Path("/{touchpointId}")
	boolean deleteTouchpoint(@PathParam("touchpointId") long id);

	// JRS1: PUT/touchpoints/<id>
	@PUT
	@Path("/{touchpointId}")
	StationaryTouchpoint updateTouchpoint(@PathParam("touchpointId") long id,
											  StationaryTouchpoint touchpoint);

}
