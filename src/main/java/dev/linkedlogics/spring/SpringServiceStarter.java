package dev.linkedlogics.spring;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import dev.linkedlogics.LinkedLogics;
import dev.linkedlogics.service.ServiceConfigurer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpringServiceStarter implements ApplicationRunner {

	@Autowired
	private final List<ServiceConfigurer> configurers;
	
	@PostConstruct
	public void configure() {
		configurers.forEach(LinkedLogics::configure);
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		LinkedLogics.launch();
	}
}
