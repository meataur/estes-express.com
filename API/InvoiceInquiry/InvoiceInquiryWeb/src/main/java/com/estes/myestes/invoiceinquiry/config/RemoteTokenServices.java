package com.estes.myestes.invoiceinquiry.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.estes.framework.logger.ESTESLogger;

public class RemoteTokenServices implements ResourceServerTokenServices
{
    private RestOperations restTemplate = new RestTemplate();
    private String checkTokenEndpointUrl;
    private String clientId;
    private String clientSecret;
    private String tokenName = "token";
    private AccessTokenConverter tokenConverter;

	public RemoteTokenServices()
	{
		((RestTemplate) this.restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != HttpStatus.BAD_REQUEST.value()) {
                    super.handleError(response);
                }
            }
        });
	} // constructor

    private Map<String, Object> getForMap(String path)
    {
		Map<String, Object> map = (Map) this.restTemplate.getForObject(path,  Map.class);
		return map;
	} // getForMap

    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException
    {
        Map<String, Object> map = this.getForMap(this.checkTokenEndpointUrl+"?client_id="+this.clientId+"&client_secret="+this.clientSecret+"&"+tokenName+"="+accessToken);
        if (map.containsKey("error")) {
            ESTESLogger.log(ESTESLogger.DEBUG, RemoteTokenServices.class, "loadAuthentication()", "Error on check_token: " + map.get("error"));
            throw new InvalidTokenException(accessToken);
        }
        else {
            return this.tokenConverter.extractAuthentication(map);
        }
    } // loadAuthentication

	public OAuth2AccessToken readAccessToken(String accessToken)
	{
		throw new UnsupportedOperationException("Not supported: read access token");
	} // readAccessToken

    public void setAccessTokenConverter(AccessTokenConverter accessTokenConverter) {
        this.tokenConverter = accessTokenConverter;
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

    public void setRestTemplate(RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }
}
