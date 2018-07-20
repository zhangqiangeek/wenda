package com.wenda.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * Mbean主类:
 * ManagedResource注解
 * ManagedAttribute注解
 *
 * @author evilhex.
 * @date 2018/7/6 上午10:38.
 */
@Component
@ManagedResource(objectName = "testBean:name=MbeanTest")
public class MbeanTest {

    private static final Logger logger = LoggerFactory.getLogger(MbeanTest.class);
    private static String MbeanVar = "www.evilhex.com";

    @ManagedAttribute
    public String getMbeanVar() {
        return MbeanVar;
    }

    @ManagedAttribute
    public void setMbeanVar(String mbeanVar) {
        MbeanVar = mbeanVar;
        logger.info("set MbeanVar {}",mbeanVar);
    }
}
