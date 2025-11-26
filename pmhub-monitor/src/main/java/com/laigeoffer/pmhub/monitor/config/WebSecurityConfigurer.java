package com.laigeoffer.pmhub.monitor.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * 监控权限配置
 * 
 * @author canghe
 */
@EnableWebSecurity
public class WebSecurityConfigurer
{
    private final String adminContextPath;

    public WebSecurityConfigurer(AdminServerProperties adminServerProperties)
    {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
    {
        //创建认证成功处理器 用于登陆成功后重定向
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminContextPath + "/");

        //配置HTTP头部
        return httpSecurity
                .headers().frameOptions().disable()
                .and().authorizeRequests()
                //设置不需要认证的公共路径
                .antMatchers(adminContextPath + "/assets/**"
                        , adminContextPath + "/login"
                        , adminContextPath + "/actuator/**"
                        , adminContextPath + "/instances/**"
                )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().loginPage(adminContextPath + "/login")
                .successHandler(successHandler).and()
                .logout()
                .logoutUrl(adminContextPath + "/logout")
                .and()
                .httpBasic()
                .and()
                .csrf()
                .disable()
                .build();
    }
}
