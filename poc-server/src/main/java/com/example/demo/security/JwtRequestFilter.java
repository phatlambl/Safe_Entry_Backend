package com.example.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.user.MyUserDetailsService;
import com.example.demo.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		
		//JWT token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		
		if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7); //7 index from "Bearer "
			try {
				username = jwtUtil.getUsernameFromToken(jwtToken);			
				
			}catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT token");
			}catch(ExpiredJwtException e) {
				System.out.println("JWT token has exprired");
			}
		}else {
			logger.warn("JWT token does not begin with Bearer String");
		}
		
		//Once we get the token validate it
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		
			UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);
			// if token is valid configure Spring Security to manually set
            // authentication
			if(jwtUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				
				usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//After setting the Authentication in the context, we specify
				//that the current user is authenticated, so it passes the 
				//spring security configurations successfully
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);		
			}			
			
		}
		filterChain.doFilter(request, response);
		
	}
}
	