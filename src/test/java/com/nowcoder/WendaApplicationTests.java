package com.nowcoder;

import com.wenda.WendaApplication;
import org.apache.naming.factory.BeanFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
@WebAppConfiguration
public class WendaApplicationTests {

    @Test
    public void contextLoads() {
    }

}
