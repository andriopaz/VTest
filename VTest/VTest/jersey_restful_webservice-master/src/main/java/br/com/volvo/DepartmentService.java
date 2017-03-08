package br.com.volvo;

import java.util.Collection;

import br.com.volvo.api.data.Department;

public interface DepartmentService {

	public Department retrieve(Long id);
	
	public Collection<Department> list();

    public void delete(Long id);
	
	public void save(Department department);
}
