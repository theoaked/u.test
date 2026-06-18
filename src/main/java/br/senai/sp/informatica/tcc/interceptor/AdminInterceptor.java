package br.senai.sp.informatica.tcc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		uri = uri.replace("/u.test", "");
		if (uri.endsWith("login") || uri.endsWith("logar")) {
			return true;
		} else if (uri.startsWith("/admin") && request.getSession().getAttribute("admLogado") == null) {
			response.sendRedirect("../login");
			return false;
		} else if (uri.startsWith("/prof") && request.getSession().getAttribute("profLogado") == null) {
			response.sendRedirect("../login");
			return false;
		} else if (uri.startsWith("/user") && request.getSession().getAttribute("userLogado") == null) {
			response.sendRedirect("../login");
			return false;
		}
		return true;
	}
}
