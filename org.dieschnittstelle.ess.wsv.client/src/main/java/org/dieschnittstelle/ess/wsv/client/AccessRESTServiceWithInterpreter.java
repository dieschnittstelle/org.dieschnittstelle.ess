package org.dieschnittstelle.ess.wsv.client;

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.crm.Address;
import org.dieschnittstelle.ess.entities.crm.StationaryTouchpoint;
import org.dieschnittstelle.ess.utils.Utils;
import org.dieschnittstelle.ess.wsv.client.service.ITouchpointCRUDService;
import org.dieschnittstelle.ess.wsv.interpreter.JAXRSClientInterpreter;

import java.util.List;

import static org.dieschnittstelle.ess.utils.Utils.show;

public class AccessRESTServiceWithInterpreter {

    protected static Logger logger = org.apache.logging.log4j.LogManager
            .getLogger(AccessRESTServiceWithInterpreter.class);

    /**
     * @param args
     */
    public static void main(String[] args) {

        /*
         * TODO WSV1 (here and following TODOs): create an instance of the invocation handler passing the service
         * interface and the base url
         */
        JAXRSClientInterpreter invocationHandler = null;

        /*
         * TODO: create a client for the web service using Proxy.newProxyInstance()
         */
        ITouchpointCRUDService serviceProxy = null;

        show("serviceProxy: " + serviceProxy);

        step();

        // 1) read out all touchpoints
        List<StationaryTouchpoint> tps = serviceProxy.readAllTouchpoints();
        show("read all: " + tps);


        // TODO: comment-in the call to delete() once this is handled by the invocation handler
//		// 2) delete the touchpoint if there is one
//		if (tps.size() > 0) {
//          step();
//			show("deleted: "
//					+ serviceProxy.deleteTouchpoint(tps.get(0).getId()));
//		}
//
//		// 3) create a new touchpoint
        step();

        Address addr = new Address("Luxemburger Strasse", "10", "13353",
                "Berlin");
        StationaryTouchpoint tp = new StationaryTouchpoint(-1,
                "BHT Verkaufsstand", addr);
        tp = (StationaryTouchpoint)serviceProxy.createTouchpoint(tp);
        show("created: " + tp);

        // TODO: comment-in the call to read() once this is handled
//		/*
//		 * 4) read out the new touchpoint
//		 */
//		show("read created: " + serviceProxy.readTouchpoint(tp.getId()));
//

        // TODO: comment-in the call to update() once this is handled
//		/*
//		 * 5) update the touchpoint
//		 */
//		// change the name
//		step();
//		tp.setName("BHT Mensa");
//
//
//		tp = serviceProxy.updateTouchpoint(tp.getId(), tp);
//		show("updated: " + tp);

    }

    public static void step() {
        Utils.step();
    }
}
