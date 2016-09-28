package com.securitymanager.v1.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Component
public class SecurityManagerConfiurationListener  extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {SecurityManagerServiceConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}
	
	protected Filter[] getServletFilters(){
		List<Filter> filters=new ArrayList<Filter>();
		filters.add(new SimpleCorsFilter());
		return filters.toArray(new Filter[filters.size()]);
	}

}
