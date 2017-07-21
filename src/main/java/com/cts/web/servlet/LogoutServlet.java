package com.cts.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onelogin.saml2.Auth;
import com.onelogin.saml2.exception.Error;
import com.onelogin.saml2.exception.SettingsException;
import com.onelogin.saml2.exception.XMLEntityException;

import java.io.IOException;
import java.net.URLEncoder;

@WebServlet(urlPatterns = { "/logout" })
public class LogoutServlet extends HttpServlet {

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession() != null) {
			Auth auth;
			try {
				auth = new Auth(request, response);
				// auth.se
				// auth.setSessionIndex((String)request.getSession().getAttribute("SessionIndex"));
				String url = "http://" + request.getServerName() + ":" + request.getServerPort()
						+ request.getContextPath() + "/login";
				while (request.getSession().getAttributeNames().hasMoreElements()) {
					request.getSession().removeAttribute(request.getSession().getAttributeNames().nextElement());
				}
				request.getSession().invalidate();
				auth.logout(url, (String) request.getSession().getAttribute("nameId"),
						(String) request.getSession().getAttribute("SessionIndex"));
			} catch (SettingsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Error e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XMLEntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// response.sendRedirect("/login");
	}

}
