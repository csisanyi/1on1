package com.getbridge.homework.rest.model;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class Util {

    public User dtoToUsr(UserDto dto) {
        User user = new User();
        BeanUtils.copyProperties(dto,user);
        return user;
    }

}
