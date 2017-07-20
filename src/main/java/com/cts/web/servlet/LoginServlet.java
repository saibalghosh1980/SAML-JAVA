package com.cts.web.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onelogin.saml2.Auth;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse res)
			throws ServletException, IOException {
		try { 
			Auth auth = new Auth(req, res);
			auth.login(req.getParameter("requestedURL"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
