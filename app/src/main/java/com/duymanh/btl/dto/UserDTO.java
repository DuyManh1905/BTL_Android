package com.duymanh.btl.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private Integer id;

    private String name;

    // Constructor không tham số
    public UserDTO() {}

    // Constructor với tham số
    public UserDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter và Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}