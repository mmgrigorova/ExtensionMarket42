package com.antman.extensionmarket42.extensions.models;

import com.antman.extensionmarket42.extensions.models.ExtensionTag;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column
    private String tagTitle;

    @OneToMany(mappedBy = "tag")
    List<ExtensionTag> extensionTags;

    public Tag(){
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
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
