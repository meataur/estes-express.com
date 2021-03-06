package com.estes.myestes.profile.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * Spring Application will be initialized here.
 * @author rahmaat
 *
 */
public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
      // Create the 'root' Spring application context
      AnnotationConfigWebApplicationContext rootContext =
        new AnnotationConfigWebApplicationContext();
      rootContext.register(AppConfig.class);
      // Manage the lifecycle of the root application context
      container.addListener(new ContextLoaderListener(rootContext));

      // Create the dispatcher servlet's Spring application context
      AnnotationConfigWebApplicationContext dispatcherContext =
        new AnnotationConfigWebApplicationContext();
      dispatcherContext.register(DispatcherServletConfig.class);
      
      
      DispatcherServlet dispatcherServlet = new DispatcherServlet(dispatcherContext);
      // Register and map the dispatcher servlet
      ServletRegistration.Dynamic dispatcher =
        container.addServlet("dispatcher",dispatcherServlet);
      dispatcher.setLoadOnStartup(1);
      dispatcher.setInitParameter("throwExceptionIfNoHandlerFound", "true");
      dispatcher.addMapping("/");
    }

 }