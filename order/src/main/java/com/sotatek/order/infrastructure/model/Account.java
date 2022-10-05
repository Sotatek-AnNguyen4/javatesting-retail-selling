package com.sotatek.order.infrastructure.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "account")
@Table(schema = "account")
public class Account {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
	
	@ManyToOne
    @JoinColumn(name = "customerId")
	public Customer customer;
	
	@Column(name = "balance")
    public Long balance;
	
}
