package com.company.market.controller;

import com.company.market.dto.order.OrderCreateDto;
import com.company.market.dto.order.OrderDetailDto;
import com.company.market.dto.order.OrderFilterDto;
import com.company.market.service.OrderService;
import com.company.market.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService service;

    @PostMapping("/action/create")
    public ResponseEntity<?> create(@RequestBody OrderCreateDto dto) {
        Integer userId = SpringSecurityUtil.getUserId();
        OrderDetailDto result = service.create(userId, dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/action/{id}")
    public ResponseEntity<?> update(@Valid OrderCreateDto dto,
                                    @PathVariable("id") Integer orderId) {
        Integer userId = SpringSecurityUtil.getUserId();
        OrderDetailDto result = service.update(userId, orderId, dto);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/action/{id}")
    public ResponseEntity<OrderDetailDto> getById(@PathVariable("id") Integer productId) {
        Integer userID = SpringSecurityUtil.getUserId();
        OrderDetailDto result = service.getById(productId);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/action/filter")
    public ResponseEntity<Page<OrderDetailDto>> filter(@RequestBody OrderFilterDto filterDto){
        Integer userID = SpringSecurityUtil.getUserId();
        Page<OrderDetailDto> result = service.filter(filterDto);
        return ResponseEntity.ok().body(result);

    }
}

