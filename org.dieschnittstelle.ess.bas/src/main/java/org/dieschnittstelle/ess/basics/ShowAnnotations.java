package org.dieschnittstelle.ess.basics;


import org.dieschnittstelle.ess.basics.annotations.AnnotatedStockItemBuilder;

import org.dieschnittstelle.ess.basics.annotations.StockItemProxyImpl;
import org.dieschnittstelle.ess.basics.annotations.Units;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.dieschnittstelle.ess.utils.Utils.*;

public class ShowAnnotations {

    public static void main(String[] args) {
        // we initialise the collection
        StockItemCollection collection = new StockItemCollection(
                "stockitems_annotations.xml", new AnnotatedStockItemBuilder());
        // we load the contents into the collection
        collection.load();

        for (IStockItem consumable : collection.getStockItems()) {
            ;
            showAttributes(((StockItemProxyImpl) consumable).getProxiedObject());
        }

//        // we initialise a consumer
//        Consumer consumer = new Consumer();
//        // ... and let them consume
//        consumer.doShopping(collection.getStockItems());
    }

    /*
     * TODO BAS2
     */

//    BAS2 dirty solution
//    private static void showAttributes(Object consumable) {
//        Class klass = consumable.getClass();
//        String result = "";
//
//        for (Field field : klass.getDeclaredFields()) {
//            field.setAccessible(true);
//
//            try {
//                result = result.concat(" " + field.getName() + ":" + field.get(consumable) + ",");
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//        result = result.substring(0, result.length() -1).concat("}");
//        show("{" + klass.getSimpleName() + result);
//    }

    //    BAS2 invoke solution
    private static void showAttributes(Object consumable) {
        Class consumableClass = consumable.getClass();
        String result = "";

        for (Field field : consumableClass.getDeclaredFields()) {
            try {
                String getterName = getAccessorNameForField("get", field.getName());
                Method getter = consumableClass.getDeclaredMethod(getterName);
                result = result.concat(" " + field.getName() + ":" + getter.invoke(consumable).toString() + ",");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        result = result.substring(0, result.length() -1).concat("}");
        show("{" + consumableClass.getSimpleName() + result);
    }



    // create getter/setter names ( from ReflectedStockItemBuilder.java )
    public static String getAccessorNameForField(String accessor, String fieldName) {
        return accessor + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }


}
