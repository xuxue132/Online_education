package com.example.community_education.Service;

import com.example.community_education.Tool.Result;

import java.util.Map;

public interface LibraryService {
    Result Books(Map<String, Object> map);

    Result ToTalData(Map<String, Object> map);

    Result TheBook(Map<String, Object> map);

    Result selectLibrary(Map<String, Object> map);

    Result AddLibrary(Map<String, Object> map);

    Result UpdateLibrary(Map<String, Object> map);

    Result UpdateLibraryPicture(Map<String, Object> map);

    Result DeleteLibrary(Map<String, Object> map);
}
