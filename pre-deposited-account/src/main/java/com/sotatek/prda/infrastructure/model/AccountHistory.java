package com.sotatek.prda.infrastructure.model;

import java.time.LocalDateTime;
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
@Entity(name = "account_history")
@Table(schema = "account_history")
public class AccountHistory {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
	
	@ManyToOne
    @JoinColumn(name = "customerId")
	public Customer customer;
	
	@Column(name = "type")
    public String type;
	
	@Column(name = "createTime")
    public Date createTime;
	
	@Column(name = "amount")
    public Long amount;
	
	@Column(name = "orderId")
    public Long orderId;
}
