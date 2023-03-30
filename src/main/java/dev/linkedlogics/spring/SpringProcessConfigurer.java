package dev.linkedlogics.spring;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import dev.linkedlogics.LinkedLogics;
import dev.linkedlogics.model.ProcessDefinition;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpringProcessConfigurer implements BeanPostProcessor {

	private final SpringServiceStarter starter;
	private final Set<String> beansContainingProcess = new HashSet<>();
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Arrays.stream(bean.getClass().getMethods()).filter(m -> ProcessDefinition.class.isAssignableFrom(m.getReturnType())).findAny().ifPresent(m -> beansContainingProcess.add(beanName));
		
//		System.out.println("checking bean " + beanName + " -> " + beansContainingProcess);
		
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (beansContainingProcess.contains(beanName)) {
			LinkedLogics.registerProcess(bean);
		}
		
		return bean;
	}
}
