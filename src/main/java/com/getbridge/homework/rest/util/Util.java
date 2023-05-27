package com.getbridge.homework.rest.util;

import com.getbridge.homework.rest.dto.UserDto;
import com.getbridge.homework.rest.entity.User;
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
