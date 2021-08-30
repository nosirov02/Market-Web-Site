package com.company.market.controller;

import com.company.market.dto.product.ProductCreateDto;
import com.company.market.dto.product.ProductDetailDto;
import com.company.market.dto.product.ProductFilterDto;
import com.company.market.enums.ProductStatus;
import com.company.market.enums.ProfileRole;
import com.company.market.service.ProductService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(ProfileController.class);
    @Autowired
    private ProductService service;

    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@Valid @RequestBody ProductCreateDto dto) {
        service.create(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @Valid @RequestBody ProductCreateDto dto) {
        service.update(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/adm/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        ProductDetailDto result = service.getById(id, true);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/adm/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable Integer id,
                                          @RequestParam ProductStatus status) {
        service.changeStatus(id, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/adm/block/{id}")
    public ResponseEntity<?> block(@PathVariable Integer id) {
        service.block(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/adm/publish/{id}")
    private ResponseEntity<?> publish(@PathVariable Integer id) {
        service.publish(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/adm/list")
    public ResponseEntity<List<ProductDetailDto>> getList() {
        List<ProductDetailDto> result = service.getList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/adm/filter")
    public ResponseEntity<Page<ProductDetailDto>> getList(@RequestBody ProductFilterDto dto) {
        Page<ProductDetailDto> result = service.filter(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByIdAsUser(@PathVariable("id") Integer id) {
        ProductDetailDto result = service.getById(id, false);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/paging")
    public ResponseEntity<Page<ProductDetailDto>> getList(@RequestParam("page") Integer page,
                                                          @RequestParam("size") Integer size) {
        Page<ProductDetailDto> result = service.getPaginationList(page, size);
        return ResponseEntity.ok(result);
    }


}

