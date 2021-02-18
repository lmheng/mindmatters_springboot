package sg.edu.iss.mindmatters.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JWTtoken {
	private static final String secretKey = "eyJhbGciOiJIUzUxMiJ9.eyJSb2xlIjoiZHNhZGFzIiwiSXNzdWVyI"
			+ "joiSXNzdWVyIiwiVXNlcm5hbWUiOiJkc25ka25vcGFsZHNkIiwiZXhwIjoxNjEzNTc3MzEyL"
			+ "CJpYXQiOjE2MTM1NzczMTJ9.jX_Bv8jPB147V0yz8ITjtYw_T6iywIZKjos9I2x-hPSWk1t9A"
			+ "yzi8hcfe1XnT9zpbyh35WB4forg6jExtCSV_w";
	
	
	public static String getJwtToken(String subject) {
		return Jwts.builder().setSubject(subject)
		.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
	}
	
	
	public static String getUserEmail(String jwtToken) {
		return Jwts.parser().setSigningKey(secretKey.getBytes())
                .parseClaimsJws(jwtToken.replace("Bearer",""))
                .getBody()
                .getSubject();
	}
}
