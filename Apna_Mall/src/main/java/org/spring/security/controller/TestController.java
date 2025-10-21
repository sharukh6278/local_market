package org.spring.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.entity.auth.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    Logger logger= LoggerFactory.getLogger(TestController.class);
    @PreAuthorize("hasPermission(#userName,'read')")
    @GetMapping("/test/{userName}")
    public String test(@PathVariable("userName") String userName) {
        logger.info("test/username:{} ",userName);
        return "this is test";
    }

    @PreAuthorize("hasPermission(#foo, 'write')")
    @PostMapping("/foos")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public User create(@RequestBody User user) {
        return user;
    }
}