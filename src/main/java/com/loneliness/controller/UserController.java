package com.loneliness.controller;

import com.loneliness.dto.UserDTO;
import com.loneliness.entity.domain.User;
import com.loneliness.exception.BadArgumentException;
import com.loneliness.service.UserService;
import com.loneliness.transfer.Exist;
import com.loneliness.transfer.New;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@RestController
@RequestMapping("user")

public class UserController extends CommonController<User, UserDTO> {

    public UserController(UserService userService) {
        this.service = userService;
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User create(@NotNull @Validated(New.class) @RequestBody UserDTO dto) throws IOException {
        checkPassword(dto);
        return service.save(dto.fromDTO());

    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User update(@NotNull @Validated(Exist.class) @RequestBody UserDTO dto, @PathVariable Integer id) {
        checkPassword(dto);
        find(id);
        return service.save(dto.fromDTO());
    }

    private void checkPassword(UserDTO dto) {
        if (!dto.getPassword().equals(dto.getCheckPassword()))
            throw new BadArgumentException("passwords are not the same");
    }
}
