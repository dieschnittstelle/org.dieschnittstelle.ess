package org.dieschnittstelle.ess.jrs.client.junit;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.crm.StationaryTouchpoint;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;

import org.dieschnittstelle.ess.jrs.IProductCRUDService;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.dieschnittstelle.ess.entities.erp.ProductType;
import org.dieschnittstelle.ess.jrs.IProductCRUDService;
import org.dieschnittstelle.ess.jrs.ITouchpointCRUDService;
import org.dieschnittstelle.ess.utils.Utils;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import static org.dieschnittstelle.ess.utils.Utils.show;


public class ProductCRUDRESTClient {

	private IProductCRUDService serviceProxy;

	protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(ProductCRUDRESTClient.class);

	public ProductCRUDRESTClient() throws Exception {

		/*
		 * create a client for the web service using ResteasyClientBuilder and ResteasyWebTarget
		 */

		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/api/");
		serviceProxy = target.proxy(IProductCRUDService.class);
	}

	public AbstractProduct createProduct(AbstractProduct prod) {
		AbstractProduct created = serviceProxy.createProduct(prod);
		// as a side-effect we set the id of the created product on the argument before returning
		prod.setId(created.getId());
		return created;
	}

	public List<?> readAllProducts() {
		return serviceProxy.readAllProducts();
	}

	public AbstractProduct updateProduct(AbstractProduct update) {
		return serviceProxy.updateProduct(update.getId(),(IndividualisedProductItem)update);
	}

	public boolean deleteProduct(long id) {
		return serviceProxy.deleteProduct(id);
	}

	public AbstractProduct readProduct(long id) {
		return serviceProxy.readProduct(id);
	}

}
