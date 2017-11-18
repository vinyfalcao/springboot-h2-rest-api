package com.springboot.h2.restapi.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;
import com.springboot.h2.restapi.views.ImageViews;
import com.springboot.h2.restapi.views.ProductViews;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Vinicius Falcão
 *
 */
@Getter
@Setter
@Entity
@NamedEntityGraph(name = "Product.detail", attributeNodes = { @NamedAttributeNode("images"),
		@NamedAttributeNode("childrenProducts"), @NamedAttributeNode("parentProduct") })
public class Product {

	@JsonView({ ProductViews.LazyView.class, ImageViews.Default.class })
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonView({ ProductViews.LazyView.class, ImageViews.Default.class })
	@NotEmpty
	private String name;

	@JsonView({ ProductViews.LazyView.class, ImageViews.Default.class })
	@NotEmpty
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idParentProduct")
	private Product parentProduct;

	@JsonView(ProductViews.DetailedView.class)
	@OneToMany(mappedBy = "parentProduct", cascade = CascadeType.REMOVE)
	private Set<Product> childrenProducts;

	@JsonView(ProductViews.DetailedView.class)
	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
	private Set<Image> images;

}
