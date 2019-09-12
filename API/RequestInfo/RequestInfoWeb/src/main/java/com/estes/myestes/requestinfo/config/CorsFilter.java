/**
 * 
 */

package com.estes.myestes.requestinfo.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import com.estes.myestes.requestinfo.util.RequestInfoConstant;

/**
 * Filter class for JavaScript cross origin restriction
 */
public class CorsFilter extends OncePerRequestFilter
{
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		response.setHeader("Access-Control-Allow-Origin", System.getProperty(RequestInfoConstant.ORIGIN_DOMAIN));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, HEAD, OPTIONS, PUT");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, access-token, "
        		+ "Access-Control-Request-Headers,X-XSRF-TOKEN,username,password, token, Access-Control-Expose-Headers, Authorization");
        filterChain.doFilter(request, response);
	} // doFilterInternal
}