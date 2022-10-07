package com.sotatek.reinv.infrastructure.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product")
@Table(schema = "product")
public class Product {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
	
	@Column(name = "name")
	public String name;
	
	@Column(name = "quantity")
	public Integer quantity;
	
	@Column(name = "price")
	public Long price;
	
	@Column(name = "description")
	public String description;
	
	@Column(name = "retailId")
	public Long retailId;
	
}
