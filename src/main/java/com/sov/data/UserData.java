package com.sov.data;

import com.sov.model.RoleModel;
import com.sov.model.UserModel;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserData {
    private Long id;
    private String username;
    private String email;
    private boolean active;
    private List<String> roles;

    public static UserData from(UserModel userModel) {
        UserData userData = new UserData();
        userData.id = userModel.getId();
        userData.username = userModel.getUsername();
        userData.email = userModel.getEmail();
        userData.active = userModel.isActive();
        userData.roles = userModel.getRoles().stream()
                .map(RoleModel::getAuthority)
                .collect(Collectors.toList());
        return userData;
    }
}
