package br.com.volvo;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.volvo.api.data.Department;
import br.com.volvo.entity.DepartmentEntity;
import br.com.volvo.persistence.DepartmentDao;

@Singleton
public class DepartmentServiceImpl implements DepartmentService {

	@Inject
	private DepartmentDao departmentDao;
	
	public Department retrieve(Long id) {
		return entityToDepartment(departmentDao.retrieve(id));
	}
	
	public Collection<Department> list() {
		return entityToDepartments(departmentDao.list());
	}

    public void delete(Long id) {
    	departmentDao.delete(id);
    }

	public void save(Department department) {
		departmentDao.save(departmentToEntity(department));
	}

	// =========== Helpers ================
	
	private Collection<Department> entityToDepartments(Collection<DepartmentEntity> entitys) {
		Collection<Department> departments = new ArrayList<Department>();
		
		if (entitys != null && entitys.size() > 0) {
			for (DepartmentEntity de: entitys) {
				Department department = new Department();
				department.setId(de.getId());
				department.setName(de.getName());
				department.setDescription(de.getDescription());
				departments.add(department);
			}
		}
		
		return departments;
	}

	private Department entityToDepartment(DepartmentEntity entity) {
		Department department = new Department();

		if (entity != null) {
			department.setId(entity.getId());
			department.setName(entity.getName());
			department.setDescription(entity.getDescription());
		}

		return department;
	}

	private DepartmentEntity departmentToEntity(Department department) {
		DepartmentEntity entity = new DepartmentEntity();

		if (department != null) {
			entity.setId(department.getId());
			entity.setName(department.getName());
			entity.setDescription(department.getDescription());
		}

		return entity;
	}
}
