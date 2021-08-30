package com.company.market.controller;

import com.company.market.dto.AuthDto;
import com.company.market.dto.RegistrationDto;
import com.company.market.dto.profile.ProfileDetailDto;
import com.company.market.repository.ProfileRepository;
import com.company.market.service.AuthorizationService;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class AuthorizationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);
    @Autowired
    private AuthorizationService service;
    @Autowired
    private ProfileRepository profileRepository;

    @PutMapping("/authorization")
    public ResponseEntity<?> authorization(@Valid @RequestBody AuthDto dto) {
        logger.info("Authorization: {}" + dto);
        ProfileDetailDto result = service.authorization(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationDto dto){
        logger.info("Authorization: {}" + dto);
        String result = service.registration(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/verification/{jwt}")
    public ResponseEntity<?> registration(@PathVariable("jwt") String jwt){
        String result = service.verification(jwt);
        return ResponseEntity.ok(result);
    }

}
