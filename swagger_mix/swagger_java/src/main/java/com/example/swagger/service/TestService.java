package com.example.swagger.service;
import com.example.swagger.entity.SwaggerData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TestService {
    List<SwaggerData> findByCaseNoAndCaseDec(String caseNo, String caseDec);
}
