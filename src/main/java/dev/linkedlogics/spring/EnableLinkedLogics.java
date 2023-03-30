package dev.linkedlogics.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import dev.linkedlogics.service.ServiceProvider;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SpringServiceConfigurer.class)
public @interface EnableLinkedLogics {
	Class<? extends ServiceProvider> storing() default ServiceProvider.class;
	Class<? extends ServiceProvider> messaging() default ServiceProvider.class;
	Class<? extends ServiceProvider> processing() default ServiceProvider.class;
	Class<? extends ServiceProvider> scheduling() default ServiceProvider.class;
	Class<? extends ServiceProvider> evaluating() default ServiceProvider.class;
	Class<? extends ServiceProvider> monitoring() default ServiceProvider.class;
}
