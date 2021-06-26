package org.dieschnittstelle.ess.entities.erp;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.GenericCRUDEntity;
import org.dieschnittstelle.ess.utils.jsonb.JsonbJsonTypeInfoHandler;

import javax.json.bind.annotation.JsonbTypeDeserializer;
import javax.json.bind.annotation.JsonbTypeSerializer;

import static org.dieschnittstelle.ess.utils.jsonb.JsonbJsonTypeInfoHandler.KLASSNAME_PROPERTY;

/*
 * TODO JRS3: entfernen Sie die Auskommentierung der Annotation
 */
// jrs/jackson annotations
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property=KLASSNAME_PROPERTY)
@JsonIgnoreProperties(ignoreUnknown = true)
// jsonb annotations
@JsonbTypeDeserializer(JsonbJsonTypeInfoHandler.class)
@JsonbTypeSerializer(JsonbJsonTypeInfoHandler.class)
public abstract class AbstractProduct implements Serializable, GenericCRUDEntity {

	protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(AbstractProduct.class);

	/**
	 *
	 */
	private static final long serialVersionUID = 6940403029597060153L;

	private long id;

	private String name;

	private int price;

	public AbstractProduct() {
		logger.info("<constructor>");
	}

	public AbstractProduct(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
