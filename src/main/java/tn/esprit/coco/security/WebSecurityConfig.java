package tn.esprit.coco.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import tn.esprit.coco.security.jwt.AuthEntryPointJwt;
import tn.esprit.coco.security.jwt.AuthTokenFilter;
import tn.esprit.coco.service.UserDetailsServiceImpl;

import java.util.Arrays;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(unauthorizedHandler))
                .authorizeHttpRequests((authz) -> authz


                        .requestMatchers("/auth/signup", "/auth/login", "/test/**","/user/**","/stats/**","/addRide","/addCar","/addImage","/subscriptions/**","/Bus/**","/Trip/**","/AddProduct","/getAllRides").permitAll()

                        .requestMatchers("/auth/signup", "/auth/login", "/test/**","/user/**", "/stats/**","/profile-picture/**",
                                "/reclamations/**","/responses/**",
                                "/addRide","/addCar","/addImage","/subscriptions/**",
                                "/Bus/**","/Trip/**",

                                "/AddProduct","/**").permitAll()
                        .requestMatchers("/user/change-password").authenticated()
                        .requestMatchers("/admin/users/**","/admin/search","reclamations/**","/responses/**").hasAuthority("ADMIN")

                        .requestMatchers( "/**").permitAll()

                        .requestMatchers("/user/change-password").authenticated()

                                "/AddProduct","/Product","/**").permitAll()
                        .requestMatchers("/user/change-password").authenticated()
                        .requestMatchers("/admin/users/**","/admin/search","reclamations/all/**").hasAuthority("ADMIN")



                        .anyRequest().authenticated()
                )
                // Register the AuthTokenFilter
                .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Angular's default port
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type","Cache-Control"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}