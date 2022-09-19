package com.fadgiras.exos.model;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class DBFile {

    public DBFile(){

    }

    public DBFile(String UUID, String originalName, String name, Date createdAt, String extension){
        this.UUID = UUID;
        this.originalName = originalName;
        this.name = name;
        this.createdAt = createdAt;
        this.extension =extension;  

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "UUID", nullable = false)
    private String UUID;

    @Column(name = "originalName", nullable = false)
    private String originalName;

    @Column(name = "name", nullable = false)
    private String name; //File name hash

    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @Column(name = "extension", nullable = false)
    private String extension;


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt){
        this.createdAt = createdAt;
    }

    public long getId(){
        return id;
    }
    public void setId(long id) {
        this.id = id;        
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName){
        this.originalName = originalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension){
        this.extension = extension;
    }

    public String getUUID(){
        return UUID;
    }
    public void setUUID(String UUID) {
        this.UUID = UUID;        
    }

    @Override
    public String toString() {
        return "Files [id=" + id +", UUID="+ UUID + ", originalName=" + originalName + ", name=" + name + ", createdAt="+createdAt+ ", extension=" + extension
       + "]";
    }
}