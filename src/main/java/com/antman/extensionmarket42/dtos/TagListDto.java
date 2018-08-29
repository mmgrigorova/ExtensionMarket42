package com.antman.extensionmarket42.dtos;

import java.util.ArrayList;
import java.util.List;

public class TagListDto {
    private List<String> tagList;

    public TagListDto() {
        tagList = new ArrayList<>();
    }

    public void addTag(String tag){
        tagList.add(tag);
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
}
