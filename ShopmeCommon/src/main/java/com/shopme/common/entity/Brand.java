package com.shopme.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

@Entity
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id =0;
	@Column(nullable = false,unique = true,length = 45)
	private String name;
	@Column(nullable = false,length = 45)
	private String logo;
	@ManyToMany
	@JoinTable(name="brands_categories",joinColumns = @JoinColumn(name="brand_id"),inverseJoinColumns = @JoinColumn(name="category_id") )
	private Set<Category> categories = new HashSet<>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Set<Category> getCategories() {
		return categories;
	}
	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	
	    @Transient
		public String getImagePath() {
			
			if (this.id == null) return "/images/image-thumbnail.png";
			return "/brands-image/" + this.id +"/" + this.logo;
		}
		public Brand(Integer id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		public Brand()
		{}	
	
}
