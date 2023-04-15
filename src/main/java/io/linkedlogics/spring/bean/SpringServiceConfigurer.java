package io.linkedlogics.spring.bean;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;

import io.linkedlogics.service.ServiceConfigurer;
import io.linkedlogics.service.ServiceProvider;
import io.linkedlogics.service.local.LocalServiceConfigurer;
import io.linkedlogics.spring.EnableLinkedLogics;

@Configuration
public class SpringServiceConfigurer implements ImportAware {

	protected EnableLinkedLogics annotation;

	@Override
	public void setImportMetadata(AnnotationMetadata importMetadata) {
		annotation = (EnableLinkedLogics) ((Class) importMetadata.getAnnotations().get(EnableLinkedLogics.class).getSource()).getAnnotationsByType(EnableLinkedLogics.class)[0];
	}
	
	@Bean
	public SpringServiceStarter springServiceStarter(List<ServiceConfigurer> list) {
		return new SpringServiceStarter(list);
	}
	
	@Bean
	public SpringLogicConfigurer springLogicConfigurer(List<ServiceConfigurer> list) {
		return new SpringLogicConfigurer(springServiceStarter(list));
	}
	
	@Bean
	public SpringProcessConfigurer springProcessConfigurer(List<ServiceConfigurer> list) {
		return new SpringProcessConfigurer(springServiceStarter(list));
	}
	
	@Bean
	@Order(0)
	public ServiceConfigurer localServiceConfigurer() {
		return new LocalServiceConfigurer();
	}

	@Bean
	@Order(1)
	public ServiceConfigurer annotatedServiceConfigurer() {
		ServiceConfigurer serviceConfigurer = new ServiceConfigurer();
		getProvider(annotation.processing()).getProcessingServices().forEach(serviceConfigurer::configure);
		getProvider(annotation.evaluating()).getEvaluatingServices().forEach(serviceConfigurer::configure);
		getProvider(annotation.storing()).getStoringServices().forEach(serviceConfigurer::configure);
		getProvider(annotation.scheduling()).getSchedulingServices().forEach(serviceConfigurer::configure);
		getProvider(annotation.messaging()).getMessagingServices().forEach(serviceConfigurer::configure);
		getProvider(annotation.monitoring()).getMonitoringServices().forEach(serviceConfigurer::configure);
		getProvider(annotation.tracking()).getTrackingServices().forEach(serviceConfigurer::configure);
		return serviceConfigurer;
	}
	
	private ServiceProvider getProvider(Class<? extends ServiceProvider> providerClass) {
		try {
			return (ServiceProvider) providerClass.getConstructor().newInstance() ;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
