package com.antman.extensionmarket42.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagId;

    @Column
    private String tagTitle;

    @OneToMany(mappedBy = "tag")
    List<ExtensionTag> extensionTags;

    public Tag(){
    }


    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagTitle() {
        return tagTitle;
    }

    public void setTagTitle(String tagTitle) {
        this.tagTitle = tagTitle;
    }

    public List<ExtensionTag> getExtensionTags() {
        return extensionTags;
    }

    public void setExtensionTags(List<ExtensionTag> extensionTags) {
        this.extensionTags = extensionTags;
    }
}
