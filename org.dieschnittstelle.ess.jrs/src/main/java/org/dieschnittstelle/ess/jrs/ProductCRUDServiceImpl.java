package org.dieschnittstelle.ess.jrs;

import java.util.List;

import org.dieschnittstelle.ess.entities.GenericCRUDExecutor;
import org.dieschnittstelle.ess.entities.crm.AbstractTouchpoint;
import org.dieschnittstelle.ess.entities.crm.StationaryTouchpoint;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

/*
 * TODO JRS2: implementieren Sie hier die im Interface deklarierten Methoden
 */

public class ProductCRUDServiceImpl implements IProductCRUDService {

	@Context
	private ServletContext servletContext;

	private GenericCRUDExecutor<AbstractProduct> readExecFromServletContext() {
		return (GenericCRUDExecutor<AbstractProduct>) servletContext.getAttribute("productCRUD");
	}

	@Override
	public List<AbstractProduct> readAllProducts() {
		return (List) readExecFromServletContext().readAllObjects();

	}


	@Override
	public AbstractProduct createProduct(AbstractProduct prod) {
	return (AbstractProduct) readExecFromServletContext().createObject(prod);
	}

	@Override
	public boolean deleteProduct(long id) {
		return readExecFromServletContext().deleteObject(id);
	}

	@Override
	public AbstractProduct readProduct(long id) {
		return (AbstractProduct) readExecFromServletContext().readObject(id);
	}

	@Override
	public AbstractProduct updateProduct(long id,
										 AbstractProduct update) {
		return (AbstractProduct) readExecFromServletContext().updateObject(update);
	}






}
