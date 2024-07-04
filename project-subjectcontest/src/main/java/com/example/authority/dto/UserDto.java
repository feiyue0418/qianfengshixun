package com.example.authority.dto;

import com.example.authority.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private Integer id;

    private Integer roleId;

    private String username;

    private String password;

    private String nickname;

    private String headerUrl;

    private String token;

    private List<Menu> menuList;
}
