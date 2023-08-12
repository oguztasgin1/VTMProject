package com.vtm.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Date;
import java.util.Optional;

@ControllerAdvice
public class JwtTokenManager {
    private final String sifreAnahtari = "#luC}VB>IsC)*>&x**zMqIdD}Pct_%T3w>{9&Zl$tbXZwfF3J+p%iD~o]8-!^`;";
    private final Long exTime = 1000L*60*15;
    public Optional<String> createToken(Long userId, String name, String surname, Long companyId, String companyName, String role){
        String token="";
        try{
            /**
             *  withClaim -> içinde Key-Value şeklinde bilgiler saklanır.
             *  Bu bilgiler Paylod olarak tutulur ve herkes tarafından görülür. Bu nedenle buraya özel bilgiler  koyulmaz.
             *  withIssuer -> jwt yi oluşturan kişinin kimliğini tutmak için kullanılır.
             *  withIssuerAt -> Jwt yi oluşturma zamanı
             *  withExpiresAt -> Jwt nin geçerlilik süresi. 30sn - sonsuz
             *  sign -> hazırlanan içeriğin imzalanması yapılır. bunun için bir şifre belirlenir ve bununla kriptolama yapılır.
             */
            token = JWT.create().withAudience()
                    .withClaim("id", userId)
                    .withClaim("name",name)
                    .withClaim("surname",surname)
                    .withClaim("companyId", companyId)
                    .withClaim("companyName", companyName)
                    .withClaim("role", role)
                    .withIssuer("VTMProject")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis()+exTime))
                    .sign(Algorithm.HMAC256(sifreAnahtari));
            return Optional.of(token);
        }catch (Exception exception){
            return Optional.empty();
        }
    }

    public Optional<DecodedJWT> validateToken(String token){
        try {
            Algorithm algorithm=Algorithm.HMAC256(sifreAnahtari);
            JWTVerifier verifier=JWT.require(algorithm).withIssuer("VTMProject").build();
            DecodedJWT decodedJWT= verifier.verify(token);
            if (decodedJWT==null){
                return  Optional.empty();
            }
            return Optional.of(decodedJWT);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Long> getIdFromToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(sifreAnahtari);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("VTMProject").build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if(decodedJWT==null) return Optional.empty();
            return Optional.of(decodedJWT.getClaim("id").asLong());
        }catch (Exception exception){
            return Optional.empty();
        }
    }

    public Optional<String> getRoleFromToken(String token){
        try {
            Algorithm algorithm=Algorithm.HMAC256(sifreAnahtari);
            JWTVerifier verifier=JWT.require(algorithm).withIssuer("VTMProject").build();
            DecodedJWT decodedJWT= verifier.verify(token);
            if (decodedJWT==null){
                return  Optional.empty();
            }
            return  Optional.of(decodedJWT.getClaim("role").asString());
        }catch (Exception exception){
            exception.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Long> getCompanyIdFromToken(String token){
        try {
            Algorithm algorithm=Algorithm.HMAC256(sifreAnahtari);
            JWTVerifier verifier=JWT.require(algorithm).withIssuer("VTMProject").build();
            DecodedJWT decodedJWT= verifier.verify(token);
            if (decodedJWT==null){
                return  Optional.empty();
            }
            return  Optional.of(decodedJWT.getClaim("companyId").asLong());
        }catch (Exception exception){
            exception.printStackTrace();
            return Optional.empty();
        }
    }
}
