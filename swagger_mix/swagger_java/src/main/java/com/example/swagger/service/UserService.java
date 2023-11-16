package com.example.swagger.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.swagger.entity.User;
import com.example.swagger.entity.UserDetail;

import java.util.List;


public interface UserService extends IService<User> {
    boolean checkUsernameExists(String username);
    void saveUser(User user, List<UserDetail> userDetailList);


}
