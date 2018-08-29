package com.antman.extensionmarket42.models.extensions;

import com.antman.extensionmarket42.models.extensions.ExtensionTag;

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

    @ManyToMany(mappedBy = "tags")
    private List<Extension> extensions;

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

    public List<Extension> extensions() {
        return extensions;
    }

    public void setExtensionTags(List<Extension> extensions) {
        this.extensions = extensions;
    }
}
