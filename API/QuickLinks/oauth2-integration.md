#Oauth2 Integration
## To integrate Spring Oauth2, just do the following things:

#### 1. Add the following properties to root pom.xml file		
	<properties>
		<spring-version>4.3.18.RELEASE</spring-version>
		<oauth.version>2.3.3.RELEASE</oauth.version>
		<spring.security.version>4.2.7.RELEASE</spring.security.version>
	</properties>

#### 2. Add the following dependency to the WAR modulepom.xml file		
		<dependency>
			<groupId>org.springframework.security.oauth</groupId>
			<artifactId>spring-security-oauth2</artifactId>
            <version>${oauth.version}</version>
		</dependency>
		
		<dependency>
		    <groupId>org.springframework.security</groupId>
		    <artifactId>spring-security-web</artifactId>
		    <version>${spring.security.version}</version>
		</dependency>
		
		<dependency>
		    <groupId>org.springframework.security</groupId>
		    <artifactId>spring-security-core</artifactId>
		    <version>${spring.security.version}</version>
		</dependency>
		<dependency>
		    <groupId>org.springframework.security</groupId>
		    <artifactId>spring-security-config</artifactId>
		    <version>${spring.security.version}</version>
		</dependency>

#### 3. Add the following configuration files to your package

#####Remote Token Service

	import java.io.IOException;
	import java.util.Map;
	
	import org.apache.commons.logging.Log;
	import org.apache.commons.logging.LogFactory;
	import org.springframework.http.client.ClientHttpResponse;
	import org.springframework.security.core.AuthenticationException;
	import org.springframework.security.oauth2.common.OAuth2AccessToken;
	import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
	import org.springframework.security.oauth2.provider.OAuth2Authentication;
	import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
	import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
	import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
	import org.springframework.web.client.DefaultResponseErrorHandler;
	import org.springframework.web.client.RestOperations;
	import org.springframework.web.client.RestTemplate;

	public class RemoteTokenServices implements ResourceServerTokenServices {
	    protected final Log logger = LogFactory.getLog(this.getClass());
	    private RestOperations restTemplate = new RestTemplate();
	    private String checkTokenEndpointUrl;
	    private String clientId;
	    private String clientSecret;
	    private String tokenName = "token";
	    private AccessTokenConverter tokenConverter;

	    public RemoteTokenServices() {
	        ((RestTemplate)this.restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
	            public void handleError(ClientHttpResponse response) throws IOException {
	                if (response.getRawStatusCode() != 400) {
	                    super.handleError(response);
	                }
	
	            }
	        });
	    }

	    public void setRestTemplate(RestOperations restTemplate) {
	        this.restTemplate = restTemplate;
	    }
	
	    public void setCheckTokenEndpointUrl(String checkTokenEndpointUrl) {
	        this.checkTokenEndpointUrl = checkTokenEndpointUrl;
	    }
	
	    public void setClientId(String clientId) {
	        this.clientId = clientId;
	    }
	
	    public void setClientSecret(String clientSecret) {
	        this.clientSecret = clientSecret;
	    }
	
	    public void setAccessTokenConverter(AccessTokenConverter accessTokenConverter) {
	        this.tokenConverter = accessTokenConverter;
	    }
	
	    public void setTokenName(String tokenName) {
	        this.tokenName = tokenName;
	    }
	
	    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
	
	        Map<String, Object> map = this.getForMap(this.checkTokenEndpointUrl+"?client_id="+this.clientId+"&client_secret="+this.clientSecret+"&"+tokenName+"="+accessToken);
	        if (map.containsKey("error")) {
	
	            if (this.logger.isDebugEnabled()) {
	                this.logger.debug("check_token returned error: " + map.get("error"));
	            }
	
	            throw new InvalidTokenException(accessToken);
	        } else {
	
	            return this.tokenConverter.extractAuthentication(map);
	        }
	    }
	
	    public OAuth2AccessToken readAccessToken(String accessToken) {
	        throw new UnsupportedOperationException("Not supported: read access token");
	    }
	
	
	
	    private Map<String, Object> getForMap(String path) {
	        Map map = (Map) this.restTemplate.getForObject(path,  Map.class);
	        return map;
	    }
	}
		
	
	
##### Custom Access Token Converter
	
	import java.util.Map;
	import org.springframework.security.oauth2.provider.OAuth2Authentication;
	import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
	
	
	public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {
	
	    @Override
	    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
	        OAuth2Authentication authentication = super.extractAuthentication(claims);
	        authentication.setDetails(claims);
	        return authentication;
	    }
	}
	
##### Oauth2 Resource Server Configuration
	
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.context.annotation.Primary;
	import org.springframework.security.authentication.AuthenticationManager;
	import org.springframework.security.config.annotation.web.builders.HttpSecurity;
	import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
	import org.springframework.security.config.http.SessionCreationPolicy;
	import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
	import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
	import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
	import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
	
	@Configuration
	@EnableResourceServer
	@EnableWebSecurity
	public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
		
		@Override
		public void configure(final HttpSecurity http) throws Exception {
			// @formatter:off
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			//.and().authorizeRequests()
					//.anyRequest().access("#oauth2.hasScope('EDITPROF')")
			.and().authorizeRequests().antMatchers("/links/**")
					.authenticated();
			// @formatter:on
		}
	
		
		@Bean
		public RemoteTokenServices tokenServices() {
			final RemoteTokenServices tokenService = new RemoteTokenServices();
			// http://appsec.qa.estesinternal.com/security/oauth/token
			// tokenService.setCheckTokenEndpointUrl("http://localhost:9080/security/oauth/check_token");
			tokenService.setCheckTokenEndpointUrl("http://appsec.qa.estesinternal.com/security/oauth/check_token");
			tokenService.setClientId("MY_ESTES");
			tokenService.setClientSecret(null);
			tokenService.setAccessTokenConverter(accessTokenConverter());
			return tokenService;
		}
	
		@Bean
		public AccessTokenConverter accessTokenConverter() {
			return new CustomAccessTokenConverter();
		}
		
		@Primary
		@Bean
		public AuthenticationManager authenticationManager() throws Exception {
			OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
			authenticationManager.setTokenServices(tokenServices());
			return authenticationManager;
		}
	
	}
##### Global Method Security
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
	import org.springframework.security.authentication.AuthenticationManager;
	import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
	import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
	import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
	
	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true,proxyTargetClass=true)
	public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
		@Autowired
		AuthenticationManager authenticationManager;
		@Override
		protected MethodSecurityExpressionHandler createExpressionHandler() {
			return new OAuth2MethodSecurityExpressionHandler();
		}
	}
	
##### Cors Filter
	import java.io.IOException;
	
	import javax.servlet.Filter;
	import javax.servlet.FilterChain;
	import javax.servlet.FilterConfig;
	import javax.servlet.ServletException;
	import javax.servlet.ServletRequest;
	import javax.servlet.ServletResponse;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	
	import org.springframework.core.Ordered;
	import org.springframework.core.annotation.Order;
	import org.springframework.stereotype.Component;
	
	@Component
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public class CorsFilter implements Filter {
	
	    @Override
	    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
	        final HttpServletResponse response = (HttpServletResponse) res;
	        response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
	        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
	        response.setHeader("Access-Control-Max-Age", "3600");
	        if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
	            response.setStatus(HttpServletResponse.SC_OK);
	        } else {
	            chain.doFilter(req, res);
	        }
	    }
	
	    @Override
	    public void destroy() {
	    }
	
	    @Override
	    public void init(FilterConfig config) throws ServletException {
	    }
	}
	
	
##### Security Web Application Initializer
	import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
	
	public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
	
	}
	
	
### From Your Controller method, you can access user object

 	@PreAuthorize("#oauth2.hasScope('EDITPROF')")
    @RequestMapping(method = RequestMethod.GET, value = "/users/extra")
    @ResponseBody
    public Map<String, Object> getExtraInfo(Authentication auth) {
        OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
		Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
        return details;
    }
    
#### Response looks alike
	{
	    "accountCode": "9996995",
	    "user_name": "com.estes.security.model.User@8a17c712",
	    "session": "2889003",
	    "scope": [
	        "QUN200",
	        "WRVIEWING",
	        "QUOTEHIST",
	        "CLAIMSFILE",
	        "EDITPROF",
	        "QUN100",
	        "ECN200",
	        "ECN300",
	        "ADMINSUBS",
	        "SHIPMAN",
	        "SDN015",
	        "PKN200",
	        "LTLRATEQOT",
	        "SHIPTRACK",
	        "TMN100",
	        "OLDBOL",
	        "RSG10O100",
	        "EDN426",
	        "EBG10O120",
	        "CLAIMIN",
	        "EBG10O101",
	        "MEXCHAT",
	        "BOL100",
	        "PICKUPHIST",
	        "VTLQUOTE",
	        "PDN100",
	        "FRN100",
	        "PKN211",
	        "INVINQUIRY",
	        "ONL100",
	        "DSN10O100",
	        "TIMECRIT"
	    ],
	    "accountType": "N",
	    "exp": 1534182305,
	    "authorities": [
	        {
	            "authority": "ROLE_N"
	        }
	    ],
	    "hash": "FF167AE70D7CA4CD6BB9E063B3BAE01B404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040",
	    "client_id": "MY_ESTES",
	    "username": "testnat"
	}

    	