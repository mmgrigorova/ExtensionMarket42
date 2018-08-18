package com.antman.extensionmarket42.models;

import javax.persistence.*;

@Entity
@Table(name = "extension_tags")
public class ExtensionTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "extensionId")
    private Extension extension;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "tagId")
    private Tag tag;

    public ExtensionTag(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Extension getExtension() {
        return extension;
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
