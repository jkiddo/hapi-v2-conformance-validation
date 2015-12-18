package org.jkiddo.web;

import java.util.Map;

import javax.inject.Singleton;
import javax.servlet.ServletContextEvent;
import javax.xml.bind.JAXBException;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.ebaysf.web.cors.CORSFilter;
import org.jkiddo.hapi.v2x.ihe.IHEHapiContext;
import org.jkiddo.hapi.v2x.ihe.IHEProfileFileStore;
import org.jkiddo.hapi.v2x.ihe.TypedProfileStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

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

	private final Logger logger = LoggerFactory.getLogger(ContextListener.class);
	private final IHEProfileFileStore profileStore = new IHEProfileFileStore();
	private final HAPIServer hapiServer;
	private final IHEHapiContext hapiContext = new IHEHapiContext(profileStore);

	public ContextListener() throws JAXBException {
		super();
		hapiServer = new HAPIServer(hapiContext);
	}

	@Override
	protected Injector getInjector() {

		logger.info("Starting up servlet ...");
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
		return Guice.createInjector(new AbstractModule() {

			@Override
			protected void configure() {

				bind(TypedProfileStore.class).toInstance(profileStore);
				bind(HAPIServer.class).toInstance(hapiServer);
				bind(IHEHapiContext.class).toInstance(hapiContext);

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
				bind(CORSFilter.class).in(Singleton.class);
				serve("/*").with(GuiceContainer.class, params);
				filter("/*").through(CORSFilter.class);
			}
		});
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		hapiServer.close();
		super.contextDestroyed(servletContextEvent);
	}
}