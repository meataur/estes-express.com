/**
 * 
 */
package com.estes.framework.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import com.estes.framework.logger.ESTESLogger;

/**
 * @author sinhasu
 * 
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private String defaultFailureUrl;
	private String baseFailureUrl;
	private boolean forwardToDestination = false;
	private boolean allowSessionCreation = true;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public CustomAuthenticationFailureHandler() {
	}

	public CustomAuthenticationFailureHandler(String defaultFailureUrl) {
		setDefaultFailureUrl(defaultFailureUrl);
	}

	/**
	 * Performs the redirect or forward to the {@code defaultFailureUrl} if set,
	 * otherwise returns a 401 error code.
	 * <p>
	 * If redirecting or forwarding, {@code saveException} will be called to
	 * cache the exception for use in the target view.
	 */
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {

		if (defaultFailureUrl == null) {
			ESTESLogger.log(ESTESLogger.INFO, this.getClass(), "onAuthenticationFailure",
					"No failure URL set, sending 401 Unauthorized error");

			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + exception.getMessage());
		} else {
			saveException(request, exception);
 
			modifyDefaultFailureUrl(SecurityContextHolder.getContext().getAuthentication());

 
			if (forwardToDestination) {
				ESTESLogger.log(ESTESLogger.INFO, this.getClass(), "onAuthenticationFailure", "Forwarding to " + defaultFailureUrl);
				request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
			} else {
				ESTESLogger.log(ESTESLogger.INFO, this.getClass(), "onAuthenticationFailure", "Redirecting to " + defaultFailureUrl);
				redirectStrategy.sendRedirect(request, response, defaultFailureUrl);
			}
		}
	}

	/**
	 * 
	 * @param pAuthentication
	 */
	private void modifyDefaultFailureUrl(Authentication pAuthentication) {

		if (pAuthentication != null && pAuthentication.getPrincipal() != null) {
			if (defaultFailureUrl.contains("?")) {
				defaultFailureUrl = baseFailureUrl + "&tUserName=" + pAuthentication.getPrincipal();
			} else {
				defaultFailureUrl = baseFailureUrl + "?tUserName=" + pAuthentication.getPrincipal();
			}
			ESTESLogger.log(ESTESLogger.INFO, this.getClass(), "modifyDefaultFailureUrl()", "Authentication Failed for User Name ::"
					+ pAuthentication.getPrincipal());
		}
	}

	/**
	 * Caches the {@code AuthenticationException} for use in view rendering.
	 * <p>
	 * If {@code forwardToDestination} is set to true, request scope will be
	 * used, otherwise it will attempt to store the exception in the session. If
	 * there is no session and {@code allowSessionCreation} is {@code true} a
	 * session will be created. Otherwise the exception will not be stored.
	 */
	protected final void saveException(HttpServletRequest request, AuthenticationException exception) {
		if (forwardToDestination) {
			request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
		} else {
			HttpSession session = request.getSession(false);

			if (session != null || allowSessionCreation) {
				request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
			}
		}
	}

	/**
	 * The URL which will be used as the failure destination.
	 * 
	 * @param defaultFailureUrl
	 *            the failure URL, for example "/loginFailed.jsp".
	 */
	public void setDefaultFailureUrl(String defaultFailureUrl) {
		Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl), "'" + defaultFailureUrl + "' is not a valid redirect URL");
		this.defaultFailureUrl = defaultFailureUrl;
	}

	/**
	 * @param baseFailureUrl
	 *            the baseFailureUrl to set
	 */
	public void setBaseFailureUrl(String baseFailureUrl) {
		this.baseFailureUrl = baseFailureUrl;
	}

	protected boolean isUseForward() {
		return forwardToDestination;
	}

	/**
	 * If set to <tt>true</tt>, performs a forward to the failure destination
	 * URL instead of a redirect. Defaults to <tt>false</tt>.
	 */
	public void setUseForward(boolean forwardToDestination) {
		this.forwardToDestination = forwardToDestination;
	}

	/**
	 * Allows overriding of the behaviour when redirecting to a target URL.
	 */
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

	protected boolean isAllowSessionCreation() {
		return allowSessionCreation;
	}

	public void setAllowSessionCreation(boolean allowSessionCreation) {
		this.allowSessionCreation = allowSessionCreation;
	}
}
