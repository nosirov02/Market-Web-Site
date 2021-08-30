package com.company.market.controller;

import com.company.market.dto.profile.*;
import com.company.market.enums.ProfileRole;
import com.company.market.enums.ProfileStatus;
import com.company.market.service.ProfileService;
import com.company.market.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService service;

    /* ======*****==== USER  =====****====== */

    @GetMapping("/detail")
    public ResponseEntity<?> getProfileDetail(HttpServletRequest request) {
        Integer userId = SpringSecurityUtil.getUserId();
        ProfileDetailDto dto = service.getDetail(userId);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/detail")
    public ResponseEntity<?> updateDetail(@Valid @RequestBody ProfileUpdateDto dto) {
        Integer userId = SpringSecurityUtil.getUserId();
        service.updateUserDetail(userId, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/email")
    public ResponseEntity<?> updateEmail(@RequestParam("email") String email,
                                         HttpServletRequest request) {
        Integer userId = SpringSecurityUtil.getUserId();
        service.updateUserEmail(userId, email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verification/{jwt}")
    public ResponseEntity<?> verification(@PathVariable("jwt") String jwt) {
        String result = service.verification(jwt);
        return ResponseEntity.ok(result);
    }


    //========****===== ADMIN =====*******======

    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody ProfileCreateDto dto) {
        service.create(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/adm/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        ProfileDetailDto result = service.getById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody ProfileUpdateDto dto,
                                    @PathVariable("id") Integer id) {
        service.update(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/adm/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Integer id,
                                          @RequestParam ProfileStatus status) {
        service.changeStatus(id, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/adm/list")
    public ResponseEntity<List<ProfileDetailDto>> getList() {
        List<ProfileDetailDto> list = service.getList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/adm/paging")
    public ResponseEntity<Page<ProfileDetailDto>> getList(@RequestParam("page") Integer page,
                                                          @RequestParam("size") Integer size) {
        Page<ProfileDetailDto> list = service.getPaginationList(page, size);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/adm/filter")
    public ResponseEntity<Page<ProfileDetailDto>> filter(@RequestBody ProfileFilterDto dto) {
        Page<ProfileDetailDto> result = service.filter(dto);
        return ResponseEntity.ok(result);
    }
}
