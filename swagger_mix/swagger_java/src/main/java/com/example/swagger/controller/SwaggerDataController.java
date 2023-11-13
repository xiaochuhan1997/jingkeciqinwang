package com.example.swagger.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.swagger.common.R;
import com.example.swagger.entity.SwaggerData;
import com.example.swagger.service.SwaggerDataService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/swagger")
@Api(value = "Swagger API", description = "SwaggerAPI")
@Component
public class SwaggerDataController {
    @Autowired
    private SwaggerDataService swaggerDataService;

    @PostMapping("/analyze")//swagger解析
    public R<List<SwaggerData>> analyze(@RequestBody Object a) {
        log.info("解析接口开始:{}");
        List<SwaggerData> dataList = swaggerDataService.analyze();
        return R.success(dataList);
    }
    //swagger查询
    @PostMapping("/query")
    public R<List<SwaggerData>> query(@RequestBody Object a) {
        log.info("查询接口开始:{}");
        List<SwaggerData> dataList = swaggerDataService.list();
        return R.success(dataList);
    }
    @GetMapping
    private String list() {
        return "get ok";
    }
    //保存案例
    @PostMapping("/saveData")
    public SwaggerData saveData(@RequestBody Map<String, Object> requestData) {
        log.info("开始保存数据:{}");
        return swaggerDataService.saveData(requestData);
    }
    @GetMapping("/selectByPage")
    public IPage<SwaggerData> getSwaggerDataPage(@RequestParam(defaultValue = "1") int current,
                                                 @RequestParam(defaultValue = "10") int size) {
        // 调用 Service 中的分页查询方法
//        IPage<SwaggerData> pageResult = swaggerDataService.selectSwaggerDataPage(current, size);
        return swaggerDataService.selectSwaggerDataPage(current, size);
    }

    @DeleteMapping("/deleteRecordforce/{id}")
    public ResponseEntity<String> deleteRecord(@PathVariable Long id) {
        boolean result = swaggerDataService.removeById(id);
        if (result) {
            return ResponseEntity.ok("记录删除成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("记录删除失败");
        }
    }
}
