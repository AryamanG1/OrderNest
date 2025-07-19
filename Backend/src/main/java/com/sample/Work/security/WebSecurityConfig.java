package com.sample.Work.security;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sample.Work.model.AppRole;
import com.sample.Work.model.Role;
import com.sample.Work.model.UserEntity;
import com.sample.Work.repository.RoleRepository;
import com.sample.Work.repository.UserRepository;
import com.sample.Work.security.jwt.AuthEntryPointJwt;
import com.sample.Work.security.jwt.AuthTokenFilter;
import com.sample.Work.security.service.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private AuthEntryPointJwt unauthorizedhandler;
	
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter(){
		return new AuthTokenFilter();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return authenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.cors()
		.and()
		.csrf(csrf->csrf.disable())
			.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedhandler));
		
		http.sessionManagement(session -> 
		session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		
		http.authorizeHttpRequests(auth -> auth.requestMatchers(
				"/api/auth/signin", 
				"/api/auth/signup","/error",
				"/swagger-ui/**",
				"/redoc/**",
			    "/swagger-ui.html",
			    "/v3/api-docs/**",
			    "/v3/api-docs",
			    "/swagger-resources/**",
			    "/configuration/**",
			    "/webjars/**",
			    "/docs/**").permitAll()
														 .anyRequest().authenticated());
		
		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer(){
		return (web -> web.ignoring().requestMatchers("/configuration/security","swagger-ui.html"));
	}
	
	@Bean
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Retrieve or create roles
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseGet(() -> {
                        Role newUserRole = new Role(AppRole.ROLE_USER);
                        return roleRepository.save(newUserRole);
                    });

            Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                    .orElseGet(() -> {
                        Role newSellerRole = new Role(AppRole.ROLE_SELLER);
                        return roleRepository.save(newSellerRole);
                    });

            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(() -> {
                        Role newAdminRole = new Role(AppRole.ROLE_ADMIN);
                        return roleRepository.save(newAdminRole);
                    });

            Set<Role> userRoles = Set.of(userRole);
            Set<Role> sellerRoles = Set.of(sellerRole);
            Set<Role> adminRoles = Set.of(userRole, sellerRole, adminRole);


            // Create users if not already present
            if (!userRepository.existsByUserName("user1")) {
            	UserEntity user1 = new UserEntity(
                        "user1",
                        "User One",
                        "user1@example.com",
                        passwordEncoder.encode("Password@123"),
                        25
                    );                
            	userRepository.save(user1);
            }

            if (!userRepository.existsByUserName("seller1")) {
            	 UserEntity seller1 = new UserEntity(
                         "seller1",
                         "Seller One",
                         "seller1@example.com",
                         passwordEncoder.encode("Seller@123"),
                         30
                     );                
            	 userRepository.save(seller1);
            }

            if (!userRepository.existsByUserName("admin")) {
            	 UserEntity admin = new UserEntity(
                         "admin",
                         "Admin User",
                         "admin@example.com",
                         passwordEncoder.encode("Admin@123"),
                         35
                     );                
            	 userRepository.save(admin);
            }

            // Update roles for existing users
            userRepository.findByUserName("user1").ifPresent(user -> {
                user.setRoles(userRoles);
                userRepository.save(user);
            });

            userRepository.findByUserName("seller1").ifPresent(seller -> {
                seller.setRoles(sellerRoles);
                userRepository.save(seller);
            });

            userRepository.findByUserName("admin").ifPresent(admin -> {
                admin.setRoles(adminRoles);
                userRepository.save(admin);
            });
        };

	}
	
	@Bean
	public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
	    org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
	    configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Angular app origin
	    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    configuration.setAllowedHeaders(List.of("*"));
	    configuration.setAllowCredentials(true); // needed if you're sending cookies or Authorization header

	    org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}

	}


