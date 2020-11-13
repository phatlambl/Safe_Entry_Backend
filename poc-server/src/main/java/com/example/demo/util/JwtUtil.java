package com.example.demo.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	//thoi gian hieu luc cua token
	public static final long JWT_TOKEN_VALIDITY = 8*60*60;
	
	//khóa bí mật, token sẽ được mã hóa với khóa này
	private static final String secret ="phat";
	
	
	//sử dụng khóa để giải mã token
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	//	Lấy user từ token
	public String getUsernameFromToken(String token) {
		final Claims claims = getAllClaimsFromToken(token);
		return claims.getSubject();
	}
	
	//lay ngay het han tu token
	public Date getExpirationDateFromToken(String token) {
		final Claims claims = getAllClaimsFromToken(token);
		return claims.getExpiration();
	}
	
	
	//kiểm tra xem token có nằm trong thời gian hiệu lực, nếu time trước thời gian hiện tại return false, nếu time đã quá hạn return true
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		System.out.println("isTokenExpired: " + expiration.before(new Date()));
		return expiration.before(new Date());
	}
	
	//tạo một token chứa thông tin: username, ngày khởi tạo, ngày hết hiệu lực
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
							.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
							.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	
	//tạo một token để trả về cho người dùng
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		
		return doGenerateToken(claims, userDetails.getUsername());
	}
	
	//kiểm tra token có hiệu lực hay không
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	
	
}

