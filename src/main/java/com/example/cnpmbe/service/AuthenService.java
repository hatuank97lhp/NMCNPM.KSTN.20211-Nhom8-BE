package com.example.cnpmbe.service;

import com.example.cnpmbe.common.APIResponse;
import com.example.cnpmbe.common.APIResponseBuilder;
import com.example.cnpmbe.common.StringUtils;
import com.example.cnpmbe.common.enums.ExceptionMessages;
import com.example.cnpmbe.common.enums.ResultMessages;
import com.example.cnpmbe.config.EnvConfig;
import com.example.cnpmbe.jwt.CustomUserDetails;
import com.example.cnpmbe.jwt.JwtTokenProvider;
import com.example.cnpmbe.jwt.UsernameAuthenProvider;
import com.example.cnpmbe.model.entity.auth.AuthToken;
import com.example.cnpmbe.model.entity.auth.User;
import com.example.cnpmbe.model.request.auth.ChangePasswordRequest;
import com.example.cnpmbe.model.request.auth.LoginRequest;
import com.example.cnpmbe.model.response.AuthenInfoResponse;
import com.example.cnpmbe.repository.AuthTokenRepository;
import com.example.cnpmbe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class AuthenService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UsernameAuthenProvider usernameAuthenProvider;

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;


    public ResponseEntity<APIResponse> loginByUsername(HttpServletRequest req, LoginRequest loginRequest) {
        // Xác thực từ username và password.
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        authentication = usernameAuthenProvider.authenticate(authentication);


        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();
        String token;
        String refreshToken;
        Instant expiredIn;

        Optional<AuthToken> authTokenOptional = authTokenRepository.findAuthTokenByUserId(user.getId());
        if (authTokenOptional.isPresent()) {
            AuthToken authToken = authTokenOptional.get();
            Instant timeExpired = authToken.getExpiredIn();
            if (timeExpired.isBefore(Instant.now())) {
                token = tokenProvider.generateToken(customUserDetails);
                authToken.setAccessToken(token);
                authToken.setUpdatedAt(Instant.now());
                authToken.setExpiredIn(Instant.now().plus(EnvConfig.ACCESS_TOKEN_TIME / 60, ChronoUnit.MINUTES));
                authToken.setRefreshToken(StringUtils.generateRandomToken());
                refreshToken = authToken.getRefreshToken();
                expiredIn = authToken.getExpiredIn();
                authTokenRepository.save(authToken);
            } else {
                token = authToken.getAccessToken();
                refreshToken = authToken.getRefreshToken();
                expiredIn = authToken.getExpiredIn();
            }
        } else {
            AuthToken authToken = new AuthToken();
            token = tokenProvider.generateToken(customUserDetails);
            authToken.setAccessToken(token);
            authToken.setUpdatedAt(Instant.now());
            authToken.setExpiredIn(Instant.now().plus(EnvConfig.ACCESS_TOKEN_TIME / 60, ChronoUnit.MINUTES));
            authToken.setRefreshToken(StringUtils.generateRandomToken());
            refreshToken = authToken.getRefreshToken();
            expiredIn = authToken.getExpiredIn();
            authToken.setCreatedAt(Instant.now());
            authToken.setUser(user);
            authTokenRepository.save(authToken);
        }
        // Trả về jwt cho người dùng.
        AuthenInfoResponse authenInfoResponse = new AuthenInfoResponse(user.getId(),token, refreshToken, expiredIn);
        return ResponseEntity.ok(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, authenInfoResponse));
    }

    @Transactional
    public ResponseEntity<APIResponse> changePassword(HttpServletRequest req, ChangePasswordRequest changePasswordRequest, Long userId) {
        Optional<User> userOptional = userRepository.findUserById(userId);
        if (!userOptional.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.USER_ID_NOT_FOUND));
        User user = userOptional.get();

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword()))
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.USER_ID_NOT_FOUND));


        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS));
    }
}
