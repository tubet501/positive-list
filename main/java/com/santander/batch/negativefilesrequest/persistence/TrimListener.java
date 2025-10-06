package com.santander.batch.negativefilesrequest.persistence;

import javax.persistence.PostLoad;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This class contains a two methods
 * for trim string
 * <p>
 * it is used on cardServiceImpl
 */
public class TrimListener {

    /**
     * This method call to getTrimProperties(),
     * and return the same String trimmed.
     *
     * @param entity the entity
     * @throws Exception the exception
     */
    @PostLoad
    public void repairAfterLoad(final Object entity) throws Exception {
        for (Field fieldToTrim : getTrimProperties(entity)) {
            String propertyValue = (String) fieldToTrim.get(entity);
            /*if (propertyValue != null) {
                fieldToTrim.set(entity, propertyValue.trim());
            }*/
        }
    }

    /**
     * This method make a trim
     * of any string
     *
     * @param entity
     * @return A SetList of elements
     * @throws Exception
     */
    private Set<Field> getTrimProperties(Object entity) throws Exception {
        Class<?> entityClass = entity.getClass();

        if (Object.class.equals(entityClass)){
            return Collections.emptySet();
        }


        Set<Field> propertiesToTrim = new HashSet<Field>();
        for (Field field : entityClass.getDeclaredFields()) {
            if (!Modifier.isFinal(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) {
                //field.setAccessible(true);
                if (field.getType().equals(String.class)) {
                    //field.setAccessible(true);
                    propertiesToTrim.add(field);
                } else if (field.getName().endsWith("EntityPK")) {
                    //field.setAccessible(true);
                    //repairAfterLoad(field.get(entity));
                } else{
                    field.setAccessible(false);
                }
            }
        }

        return propertiesToTrim;
    }
}
