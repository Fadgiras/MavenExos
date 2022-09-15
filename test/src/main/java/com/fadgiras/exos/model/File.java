package com.fadgiras.exos.model;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class File {

    public File(){

    }

    public File(String originalName, String name, Date createdAt){
        this.originalName = originalName;
        this.name = name;
        this.createdAt = createdAt;  

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "originalName", nullable = false)
    private String originalName;

    @Column(name = "name", nullable = false)
    private String name; //File name hash

    @Column(name = "createdAt", nullable = false)
    private Date createdAt;


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

    @Override
    public String toString() {
        return "Files [id=" + id + ", originalName=" + originalName + ", name=" + name + ", createdAt="+createdAt
       + "]";
    }
}