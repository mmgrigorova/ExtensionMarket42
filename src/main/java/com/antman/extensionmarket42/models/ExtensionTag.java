package com.antman.extensionmarket42.models;

import javax.persistence.*;

@Entity
@Table(name = "extension_tags")
public class ExtensionTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int extensionId;
    @Column
    private int tagId;

    public ExtensionTag(){
    }

    public int getExtensionId() {
        return extensionId;
    }

    public void setExtensionId(int extensionId) {
        this.extensionId = extensionId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}
