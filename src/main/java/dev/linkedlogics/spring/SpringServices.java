package dev.linkedlogics.spring;

import java.util.List;

import dev.linkedlogics.service.LinkedLogicsService;
import dev.linkedlogics.service.ServiceProvider;
import dev.linkedlogics.spring.service.SpringEvaluatorService;

public class SpringServices extends ServiceProvider {

	@Override
	public List<LinkedLogicsService> getEvaluatingServices() {
		return List.of(new SpringEvaluatorService());
	}
}
