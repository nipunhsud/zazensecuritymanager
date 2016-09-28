package com.securitymanager.v1.configuration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class SimpleCorsFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpServletReponse=(HttpServletResponse) response;
		if(request instanceof HttpServletRequest)
		{
			HttpServletRequest httpServletRequest=(HttpServletRequest) request;
			String additionalHeader=((HttpServletRequest) request).getHeader("Access-Control-Request-Headers");
			if(additionalHeader != null)
			{
				httpServletReponse.setHeader("Access-Control-Allow-Headers", additionalHeader);
			}
			httpServletReponse.setHeader("Access-Control-Max-Age", "3600");
			httpServletReponse.setHeader("Access-Control-Allow-Origin", "*");
			httpServletReponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		}
		
		chain.doFilter(request, response);
	}

	public void destroy() {
		
	}

}
