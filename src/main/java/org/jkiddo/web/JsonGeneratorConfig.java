package org.jkiddo.web;

import java.util.List;

import com.sun.jersey.api.wadl.config.WadlGeneratorConfig;
import com.sun.jersey.api.wadl.config.WadlGeneratorDescription;
import com.sun.jersey.wadl.generators.json.WadlGeneratorJSONGrammarGenerator;

public class JsonGeneratorConfig extends WadlGeneratorConfig {

	@Override
	public List<WadlGeneratorDescription> configure() {
		return generator(WadlGeneratorJSONGrammarGenerator.class)
				.descriptions();
	}
}