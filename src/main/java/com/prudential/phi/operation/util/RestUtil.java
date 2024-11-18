package com.prudential.phi.operation.util;

import java.net.Socket;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.hc.client5.http.classic.methods.HttpPatch;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestUtil {
	
	@Autowired
    private RestTemplate restTemplate;

	

	@Value("${crm.disableSSLCertificateValidation}")
	private Boolean CRM_DISABLE_SSL_VALIDATION;

	private static final TrustManager MOCK_TRUST_MANAGER = new X509ExtendedTrustManager() {
		@Override
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return new java.security.cert.X509Certificate[0];
		}

		@Override
		public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
				throws CertificateException {
			// empty method
		}
		// ... Other void methods

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			// TODO Auto-generated method stub

		}

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1, Socket arg2) throws CertificateException {
			// TODO Auto-generated method stub

		}

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1, SSLEngine arg2)
				throws CertificateException {
			// TODO Auto-generated method stub

		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1, Socket arg2) throws CertificateException {
			// TODO Auto-generated method stub

		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1, SSLEngine arg2)
				throws CertificateException {
			// TODO Auto-generated method stub

		}
	};

	public String sendGET(String url, String relativePath, String accessToken, MultiValueMap<String, String> params) {
		if (CRM_DISABLE_SSL_VALIDATION) {
			disableSSLCertificateValidation();
		}
		RestClient restClient = RestClient.builder().requestFactory(new SimpleClientHttpRequestFactory()).baseUrl(url)
				.defaultHeaders(httpHeaders -> {
					httpHeaders.setBearerAuth(accessToken);
				}).build();
		ResponseEntity<String> response = restClient.get()
				.uri(uriBuilder -> uriBuilder.path("/" + relativePath).queryParams(params).build()).retrieve()
				.toEntity(String.class);
		return response.getBody();
	}
	
	public Integer sendDELETE(String url, String relativePath, String accessToken, MultiValueMap<String, String> params) {
		if (CRM_DISABLE_SSL_VALIDATION) {
			disableSSLCertificateValidation();
		}
		RestClient restClient = RestClient.builder().requestFactory(new SimpleClientHttpRequestFactory()).baseUrl(url)
				.defaultHeaders(httpHeaders -> {
					httpHeaders.setBearerAuth(accessToken);
				}).build();
		ResponseEntity<String> response = restClient.delete()
				.uri(uriBuilder -> uriBuilder.path("/" + relativePath).queryParams(params).build()).retrieve()
				.toEntity(String.class);
		return response.getStatusCode().value();
	}
	

	public String sendPost(String url, String accessToken, String body, String relativePath, MediaType contentType)
			throws JsonProcessingException {
		if (CRM_DISABLE_SSL_VALIDATION) {
			disableSSLCertificateValidation();
		}

		RestClient restClient = RestClient.builder().requestFactory(new SimpleClientHttpRequestFactory()).baseUrl(url)
				.defaultHeaders(httpHeaders -> {
					httpHeaders.setBearerAuth(accessToken);
					httpHeaders.set("Content-Type", contentType.toString());
				}).build();
		ResponseEntity<String> response = restClient.post()
				.uri(uriBuilder -> uriBuilder.path("/" + relativePath).build()).contentType(contentType).body(body)
				.retrieve().toEntity(String.class);
		return response.getBody();
	}

	@SuppressWarnings("deprecation")
	public Integer sendPatch(String url, String accessToken, String body, String relativePath, MediaType contentType)
			throws Exception {
		if (CRM_DISABLE_SSL_VALIDATION) {
			disableSSLCertificateValidation();
		}

		SSLContext sslContext = SSLContext.getInstance("SSL"); // OR TLSv1.2
		sslContext.init(null, new TrustManager[] { MOCK_TRUST_MANAGER }, new SecureRandom());

		PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
				.setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create().setSslContext(sslContext).build())
				.build();

		HttpPatch oauthPost = new HttpPatch(url + "/" + relativePath);
		StringEntity params = new StringEntity(body, ContentType.APPLICATION_JSON);
		oauthPost.addHeader("Content-Type", contentType.toString());
		oauthPost.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + " " + accessToken);
		oauthPost.setEntity(params);
		Integer statusCode;
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
		HttpResponse response = httpClient.execute(oauthPost);

		statusCode = response.getCode();

		return statusCode;
	}

	@SuppressWarnings("deprecation")
	public String sendPost(String baseURL, List<NameValuePair> parametersBody, String relativePath,
			MediaType contentType) throws Exception {
		if (CRM_DISABLE_SSL_VALIDATION) {
			disableSSLCertificateValidation();
		}

		SSLContext sslContext = SSLContext.getInstance("TLSv1.2"); // OR TLSv1.2
		sslContext.init(null, new TrustManager[] { MOCK_TRUST_MANAGER }, new SecureRandom());

		PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
				.setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create().setSslContext(sslContext).build())
				.build();

		HttpPost oauthPost = new HttpPost(baseURL+"/"+relativePath);
		oauthPost.setEntity(new UrlEncodedFormEntity(parametersBody));
		log.info("POST URL is :"+baseURL+"/"+relativePath);
		String result = "";
		try (CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
				.build();
				CloseableHttpResponse response = httpClient.execute(oauthPost)) {

			result = EntityUtils.toString(response.getEntity());
		}

		return result;
	}

	public SSLContext disableSSLCertificateValidation() {

		try {

			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}
			}

			};

			SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
			sslContext.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
			HostnameVerifier allHostsValid = (hostname, session) -> true;
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			return sslContext;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	public String getOctetStream(String url, String accessToken) throws Exception{
		
		
		if (CRM_DISABLE_SSL_VALIDATION) {
			disableSSLCertificateValidation();
		}
		
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setBearerAuth(accessToken);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<byte[]> response = restTemplate.exchange(
	            url,
	            HttpMethod.GET,
	            entity,
	            byte[].class
	        );
        byte[] data = response.getBody();
        String encodedString = Base64.getEncoder().encodeToString(data);
        return encodedString;
    }


}
