package com.loneliness.controller;

import com.loneliness.dto.OrdersDTO;
import com.loneliness.entity.Status;
import com.loneliness.entity.domain.Orders;
import com.loneliness.service.OrderService;
import com.loneliness.transfer.Exist;
import com.loneliness.transfer.New;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;
import java.io.IOException;

@RestController
@RequestMapping("orders")
public class OrderController extends CommonController<Orders, OrdersDTO> {


    public OrderController(OrderService orderService) {
        this.service = orderService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("{id}{user_id}")
    public Orders getOneById(@PathVariable Integer id, @PathVariable Integer user_id) {
        if (checkAccess(id, user_id)) {
            throw new AccessDeniedException("you haven't got access to this function");
        }
        return find(id);
    }


    @Override
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Orders create(@Validated(New.class) @RequestBody OrdersDTO dto)  throws IOException {
        return ((OrderService) service).create(dto.fromDTO());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping(value = "{id}{user_id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces =  MediaType.APPLICATION_JSON_VALUE)
    public Orders update(@Validated(Exist.class) @RequestBody OrdersDTO dto, @PathVariable Integer id, @PathVariable Integer user_id)  {
        if (checkAccess(id, user_id)) {
            throw new AccessDeniedException("you haven't got access to this function");
        }
        else {
           if( find(id).getStatus().equals(Status.CHECKOUT_IN_PROGRESS)){
               return service.save(dto.fromDTO());
           }
           else{
               throw new IllegalStateException("Order was already accept or executed");
           }
        }

    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("{id}{user_id}")
    public void delete(@PathVariable Integer id,@PathVariable Integer user_id){
        if (checkAccess(id, user_id)) {
            throw new AccessDeniedException("you haven't got access to this function");
        }
        else {
            if( find(id).getStatus().equals(Status.CHECKOUT_IN_PROGRESS)){
                service.delete(id);
            }
            else{
                throw new IllegalStateException("Order was already accept or executed");
            }
        }

    }
    private boolean checkAccess(Integer id, Integer user_id) {
        return !getOneById(id).getUser().getId().equals(user_id);
    }

}
