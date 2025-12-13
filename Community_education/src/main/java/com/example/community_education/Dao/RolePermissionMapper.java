package com.example.community_education.Dao;

import com.example.community_education.Model.RolePermission;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolePermissionMapper {

    RolePermission selectStatus(String phone);
}