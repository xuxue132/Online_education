package com.example.community_education.Service;

import com.example.community_education.Tool.Result;

import java.util.Map;

public interface CharacterProjectService  {

    Result selectAll(Map<String, Object> map);

    Result Number();

    Result TheProject(Map<String, Object> map);

    Result selectCharacterProject(Map<String, Object> map);

    int AddCharacterProject(Map<String, Object> map);

    void updateImages(Map<String, Object> map);

    void UpdateCharacterProject(Map<String, Object> map);

    void DeleteCharacterProject(Map<String, Object> map);
}
