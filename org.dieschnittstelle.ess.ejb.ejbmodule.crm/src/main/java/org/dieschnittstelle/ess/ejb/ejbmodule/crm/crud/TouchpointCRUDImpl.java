package org.dieschnittstelle.ess.ejb.ejbmodule.crm.crud;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dieschnittstelle.ess.ejb.ejbmodule.crm.shopping.ShoppingException;
import org.dieschnittstelle.ess.entities.crm.AbstractTouchpoint;
import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.utils.interceptors.Logged;

import static org.dieschnittstelle.ess.utils.Utils.show;

@Logged
public class TouchpointCRUDImpl implements TouchpointCRUD {

	protected static Logger logger = org.apache.logging.log4j.LogManager
			.getLogger(TouchpointCRUDImpl.class);

	@Inject
	@EntityManagerProvider.CRMDataAccessor
	private EntityManager em;

	/*
	 * TODO ADD1: run CreateTouchpointsAccessingCRUD in the client project with the @TransactionAttribute commented in - what happens?
	 */
	@Override
	public AbstractTouchpoint createTouchpoint(AbstractTouchpoint touchpoint) throws ShoppingException {

		/*
		 * TODO ADD1: swap true/false
		 */		
		if (/*true*/false) {
			throw new RuntimeException(new ShoppingException(
					ShoppingException.ShoppingSessionExceptionReason.UNKNOWN));
		} else {
			em.persist(touchpoint);
			return touchpoint;
		}

	}

	@Override
	public AbstractTouchpoint readTouchpoint(long id) {
		AbstractTouchpoint touchpoint = em.find(AbstractTouchpoint.class, id);

		return touchpoint;
	}

	@Override
	public AbstractTouchpoint updateTouchpoint(AbstractTouchpoint touchpoint) {
		touchpoint = em.merge(touchpoint);
		return touchpoint;
	}

	@Override
	public boolean deleteTouchpoint(int id) {
		em.remove(em.find(AbstractTouchpoint.class, id));

		return true;
	}

	@Override
	public List<AbstractTouchpoint> readAllTouchpoints() {

		show("entity manager is: " + this.em);

		Query query = em.createQuery("SELECT t FROM AbstractTouchpoint AS t");

		List<AbstractTouchpoint> tps = (List<AbstractTouchpoint>) query
				.getResultList();

		return tps;
	}

}