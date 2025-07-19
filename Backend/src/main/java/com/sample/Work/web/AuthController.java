package com.sample.Work.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.Work.model.AppRole;
import com.sample.Work.model.Role;
import com.sample.Work.model.TokenEntity;
import com.sample.Work.model.UserEntity;
import com.sample.Work.repository.RoleRepository;
import com.sample.Work.repository.TokenRepository;
import com.sample.Work.repository.UserRepository;
import com.sample.Work.security.jwt.AuthTokenFilter;
import com.sample.Work.security.jwt.JwtUtils;
import com.sample.Work.security.request.LoginRequest;
import com.sample.Work.security.request.SignupRequest;
import com.sample.Work.security.response.LoginResponse;
import com.sample.Work.security.response.MessageResponse;
import com.sample.Work.security.service.UserDetailsImpl;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import com.sample.Work.utils.ExceptionUtils;



@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth Controller", description = "Handles User Signin and Signup functionality")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
	
	@PostMapping("/signin")
	public ResponseEntity<?> signInFunction(@RequestBody LoginRequest loginRequest){
		logger.debug("Entering Sign In Controller");
		// Step 1 - Initialise an authentication object which will contain the credentials 
		Authentication authentication;
		
		// Step 2 - Initialise a try catch block to authenticate the user based on their credentials
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getUsername(),loginRequest.getPassword())
			);
			
			logger.debug("Successfully logged in !!");
			// Now if some issue is encountered we will call the catch block
		}
		catch(AuthenticationException e){
			Map<String,Object> body = new HashMap<>();
			body.put("message", "Bad Credentials");
			body.put("status", false);
			logger.error("Encountered Authentication Exception ");
			return new ResponseEntity<>(body,HttpStatus.UNAUTHORIZED);
		}
		
		// Now that we have successfully come out of this block we will set the context
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		// Create the JWT token
		UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
		logger.error("User found {} : " ,userDetails.getUsername() );
		String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
		
		UserEntity user = userRepository.findByUserName(userDetails.getUsername()).orElseThrow(() -> ExceptionUtils.userNotFoundError(userDetails.getUsername()));
		
		// Remove all the earlier tokens flag them as 
		List<TokenEntity> tokens  = tokenRepository.findAllTokenByUser(user.getUserId());
		
		if(!tokens.isEmpty()) {
			tokens.forEach(token -> {
				token.setLoggedOut(true);
			});
		}
		
		tokenRepository.saveAll(tokens);
		
		// Adding the token to the token repository
		TokenEntity token = new TokenEntity(jwtToken,false,user);
		tokenRepository.save(token);
		
		
		// Getting the roles
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
								.collect(Collectors.toList());
		LoginResponse response = new LoginResponse(userDetails.getId(),jwtToken,userDetails.getUsername(),roles);
		
		logger.debug("Exiting Sign In Controller");
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUpFunction(@RequestBody @Valid SignupRequest signupRequest){
		logger.debug("Entering Signup Controller");
		// Step 1 --> validate the username
		logger.debug("Username:" + signupRequest.getUsername());
		if(userRepository.existsByUserName(signupRequest.getUsername())){
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username Already Exists"));
		}
		// Step 2 --> validate the email
		if(userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email Already Exists"));
		}
		//Step 3 --> Create the user
		UserEntity user = new UserEntity(signupRequest.getUsername(),signupRequest.getName(),
										 signupRequest.getEmail(),
										 encoder.encode(signupRequest.getPassword()),
										 signupRequest.getAge());
		
		// Step 4 --> get roles from the request
		Set<String> strRoles = signupRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		// Step 5 --> Validation of User entered role
		
		if (strRoles == null) {
		    Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
		            .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
		    roles.add(userRole);
		} else {
		    strRoles.forEach(role -> {
		        switch (role) {
		            case "admin": {
		                Role adminRole = roleRepository
		                        .findByRoleName(AppRole.ROLE_ADMIN)
		                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
		                roles.add(adminRole);
		                break;
		            }
		            case "seller": {
		                Role sellerRole = roleRepository
		                        .findByRoleName(AppRole.ROLE_SELLER)
		                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
		                roles.add(sellerRole);
		                break;
		            }
		            default: {
		                Role userRole = roleRepository
		                        .findByRoleName(AppRole.ROLE_USER)
		                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
		                roles.add(userRole);
		            }
		        }
		    });  
		}
		    
		    user.setRoles(roles);
		    userRepository.save(user);
			logger.debug("Exiting Signup Controller");
		    return ResponseEntity.ok(new MessageResponse("User Successfully stored"));
		
	
}
	
	
	@PostMapping("/signout")
	public ResponseEntity<?> signOutFunction(HttpServletRequest request){
			logger.debug("Entering Signout controller");
			// Step 1 --> Get the authorization header
			String jwt = jwtUtils.getjwtFromHeader(request);
			logger.debug("JWT found : {}",jwt);
			
			// Step 2 --> Mark the jwt in token table as logged out
			
			TokenEntity token = tokenRepository.findByToken(jwt).orElseThrow(() -> ExceptionUtils.tokenNotFoundError(jwt));
			token.setLoggedOut(true);
			tokenRepository.save(token);
			logger.debug("Set Value of token {} ",token);
			
			// Step 3 --> Clear the security context
			SecurityContextHolder.clearContext();
			
			
			logger.debug("Exiting Signout controller");
			return ResponseEntity.ok(new MessageResponse("Successfully logged out"));
		
		
	}
	



}
