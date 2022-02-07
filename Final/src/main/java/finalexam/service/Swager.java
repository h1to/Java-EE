package finalexam.service;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;

import io.swagger.jaxrs.config.BeanConfig;

public class Swager {
    public Swager(@Context ServletConfig servletConfig) {
        super();

        BeanConfig beanConfig = new BeanConfig();

        beanConfig.setVersion("1.0.0");
        beanConfig.setTitle("Todo API");
        beanConfig.setBasePath("/todo/api");
        beanConfig.setResourcePackage("com.synaptik.javaee");
        beanConfig.setScan(true);
    }
}
