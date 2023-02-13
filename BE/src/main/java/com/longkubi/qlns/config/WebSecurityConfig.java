package com.longkubi.qlns.config;

import com.longkubi.qlns.security.jwt.CustomAccessDeniedHandler;
import com.longkubi.qlns.security.jwt.JwtEntryPoint;
import com.longkubi.qlns.security.jwt.JwtTokenFilter;
import com.longkubi.qlns.security.userpincal.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    //    @Autowired
//    private DataSource dataSource;
    @Bean
    public JwtTokenFilter jwtTokenFilter() throws Exception {
        //return new JwtTokenFilter();
        JwtTokenFilter jwtAuthenticationTokenFilter = new JwtTokenFilter();
        jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
        return jwtAuthenticationTokenFilter;
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().ignoringAntMatchers("/**");
        httpSecurity.authorizeRequests().antMatchers("/api/auth/login","/api/auth/checkToken").permitAll();
        httpSecurity.antMatcher("/**").httpBasic().authenticationEntryPoint(jwtEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers("/api/roles/**").hasAnyAuthority("ADMIN","LEADER")
                .antMatchers("/api/users/**").hasAnyAuthority("ADMIN","LEADER")
                .antMatchers("/api/recruits/**").hasAnyAuthority("RECRUITMENT","ADMIN","LEADER","HR")
                .antMatchers("api/planApprovalAdmin/**").hasAnyAuthority("ADMIN","LEADER")
                .antMatchers("api/candidate-profiles/**").hasAnyAuthority("RECRUITMENT","ADMIN","HR")
                .antMatchers("api/contracts/**").hasAnyAuthority("RECRUITMENT","ADMIN","LEADER","HR")
                .antMatchers("/api/employees/**").hasAnyAuthority("RECRUITMENT","ADMIN","LEADER","HR","ACCOUNTANCY")
                .antMatchers("/api/departments/**").hasAnyAuthority("ADMIN","RECRUITMENT","LEADER","HR")
                .antMatchers("/api/positions/**").hasAnyAuthority("ADMIN","RECRUITMENT","LEADER","HR")
                .antMatchers("/api/certificates/**").hasAnyAuthority("ADMIN","RECRUITMENT","LEADER","HR")
                .antMatchers("/api/languages/**").hasAnyAuthority("ADMIN","RECRUITMENT","LEADER","HR")
                .antMatchers("/api/commendation-and-discipline/**").hasAnyAuthority("ADMIN","LEADER")
                .antMatchers("/api/salarys/**").hasAnyAuthority("ADMIN","ACCOUNTANCY")
                .antMatchers("/api/payment-salarys/**").hasAnyAuthority("RECRUITMENT","ACCOUNTANCY","ADMIN","LEADER")
                .antMatchers("/api/time-keepings/**").hasAnyAuthority("RECRUITMENT","ADMIN","HR")
                .antMatchers("api/leaves/**").hasAnyAuthority("RECRUITMENT","ADMIN","HR")
                .antMatchers("api/files/**").hasAnyAuthority("RECRUITMENT","ADMIN","HR")
                .and().addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
//        // Cấu hình Remember Me.
//        httpSecurity.authorizeRequests().and() //
//                .rememberMe().tokenRepository(this.persistentTokenRepository()) //
//                .tokenValiditySeconds(1 * 24 * 60 * 60); // 24h
        //   httpSecurity.csrf().ignoringAntMatchers("/**");
//        httpSecurity.authorizeRequests().antMatchers("/**").permitAll();

    }
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository() {
//        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
//        db.setDataSource(dataSource);
//        return db;
//    }
}