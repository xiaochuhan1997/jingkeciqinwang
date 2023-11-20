package com.example.swagger.controller;

import com.example.swagger.entity.SwaggerData;
import com.example.swagger.service.TestService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@ApiIgnore
@RequestMapping("/test")
@Api(value = "Test API", description = "测试API")
public class TestController {

    @Autowired
    private TestService testService;

    @ApiOperation(value = "根据案例号和案例描述查找", notes = "根据案例号和案例描述查找相关信息", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"),
            @ApiResponse(code = 404, message = "未找到")
    })
    @GetMapping("/findByCaseNoAndCaseDec")
    public List<SwaggerData> findByCaseNoAndCaseDec(
            @ApiParam(value = "案例号", required = true) @RequestParam String caseNo,
            @ApiParam(value = "案例描述", required = true) @RequestParam String caseDec) {
        return testService.findByCaseNoAndCaseDec(caseNo, caseDec);
    }
}
