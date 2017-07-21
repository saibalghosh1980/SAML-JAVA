package com.cts.web.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/portal/*")
public class Auth0Filter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	/**
	 * Perform filter check on this request - verify the User Id is present.
	 *
	 * @param request
	 *            the received request
	 * @param response
	 *            the response to send
	 * @param next
	 *            the next filter chain
	 **/
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String url = ((HttpServletRequest)request).getRequestURL().toString();
		if (req.getSession().getAttribute("attributes")==null ||req.getSession().getAttribute("nameId")==null) {			
			res.sendRedirect("/login?requestedURL="+URLEncoder.encode(url));
			return;
		}
		
		next.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
