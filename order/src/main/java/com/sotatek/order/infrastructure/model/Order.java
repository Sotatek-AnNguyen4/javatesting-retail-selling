package com.sotatek.order.infrastructure.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "`order`")
@Table(schema = "`order`")
public class Order {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
	
	@Column(name = "totalAmount")
    public Long totalAmount;
	
	@Column(name = "customerId")
    public Long customerId;
	
	@Column(name = "createTime")
    public Date createTime;
	
	@Column(name = "state")
    public String state;
}
