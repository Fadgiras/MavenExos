package com.fadgiras.exos.model;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class File {

    //id, nomOri, nom, ext,
    private long id;
    private String originalName;
    private String name; //File name hash
    private String extension;
    private Date createdAt;
 
    public File(){

    }

    public File(String originalName, String name,String extension,Date createdAt){
        this.originalName = originalName;
        this.name = name;
        this.extension = extension;
        this.createdAt = createdAt;  

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId(){
        return id;
    }
    public void setId(long id) {
        this.id = id;        
    }

    @Column(name = "originalName", nullable = false)
    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName){
        this.originalName = originalName;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Column(name = "extension", nullable = false)
    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension){
        this.extension = extension;
    }

    @Column(name = "createdAt", nullable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt){
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Files [id=" + id + ", originalName=" + originalName + ", name=" + name + ", extension=" + extension + ", createdAt="+createdAt
       + "]";
    }
}