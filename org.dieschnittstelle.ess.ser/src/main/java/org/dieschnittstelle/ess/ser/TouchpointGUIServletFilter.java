package org.dieschnittstelle.ess.ser;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.dieschnittstelle.ess.utils.Utils.*;

import org.apache.logging.log4j.Logger;

/**
 * checks whether the gui servlet is accessed by a user agent that accepts html
 * @author kreutel
 *
 */
public class TouchpointGUIServletFilter implements Filter {

	protected static Logger logger = org.apache.logging.log4j.LogManager
			.getLogger(TouchpointGUIServletFilter.class);

	public TouchpointGUIServletFilter() {
		show("TouchpointGUIServletFilter: constructor invoked\n");
	}

	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

//	greift auf request-header zu und prüft ob accept language header da ist
//	ACH wird von browsern gesetzt
//	wenn man einen nicht-broweser-client hat, ist der Header normalerweise nicht da
//	so kann Anwendung filtern ob tatsächlich von GUI-Client zugegriffen wird
//	oder ob API-Client zugreift (das wollen wir nicht)
// = Authentifizierung mit Filter
//	Filter: nur requests durchzulesen die von GUI Clients kommen
//	Aufgabe: Querschnittsfunktionen umzusetzen
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		show("TouchpointGUIServletFilter: doFilter() invoked\n");
		
		// check whether we have a an accept-language header that will be set by the browser but not by the apache http client.
		// otherwise reject the request
		String acceptLanguageHeader = ((HttpServletRequest) request)
				.getHeader("accept-language");
		logger.info("got accept-language header: " + acceptLanguageHeader);

		// we do quite a brute force string match
		if (acceptLanguageHeader != null) {
			chain.doFilter(request, response);
		} else {
			// if we do not find the required header, we block access
			((HttpServletResponse) response)
					.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
