package br.com.zupacademy.metricas.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests()
    		.antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
    		.antMatchers(HttpMethod.GET, "/proposta/**").hasAuthority("SCOPE_propostas:read")
    		.antMatchers(HttpMethod.GET, "/cartao/**").hasAuthority("SCOPE_cartoes:read")
    		.antMatchers(HttpMethod.POST, "/cartao/**").hasAuthority("SCOPE_cartoes:write")
    		.antMatchers(HttpMethod.PUT, "/cartao/**").hasAuthority("SCOPE_cartoes:write")
    		.antMatchers(HttpMethod.POST, "/proposta/**").hasAuthority("SCOPE_propostas:write")
    		.anyRequest().authenticated()
    	.and().oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
    	.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}