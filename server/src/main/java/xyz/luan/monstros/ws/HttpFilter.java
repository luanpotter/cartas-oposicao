package xyz.luan.monstros.ws;

import io.yawp.commons.http.HttpException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

public abstract class HttpFilter implements Filter {

	private static final Logger LOGGER = Logger.getLogger(HttpFilter.class.getCanonicalName());

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		HttpServletResponse resp = (HttpServletResponse) response;
		try {
			filter((HttpServletRequest) request, resp);
			chain.doFilter(request, response);
		} catch (HttpException ex) {
			handleException(resp, ex);
		} catch (Exception e) {
			LOGGER.log(SEVERE, "Unexpected error on Filter", e);
			handleException(resp, new HttpException(500, "Unexpected error on filter"));
		}
	}

	private void handleException(HttpServletResponse response, HttpException ex) {
		response.setStatus(ex.getHttpStatus());
		try (PrintWriter writer = response.getWriter()) {
			writer.write(ex.getText());
		} catch (IOException e) {
			LOGGER.log(SEVERE, "Unexpected exception while writing response", e);
		}
	}

	protected abstract void filter(HttpServletRequest request, HttpServletResponse response) throws Exception;

	@Override
	public void destroy() {
	}
}
