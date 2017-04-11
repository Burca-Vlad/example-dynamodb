package com.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.example.models.User;

@Configuration
public class RepositoryConfiguraton extends RepositoryRestConfigurerAdapter {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		super.configureRepositoryRestConfiguration(config);
        config.exposeIdsFor(User.class);
        config.setReturnBodyOnCreate(true);
	}	
   
}
