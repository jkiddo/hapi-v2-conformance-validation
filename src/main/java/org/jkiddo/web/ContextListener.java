package org.jkiddo.web;

import java.io.File;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.jkiddo.hapi.v2x.ihe.IHEProfileFileStore;
import org.jkiddo.hapi.v2x.ihe.TypedProfileStore;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.api.container.filter.GZIPContentEncodingFilter;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class ContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {

		return Guice.createInjector(new AbstractModule() {

			@Override
			protected void configure() {

				try {
					bind(TypedProfileStore.class).toInstance(
							new IHEProfileFileStore(new File(".")));
				} catch (JAXBException e) {
					throw new RuntimeException("Unable to create store");
				}
				bind(ValidationServlet.class).asEagerSingleton();
			}

		}, new JerseyServletModule() {

			@Override
			protected void configureServlets() {
				final Map<String, String> params = ImmutableMap
						.<String, String> builder()
						.put(JSONConfiguration.FEATURE_POJO_MAPPING, "true")
						.put(ResourceConfig.PROPERTY_WADL_GENERATOR_CONFIG,
								JsonGeneratorConfig.class.getCanonicalName())
						.put(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS,
								Joiner.on(";").join(
										CharsetResponseFilter.class.getName(),
										GZIPContentEncodingFilter.class
												.getName())).build();
				final JacksonJsonProvider instance = new JacksonJsonProvider();
				instance.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, false);
				bind(JacksonJsonProvider.class).toInstance(instance);
				serve("/*").with(GuiceContainer.class, params);
			}
		});
	}
}