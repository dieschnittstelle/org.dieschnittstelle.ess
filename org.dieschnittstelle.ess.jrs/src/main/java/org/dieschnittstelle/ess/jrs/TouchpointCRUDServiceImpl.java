package org.dieschnittstelle.ess.jrs;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.dieschnittstelle.ess.entities.crm.StationaryTouchpoint;
import org.dieschnittstelle.ess.entities.crm.AbstractTouchpoint;
import org.dieschnittstelle.ess.entities.GenericCRUDExecutor;

public class TouchpointCRUDServiceImpl implements ITouchpointCRUDService {

    @Context
    private ServletContext servletContext;

    private GenericCRUDExecutor<AbstractTouchpoint> readExecFromServletContext() {
        return (GenericCRUDExecutor<AbstractTouchpoint>) servletContext.getAttribute("touchpointCRUD");
    }


    @Override
    public List<AbstractTouchpoint> readAllTouchpoints() {
        return (List) readExecFromServletContext().readAllObjects();
    }

    @Override
    public AbstractTouchpoint createTouchpoint(AbstractTouchpoint touchpoint) {
        return (AbstractTouchpoint) readExecFromServletContext().createObject(touchpoint);
    }

    @Override
    public boolean deleteTouchpoint(long id) {
        return readExecFromServletContext().deleteObject(id);
    }

    @Override
    public AbstractTouchpoint readTouchpoint(long id) {
        return null;
    }
}