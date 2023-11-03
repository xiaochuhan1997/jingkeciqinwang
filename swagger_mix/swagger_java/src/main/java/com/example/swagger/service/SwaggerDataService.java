package com.example.swagger.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.swagger.entity.SwaggerData;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface SwaggerDataService extends IService<SwaggerData> {

    @Autowired
    List<SwaggerData> analyze();
    SwaggerData getInputOutputParamsById(Long id);

    SwaggerData saveData(@RequestBody Map<String, Object> requestData);

    IPage<SwaggerData> selectSwaggerDataPage(int current, int size);

}
