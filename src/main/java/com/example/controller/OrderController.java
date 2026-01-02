package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.entity.Order;
import com.example.service.OrderService;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RequestMapping("/order")
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/listOrder", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getAll() {
        List<Order> oderList = orderService.findAll();
        if (oderList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(oderList, HttpStatus.OK);
    }

}
