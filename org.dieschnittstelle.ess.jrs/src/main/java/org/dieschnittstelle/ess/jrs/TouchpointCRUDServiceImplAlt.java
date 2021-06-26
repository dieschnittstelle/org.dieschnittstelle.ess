//package org.dieschnittstelle.ess.jrs;
//
//import org.apache.logging.log4j.Logger;
//import org.dieschnittstelle.ess.entities.GenericCRUDExecutor;
//import org.dieschnittstelle.ess.entities.crm.AbstractTouchpoint;
//import org.dieschnittstelle.ess.entities.crm.StationaryTouchpoint;
//import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
//
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.NotFoundException;
//import javax.ws.rs.core.Context;
//import java.util.List;
//
//public class TouchpointCRUDServiceImplAlt implements ITouchpointCRUDService {
//
//    protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(TouchpointCRUDServiceImplAlt.class);
//
//    private ServletContext servletContext;
//
//    private GenericCRUDExecutor<AbstractProduct> readExecFromServletContext  () {
//        return (GenericCRUDExecutor<AbstractProduct>)servletContext.getAttribute("touchpointCRUD");
//    }
//
//
//    /**
//     * this accessor will be provided by the ServletContext, to which it is written by the TouchpointServletContextListener
//     */
//    private GenericCRUDExecutor<AbstractTouchpoint> touchpointCRUD;
//
//    /**
//     * here we will be passed the context parameters by the resteasy framework. Alternatively @Context
//     * can  be declared on the respective instance attributes. note that the request context is only
//     * declared for illustration purposes, but will not be further used here
//     *
//     * @param servletContext
//     */
//    public TouchpointCRUDServiceImplAlt(@Context ServletContext servletContext, @Context HttpServletRequest request) {
//        logger.info("<constructor>: " + servletContext + "/" + request);
//        // read out the dataAccessor
//        this.touchpointCRUD = (GenericCRUDExecutor<AbstractTouchpoint>) servletContext.getAttribute("touchpointCRUD");
//
//        logger.debug("read out the touchpointCRUD from the servlet context: " + this.touchpointCRUD);
//    }
//
//
//    @Override
//    public List<AbstractTouchpoint> readAllTouchpoints() {
//        return (List) this.touchpointCRUD.readAllObjects();
//    }
//
//    @Override
//    public AbstractTouchpoint createTouchpoint(StationaryTouchpoint touchpoint) {
//        return (StationaryTouchpoint) this.touchpointCRUD.createObject(touchpoint);
//    }
//
//    @Override
//    public boolean deleteTouchpoint(long id) {
//        return this.touchpointCRUD.deleteObject(id);
//    }
//
//    @Override
//    public StationaryTouchpoint readTouchpoint(long id) {
//        StationaryTouchpoint tp = (StationaryTouchpoint) this.touchpointCRUD.readObject(id);
//        if (tp != null) {
//            return tp;
//        } else {
//            throw new NotFoundException("The touchpoint with id " + id + " does not exist!");
//        }
//    }
//
//    /*
//     * UE JRS1: implement the method for updating touchpoints
//     */
//
//}
