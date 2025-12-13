package com.example.community_education.Tool.Token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.community_education.Tool.ConstCorrespond;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TokenUtil {
    private static final long EXPIRE_TIME = 20*60*60*1000; //token到期时间10小时
    private static final String TOKEN_SECRET = "ljdyaishijin**3nkjnj??";            //设置密钥
    private static Date expireAtTime;

    /**
     * 生成token
     * */
    public static String sign(String role, String number){
        String token = null;
        String identity = getIdentity(role);
        try {
            Date expireAt = new Date(System.currentTimeMillis() + EXPIRE_TIME); //创建过期时间
            expireAtTime = expireAt;
            token = JWT.create()
                    .withClaim("role",identity)        //存放用户角色
                    .withIssuer("auth0")    //发行人
                    .withClaim("username",number)    //存放账号
                    .withExpiresAt(expireAt)                          //过期时间
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        }catch (IllegalArgumentException| JWTCreationException je){

        }
        return token;
    }
    /**
     * token验证
     */
    public static Boolean verify(String token){

        try {
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();//创建token验证器
            DecodedJWT decodedJWT=jwtVerifier.verify(token);

        } catch (IllegalArgumentException | JWTVerificationException e) {
            //抛出错误即为验证不通过
            return false;
        }
        return true;
    }
    /**
     * 验证token是否过期
     * **/
    public static Boolean isTokenExpired(String token){
        try {
            return expireAtTime.before(new Date());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取账号
     * **/
    public static String getUsername(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();//创建token验证器

        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getClaim("username").asString();

    }



    /**
     * 获取角色
     *
     * @return**/
    public static Set<GrantedAuthority> getRole(String token){
        JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();//创建token验证器
        DecodedJWT decodedJWT=jwtVerifier.verify(token);
        String[]  a = {"ROLE_USER","ROLE_ADMIN"};
        Set<GrantedAuthority> grantedAuths = new HashSet<>();

        String[] identityTypeArray = decodedJWT.getClaim("role").asString().split("\\|");

        for(String identityType: identityTypeArray){
            grantedAuths.add( new SimpleGrantedAuthority(identityType));
        }
//        return decodedJWT.getClaim("role").asString();
        return grantedAuths;
    }

    public static String getIdentity(String identityId){
        String[] identityTypeArray = identityId.split("\\|");
        String identity = ConstCorrespond
                .USER_AUTHORIZATION[Integer.parseInt(identityTypeArray[0])];
        int num = identityTypeArray.length;
        for (int i = 1;i<num;i++) {
            identity  = identity + "|" + ConstCorrespond
                    .USER_AUTHORIZATION[Integer.parseInt(identityTypeArray[i])];
        }
        return identity;
    }
}
