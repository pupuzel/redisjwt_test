package com.jockjock.token.util;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jockjock.token.model.AuthUserDetail;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Value("${spring.jwt.secret}")
    private String secret;
    
    @Value("${spring.session.timeout}")
    private long session_timeout;
    
    //jwt token 모든 정보 조회
    public Map<String,Object> getBobyFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    
    //토큰이 유효성검사
    public Boolean isValidateToken(String token) {
    	try {
	        final String userId = (String) getBobyFromToken(token).get("id");
	        return !userId.isEmpty();
    	}catch (Exception e) {
			return false;
		}
    }

    // 토큰이 만료되었는지 확인한다.
    public Boolean isTokenExpired(String token) {
    	try {
	    	long exp = (Long) getBobyFromToken(token).get("exp");
	        final Date expiration = new Date(exp);
	        
	        return expiration.before(new Date());
    	}catch (Exception e) {
			return false;
		}
    }
    
    // 유저를 위한 토큰을 발급해준다.
    public String generateToken(AuthUserDetail userDetails) {
        userDetails.setAccess_token(null);
        Map<String,Object> claim = MAPPER.convertValue(userDetails, Map.class);
        claim.put("iat", new Date(System.currentTimeMillis()));
        claim.put("exp", new Date(System.currentTimeMillis() + (1000 * session_timeout) ));
        
        return Jwts.builder()
        			.setClaims(claim)
        			.signWith(SignatureAlgorithm.HS512, secret)
        			.compact();
    }
    
    

/*
    // jwt token으로부터 userId를 획득한다.
    private String getUserIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // jwt token으로부터 만료일자를 알려준다.
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
 */

}