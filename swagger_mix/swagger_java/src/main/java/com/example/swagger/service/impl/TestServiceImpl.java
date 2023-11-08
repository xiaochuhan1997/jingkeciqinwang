package com.example.swagger.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swagger.entity.SwaggerData;
import com.example.swagger.entity.Test;
import com.example.swagger.mapper.TestMapper;
import com.example.swagger.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public List<SwaggerData> findByCaseNoAndCaseDec(String caseNo, String caseDec) {
        QueryWrapper<SwaggerData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("case_no", caseNo).eq("case_dec", caseDec);
        return testMapper.selectList(queryWrapper);
    }
}
