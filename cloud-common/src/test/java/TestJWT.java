import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ligong.common.utils.JWTUtils;

import java.util.Map;

/**
 * @description:
 * @since: 2023/10/9 19:58
 * @author: sdw
 */
public class TestJWT {
    public static void main(String[] args) {
        DecodedJWT verify = JWTUtils.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IjEyMyIsImV4cCI6MTY5NzQ1Nzg0NCwidXNlcklkIjoiMTAwMSIsInVzZXJuYW1lIjoiemhhbmdzYW4ifQ.vxIJyAtE3O95a-sYepowHsYO-uEpvEs8ZDTzEoJwo8o");

        String username = verify.getClaim("username").asString();
        System.out.println(username);

        String password = verify.getClaim("password").asString();
        System.out.println(password);

        String userId = verify.getClaim("userId").asString();
        System.out.println(userId);

    }
}
