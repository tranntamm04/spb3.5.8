package com.example.config;

import com.example.service.OAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    private final OAuth2UserService oAuth2UserService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;

        OAuth2User user = authToken.getPrincipal();

        String provider = authToken.getAuthorizedClientRegistrationId().toUpperCase();

        String email = user.getAttribute("email");
        String name  = user.getAttribute("name");

        if (email == null) {
            email = user.getAttribute("id") + "@facebook.com";
        }

        oAuth2UserService.processOAuth2User(email, name, provider);

        String jwt = jwtTokenUtil.generateJwtToken(email);

        response.sendRedirect("http://localhost:4200/oauth2-success" + "?token=" + jwt
                        + "&provider=" + provider.toLowerCase()
        );
    }
}
