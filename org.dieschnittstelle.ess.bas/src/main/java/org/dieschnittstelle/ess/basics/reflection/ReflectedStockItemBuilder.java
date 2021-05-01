package org.dieschnittstelle.ess.basics.reflection;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.basics.IStockItem;
import org.dieschnittstelle.ess.basics.IStockItemBuilder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.dieschnittstelle.ess.utils.Utils.*;

// this builder only reads in the item
public class ReflectedStockItemBuilder implements IStockItemBuilder {

    protected static Logger logger = LogManager
            .getLogger(ReflectedStockItemBuilder.class);

    @Override
    public IStockItem buildStockItemFromElement(Element el) {

        try {
            // obtain the child nodes
            NodeList children = el.getChildNodes();
            // iterate over the nodes and populate a map of attributes that we will use later for instantiating the java objects
            Map<String, String> instanceAttributes = new HashMap<String, String>();

            for (int i = 0; i < children.getLength(); i++) {
                // once we have found an element node we check its name
                if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    // get the textual content and the name
                    String elementContent = ((Element) children.item(i))
                            .getTextContent();
                    String elementName = ((Element) children.item(i))
                            .getTagName();
                    logger.debug("found element " + elementName
                            + " with content: " + elementContent);
                    instanceAttributes.put(elementName, elementContent);
                } else {
                    // logger.debug("found node " + children.item(i)
                    // + " of class " + children.item(i).getClass());
                }
            }

            logger.info("read out child elements and values: " + instanceAttributes);

            IStockItem instance = null;
            // determine the classname from the instance attributes
            String klassname = instanceAttributes.get("class");
            show("klassname: %s", klassname);

            // access the class
            Class klass = Class.forName(klassname);
            show("klass: %s", klass);

            Arrays.asList(klass.getDeclaredFields()).forEach(attr -> {
                show("found attribute: %s of type %s", attr.getName(), attr.getType());
            });

            // show attributes and methods
            Arrays.stream(klass.getDeclaredMethods()).forEach(meth -> {
                show("found method: %s with arg types %s", meth.getName(), Arrays.asList(meth.getParameterTypes(), meth.getGenericReturnType()));
            });

            show("found superclass: %s", klass.getSuperclass());
            show("found interfaces: %s", Arrays.asList(klass.getInterfaces()));

            // create the instance (with getCunstroctor()...)
			instance = (IStockItem) klass.getConstructor(new Class[]{}).newInstance();

            show("created instance: %s", instance);

            // call initialise method
            instance.initialise(Integer.parseInt(instanceAttributes.get("units")), instanceAttributes.get("brandname"));

            // remove all attributes that have been processed so far
            instanceAttributes.remove("class");
            instanceAttributes.remove("brandname");
            instanceAttributes.remove("units");

            // iterate over the so far unprocessed attributes
            for (String xmlAttr : instanceAttributes.keySet()) {
                String value = instanceAttributes.get(xmlAttr);
                show("set: %s=%s", xmlAttr, instanceAttributes.get(xmlAttr));

                // determine the field object for the attribute
                Field attr = klass.getDeclaredField(xmlAttr);

                // determine the name of the setter
                String setterName = getAccessorNameForField("set", attr.getName());

                // declare the argument types of the setter
                // ALT: Class[] setterargs = new Class[]{attr.getType()};
                Type setterType = attr.getType();
                // obtain the setter
                Method setter = klass.getDeclaredMethod(setterName, (Class) setterType);
                show("setter: %s", setter);
                // ALT: Method setter = klass.getDeclaredMethod(settername,setterargs);

                // invoke the setter
                if (setterType == Integer.TYPE || setterType == Integer.class) {
                    setter.invoke(instance, Integer.parseInt(value));
                } else {
                    setter.invoke(instance, String.valueOf(value));
                }

            }
            // and pass back the instance
            return (IStockItem) instance;
        } catch (ClassNotFoundException e) {
            logger.error("got ClassNotFoundException: " + e, e);
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            logger.error("got InstantiationException: " + e, e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            logger.error("got IllegalAccessException: " + e, e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("got Exception: " + e, e);
            throw new RuntimeException(e);
        }

    }

    /*
     * create getter/setter names
     */
    public static String getAccessorNameForField(String accessor, String fieldName) {
        return accessor + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

}
