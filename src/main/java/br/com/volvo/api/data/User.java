package br.com.volvo.api.data;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = -5183069099661541090L;
	
	private Long id;
	private String name;
	private String description;
	private Department department;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
