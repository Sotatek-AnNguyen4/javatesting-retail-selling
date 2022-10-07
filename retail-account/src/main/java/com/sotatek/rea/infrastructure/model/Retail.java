package com.sotatek.rea.infrastructure.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "retail")
@Table(schema = "retail")
public class Retail {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
	
    @Column(name = "name")
    public String name;
    
    @Column(name = "email")
    public String email;
    
    @Column(name = "phone")
    public String phone;
    
    @Column(name = "token")
    public String token;
    
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
    
}
