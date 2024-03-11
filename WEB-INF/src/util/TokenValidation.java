package util;

public class TokenValidation {

    public static boolean verifToken(String authorization){
        if (authorization == null || !authorization.startsWith("Bearer")) return false;
        String token = authorization.substring("Bearer".length()).trim();
        try {
            JwtManager.decodeJWT(token);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
