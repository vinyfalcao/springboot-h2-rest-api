package com.springboot.h2.restapi.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

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
public class Image implements Serializable {

	private static final long serialVersionUID = -622584948298350860L;

	@JsonView({ ProductViews.DetailedView.class, ImageViews.Default.class })
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonView({ ProductViews.DetailedView.class, ImageViews.Default.class })
	@NotNull
	private String type;

	@JsonView({ ImageViews.Default.class })
	@ManyToOne
	@JoinColumn(name = "productId", nullable = false)
	private Product product;

}
