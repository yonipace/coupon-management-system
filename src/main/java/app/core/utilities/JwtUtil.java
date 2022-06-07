package app.core.utilities;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import app.core.login.LoginManagerInterface.ClientType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private String alg = SignatureAlgorithm.HS256.getJcaName();
	@Value("${jwt.util.secret.key}")
	private String secret;
	private Key key;

	@Value("${jwt.util.chrono.unit.number}")
	private int unitsNumber;
	@Value("${jwt.util.chrono.unit}")
	private String chronoUnit;

	@PostConstruct
	public void initKey() {
		byte[] secretDecoded = Base64.getDecoder().decode(secret.getBytes());
		this.key = new SecretKeySpec(secretDecoded, alg);
	}

	public String generateToken(int id, String email, ClientType clientType) {

		Map<String, Object> claims = new HashMap<>();
		claims.put("clientId", id);
		claims.put("clientType", clientType);
		return createToken(claims, email);

	}

	private String createToken(Map<String, Object> claims, String subject) {

		Instant now = Instant.now();
		Instant expire = now.plus(this.unitsNumber, ChronoUnit.valueOf(chronoUnit));

		String token = Jwts.builder()

				.setClaims(claims)

				.setSubject(subject)

				.setIssuedAt(Date.from(now))

				.setExpiration(Date.from(expire))

				.signWith(key)

				.compact();

		return token;
	}

	public String extractEmail(String token) {

		Claims claims = extractAllClaims(token);
		return claims.getSubject();
	}

	public int extractId(String token) {

		Claims claims = extractAllClaims(token);
		return (int) claims.get("clientId");
	}

	public ClientType extractClientType(String token) {

		Claims claims = extractAllClaims(token);
		return ClientType.valueOf((String) claims.get("clientType"));
	}

	private Claims extractAllClaims(String token) {
		JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
		Jws<Claims> jws = jwtParser.parseClaimsJws(token);
		return jws.getBody();
	}

	// more method for more info
	public Date getTokenExp(String token) {
		return extractAllClaims(token).getExpiration();
	}

	public Date getTokenIssuedAt(String token) {
		return extractAllClaims(token).getIssuedAt();
	}

}
