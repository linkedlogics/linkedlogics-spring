package dev.linkedlogics.spring;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import dev.linkedlogics.LinkedLogics;
import dev.linkedlogics.annotation.Logic;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpringLogicConfigurer implements BeanPostProcessor {

	private final SpringServiceStarter starter;
	private final Set<String> beansContainingLogic = new HashSet<>();
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Arrays.stream(bean.getClass().getMethods()).filter(m -> m.getAnnotation(Logic.class) != null).findAny().ifPresent(m -> beansContainingLogic.add(beanName));
		
//		System.out.println(bean.getClass().getName() + " checking bean " + beanName + " -> " + beansContainingLogic);
		
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (beansContainingLogic.contains(beanName)) {
			LinkedLogics.registerLogic(bean);
		}
		
		return bean;
	}
}
