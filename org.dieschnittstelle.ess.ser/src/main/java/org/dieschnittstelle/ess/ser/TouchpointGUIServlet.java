package org.dieschnittstelle.ess.ser;

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.crm.Address;
import org.dieschnittstelle.ess.entities.crm.StationaryTouchpoint;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.dieschnittstelle.ess.utils.Utils.show;

// servlet wird ausgeführt bei hhtp-get-request
// müssen für jeden request, den sie bearbeiten sollen, eine Bearbeitungsmethode bereitstellen
public class TouchpointGUIServlet extends HttpServlet {

	protected static Logger logger = org.apache.logging.log4j.LogManager
			.getLogger(TouchpointGUIServlet.class);

	private boolean demoPostNonIdempotence = false;
	private boolean demoGetIdempotence = false;

	public TouchpointGUIServlet() {
		show("TouchpointGUIServlet: constructor invoked\n");
	}

//	ruft displayView-Methode auf
//	displayView generiert Ansicht die bereit gestellt weden soll
//	hat dafür den hhtp request
//	zunäacht brauch ich die Daten die angezeigt werden sollen (touchpoint), dann Ansicht generieren
//	zunäachst auf Objekt zugreifen was die Daten enthält (Touchpoint.CRUDEXECUTER) -> wird aus ServletContext ausgelesen
	


	@Override
	protected void doGet(HttpServletRequest request,
						 HttpServletResponse response) {

		show("TouchpointGUIServlet: doGet() invoked\n");

		// this can be used to demonstrate browser behaviour
		// for refreshes of POST vs. GET requests for the create action.
		// change method attribute in jsp for trying it out.
		if (request.getPathInfo().startsWith("/create"))  {
			doPost(request,response);
		}
		else {
			if (demoGetIdempotence) {
				request.setAttribute("demoGetIdempotence",true);
			}
			displayView(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response) {

		show("TouchpointGUIServlet: doPost() invoked\n");

		// obtain the executor
		TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext()
				.getAttribute("touchpointCRUD");

		// obtain the pathInfo value
		String pathInfo = request.getPathInfo();

		// check whether we need to create or delete a touchpoint
		if (pathInfo.startsWith("/delete")) {
			// if we have a delete request, the id to be deleted will be the
			// last segment of the path
			int id = Integer.parseInt(pathInfo.substring(pathInfo
					.lastIndexOf("/") + 1));
			exec.deleteTouchpoint(id);
		} else if (request.getPathInfo().startsWith("/create")) {
			String name = request.getParameter("name");

			// if we have a create request, we read out the parameters
			String street = request.getParameter("street");
			String houseNr = request.getParameter("houseNr");
			String city = request.getParameter("city");
			String zipCode = request.getParameter("zipCode");

			// create a new address
			Address addr = new Address(street, houseNr, zipCode, city);
			StationaryTouchpoint tp = new StationaryTouchpoint(-1, name, addr);
			exec.createTouchpoint(tp);
		}

		if (!demoPostNonIdempotence && !demoGetIdempotence) {
			request.setAttribute("redirectToRoot",true);
		}
		else if (demoGetIdempotence) {
			request.setAttribute("demoGetIdempotence",true);
		}
		displayView(request, response);
	}

	/**
	 * display the view (we always use the same jsp for all requests)
	 */
	/*
	 * now pass the request to the jsp for display
	 */
//	 touchpointCRUD Name was beim Filter hinterlegt ist
//	interne Weiterleitung durch dispatcher
//	request Dispatcher kriegt Pfad + reuqest
//	auf	request wird zuvor die Liste der touchspoints geschrieben
	private void displayView(HttpServletRequest request,
							 HttpServletResponse response) {
		/*
		 * we read out the touchpoint data and add it as an attribute to the
		 * request such that the jsp can be agnostic with regard to how the data
		 * to be display has been obtained
		 */
		// obtain the executor
		TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext()
				.getAttribute("touchpointCRUD");
		// read out the touchpoints and them to the request
		request.setAttribute("touchpoints", exec.readAllTouchpoints());

		// obtain a dispatcher for the jsp (which will always be the same view)
		RequestDispatcher dispatcher = ((HttpServletRequest) request)
				.getServletContext().getRequestDispatcher("/gui.jsp");
		// and forward the request to the jsp
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			String err = "got exception: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}
	}

}
