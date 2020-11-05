package com.restdeveloper.app.ws.ui.controller;

import com.restdeveloper.app.ws.ui.model.request.LoginRequestModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class AuthenticationSwaggerLoginController {
    @ApiOperation("User Login")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Response Headers",
                    responseHeaders = {
                            @ResponseHeader(name = "authorization", description = "Bearer <JWT value>", response =
                                    String.class),
                            @ResponseHeader(name = "userId", description = "<Public user id value", response =
                                    String.class)
                    })
    })
    @PostMapping("/login")
    public void fakeLogin(@RequestBody LoginRequestModel loginRequestModel) {
        throw new IllegalStateException("This methode is implemented by Spring Security, do not use");
    }
}
