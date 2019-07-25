package com.grapheople.miner.common.retrofit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.util.HashMap;
import java.util.Map;


@Component
public class RetrofitFactory<S> {

	public S createService(String domain, Class<S> service) {
		return this.createService(domain, service, this.getDefaultHeaders());
	}

    public S createService(String domain, Class<S> service, Converter.Factory converter) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(this.createLoggingInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(domain)
                .addConverterFactory(converter)
                .client(client)
                .build();

        return retrofit.create(service);
    }

    public S createService(String domain, Class<S> service, Map<String, String> headers, Converter.Factory converter) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(this.createInterceptor(headers))
                .addInterceptor(this.createLoggingInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(domain)
                .addConverterFactory(converter)
                .client(client)
                .build();

        return retrofit.create(service);
    }

	public S createService(String domain, Class<S> service, Map<String, String> headers) {
		OkHttpClient client = new OkHttpClient.Builder()
				.addInterceptor(this.createInterceptor(headers))
				.addInterceptor(this.createLoggingInterceptor())
				.build();

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(domain)
				.addConverterFactory(GsonConverterFactory.create())
				.client(client)
				.build();

		return retrofit.create(service);
	}

	public S createUnsafeService(String domain, Class<S> service, Map<String, String> headers) {
		Interceptor interceptor = createInterceptor(headers);

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(domain)
				.addConverterFactory(GsonConverterFactory.create())
				.client(this.getUnsafeOkHttpClient(interceptor).build())
				.build();

		return retrofit.create(service);
	}

	public S createUnsafeService(String domain, Class<S> service) {
		return this.createUnsafeService(domain, service, this.getDefaultHeaders());
	}

	private OkHttpClient.Builder getUnsafeOkHttpClient(Interceptor interceptor) {
		try {
			final TrustManager[] trustAllCerts = new TrustManager[]{
					new X509TrustManager() {
						@Override
						public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
						}

						@Override
						public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
						}

						@Override
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return new java.security.cert.X509Certificate[]{};
						}
					}
			};

			final SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

			final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			return new OkHttpClient.Builder()
					.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
					.hostnameVerifier((hostname, session) -> true)
					.addInterceptor(interceptor)
					.addInterceptor(this.createLoggingInterceptor());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Interceptor createInterceptor(Map<String, String> headers) {
		return chain -> {
			Request.Builder requestBuilder = chain.request().newBuilder();
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				requestBuilder.addHeader(entry.getKey(), entry.getValue());
			}
			return chain.proceed(requestBuilder.build());

		};
	}

	private Interceptor createLoggingInterceptor() {
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		logging.setLevel(HttpLoggingInterceptor.Level.BODY);
		return logging;
	}

	private Map<String, String> getDefaultHeaders() {
		Map<String, String> defaultHeader = new HashMap<>();
		defaultHeader.put("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
		defaultHeader.put("Cache-Control", "no-cache");
		return defaultHeader;
	}
}
