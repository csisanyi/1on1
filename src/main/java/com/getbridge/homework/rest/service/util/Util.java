package com.getbridge.homework.rest.service.util;

import com.getbridge.homework.rest.dto.UserDto;
import com.getbridge.homework.rest.entity.OneOnOne;
import com.getbridge.homework.rest.dto.OneOnOneDto;
import com.getbridge.homework.rest.entity.User;
import com.getbridge.homework.rest.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class Util {

    @Autowired
    UserRepository userRepository;



    public User dtoToUsr(UserDto dto) {
        User user = new User();
        BeanUtils.copyProperties(dto,user);
        return user;
    }

    public OneOnOne dtoToOneOnOne(OneOnOneDto oneOnOneDto) {
        return convertOneOnOneDtoToEntity(oneOnOneDto);
    }

    public OneOnOne convertOneOnOneDtoToEntity(OneOnOneDto oneOnOneDto) {
        OneOnOne oneOnOne = new OneOnOne();
        oneOnOne.setTitle(oneOnOneDto.getTitle());
        oneOnOne.setPlannedDate(oneOnOneDto.getPlannedDate());
        oneOnOne.setDescription(oneOnOneDto.getDescription());
        oneOnOne.setLocation(oneOnOneDto.getLocation());

        List<String> participants = new ArrayList<>();
        for (String participantId : oneOnOneDto.getParticipantIds()) {
            participants.add(participantId);
        }
        oneOnOne.setParticipantIds(participants);
        return oneOnOne;
    }

}
