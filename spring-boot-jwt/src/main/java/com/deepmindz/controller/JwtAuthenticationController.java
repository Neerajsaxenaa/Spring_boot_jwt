package com.deepmindz.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deepmindz.config.JwtTokenUtil;
import com.deepmindz.dto.ApiResponse;
import com.deepmindz.model.JwtRequest;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@RequestMapping(value = "/getToken", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		if (userDetails == null) {
			ApiResponse apiResponsere = new ApiResponse();
			Map<String, String> data = new HashMap<>();
			data.put("User", "Admin");
			data.put("Status", "Authorized User");
			data.put("Token", "NA");
			apiResponsere.setStatus(200);
			apiResponsere.setMessage("Success..");
			apiResponsere.setData(data);
			return new ResponseEntity<>(apiResponsere, HttpStatus.OK);
		}
		final String token = jwtTokenUtil.generateToken(userDetails);

		ApiResponse apiResponsere = new ApiResponse();
		Map<String, String> data = new HashMap<>();
		data.put("User", "Admin");
		data.put("Status", "Authorized User");
		data.put("Token", "" + token);
		apiResponsere.setStatus(200);
		apiResponsere.setMessage("Success..");
		apiResponsere.setData(data);
		return new ResponseEntity<>(apiResponsere, HttpStatus.OK);

	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);

		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
