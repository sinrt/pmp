package com.pmp.nwms.web.rest.v2;

import com.pmp.nwms.service.UserService;
import com.pmp.nwms.web.rest.util.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userResourceV2")
@RequestMapping("/api/v2")
public class UserResource {
    @Autowired
    private UserService userService;

    @DeleteMapping("/user/full/delete/{userId}")
    public ResponseEntity<Boolean> fullDeleteUser(@PathVariable("userId") Long userId){
        userService.fullDeleteUser(userId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert("User", userId.toString()))
            .body(true);
    }
}
