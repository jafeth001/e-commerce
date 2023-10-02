package com.Ecommerce.Ecommerceapp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY
            = "68576D5A7134743777217A25432A462D4A614E635266556A586E327235753878";

    public String extractusername(String token) {
        return extractclaims(token, Claims::getSubject);
    }

    public <T> T extractclaims(String token, Function<Claims, T> function) {
        final Claims claims = extractallclaims(token);
        return function.apply(claims);
    }


    private Claims extractallclaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generatetokens(
            Map<String, Object> extractclaims, UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extractclaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generatesingletoken(UserDetails userDetails){
        return generatetokens(new HashMap<>(),userDetails);
    }
    public boolean tokenvalid(UserDetails userDetails, String token){
        String username=extractusername(token);
        return(username.equals(userDetails.getUsername()))&&!tokenExpired(token);
    }

    private boolean tokenExpired(String token) {
        return extractexpiration(token).before(new Date());
    }

    private Date extractexpiration(String token) {
        return extractclaims(token,Claims::getExpiration);
    }

    private Key getSigningKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }
}
