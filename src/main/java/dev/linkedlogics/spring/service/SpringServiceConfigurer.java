package dev.linkedlogics.spring.service;

import dev.linkedlogics.service.ServiceConfigurer;

public class SpringServiceConfigurer  extends ServiceConfigurer {
	public SpringServiceConfigurer() {
		configure(new SpringEvaluatorService());
	}
}