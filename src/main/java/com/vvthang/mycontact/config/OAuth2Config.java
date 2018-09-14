package com.vvthang.mycontact.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import com.vvthang.mycontact.common.Constant.Role;


@Configuration
public class OAuth2Config {
    
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(OAuth2Config.class);
    
    // =================================== AuthorizationServerConfigurerAdapter ====================================
    // =============================================================================================================
    @Configuration
    @EnableAuthorizationServer
    public static class AuthServerConfig extends AuthorizationServerConfigurerAdapter{
        
        @Autowired
        private AuthenticationManager authenticationManager;
        
        @Autowired
        private UserDetailsService userDetailsService;
        
        @Bean
        public TokenStore tokenStore(){
            return new InMemoryTokenStore();
        }
        
        @Autowired
        private TokenStore tokenStore;
        
        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
             // oauthServer.tokenKeyAccess("isAnonymous()").checkTokenAccess("isAuthenticated()");
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                .withClient("mycontact")
                .authorizedGrantTypes("password", "client_credentials", "implicit")
                .secret("secret")
                .resourceIds("mycontact_id")
                .scopes("read")
                .accessTokenValiditySeconds(180);//Access token is only valid for 2 minutes.
                //.refreshTokenValiditySeconds(60);//Refresh token is only valid for 10 minutes.
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.tokenStore(tokenStore).authenticationManager(authenticationManager).userDetailsService(userDetailsService);
        }
        
    }
    
    // =================================== ResourceServerConfigurerAdapter =========================================
    // =============================================================================================================
    @Configuration
    @EnableResourceServer
    public static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId("mycontact_id").stateless(false);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
            .authorizeRequests()
            .antMatchers("/user/**").hasRole(Role.MEMBER)
            .antMatchers("/admin/**").hasRole(Role.ADMIN)
            .antMatchers("/api/**").hasRole(Role.MEMBER)
                .and()
                    .authorizeRequests()
                    .anyRequest().access("#oauth2.hasScope('read')");
        }
    }
    
}
