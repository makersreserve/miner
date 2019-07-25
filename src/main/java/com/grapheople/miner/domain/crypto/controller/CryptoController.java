package com.grapheople.miner.domain.crypto.controller;

import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("crypto")
@RequiredArgsConstructor
public class CryptoController {
    private final StringEncryptor jasyptStringEncryptor;
    @GetMapping("encrypt")
    public String encryptString(@RequestParam String message) {
        return jasyptStringEncryptor.encrypt(message);
    }

    @GetMapping("decrypt")
    public String decryptString(@RequestParam String message) {
        return jasyptStringEncryptor.decrypt(message);
    }
}
