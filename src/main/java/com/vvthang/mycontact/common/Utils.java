package com.vvthang.mycontact.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Utils {
    
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    
    /** The Constant mapper. */
    private static final ObjectMapper mapper = new ObjectMapper();
    
    /**
     * Parses the object to json.
     *
     * @param object the object
     * @return the json string
     */
    public static String parseObjectToJson(Object object) {
        logger.debug("IN - parseObjectToJson");
        if (object == null) {
            logger.debug("OUT - parseObjectToJson");
            return null;
        }
        String json = null;
        try {
            logger.debug("OUT - parseObjectToJson");
            mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.info("Exception: ", e);
        }
        return json;
    }
}
