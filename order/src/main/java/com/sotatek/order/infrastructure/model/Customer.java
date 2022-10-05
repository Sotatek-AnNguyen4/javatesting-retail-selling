package com.sotatek.order.infrastructure.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customer")
@Table(schema = "customer")
public class Customer {

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
