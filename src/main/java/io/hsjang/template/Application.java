package io.hsjang.template;


import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.WebExceptionHandler;

import io.hsjang.template.common.exception.HException;
import reactor.core.publisher.Flux;


@SpringBootApplication
@EnableWebFlux
public class Application implements WebFluxConfigurer{

	@Value("classpath:/static/index.html") Resource index;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public RouterFunction<ServerResponse> resources(@Value("classpath:/static/index.html") Resource index) {
		return RouterFunctions.route(RequestPredicates.GET("/"), req -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).syncBody(index));
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("/", "classpath:/static/").setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }

	@Bean
	@Order(-1)
	public WebExceptionHandler exHandler(){
		
		return (exchange, ex) -> {

			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			String message = status.getReasonPhrase();
			if(ex instanceof HException){
				status = ((HException) ex).getStatus();
				message = ((HException) ex).getReason();
			}else if(ex instanceof ResponseStatusException){
				status = ((ResponseStatusException) ex).getStatus();
				String reason = ((ResponseStatusException) ex).getReason();
				message = reason==null ? status.getReasonPhrase() : reason;
			}
			ServerHttpResponse res = exchange.getResponse();
			res.setStatusCode(status);
			res.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
			return res.writeWith( Flux.just(res.bufferFactory().wrap(String.format("{\"msg\":\"%s\"}",message).getBytes())) );
		};
	}
}