package com.vtm.controller;

import com.vtm.dto.request.LoginRequestDto;
import com.vtm.dto.request.RegisterRequestDto;
import com.vtm.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Mail, şifre, isim, soyisim ve rol girilerek yeni kullanıcı oluşturan metot. #1")
    public ResponseEntity<Boolean> register(@RequestBody @Valid RegisterRequestDto dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Mail ve şifre ile giriş yapılmasını sağlayan metot. #0")
    public ResponseEntity<String> authenticate(@RequestBody @Valid LoginRequestDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
