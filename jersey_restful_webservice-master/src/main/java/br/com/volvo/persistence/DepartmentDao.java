package br.com.volvo.persistence;

import java.util.Collection;

import br.com.volvo.entity.DepartmentEntity;

public interface DepartmentDao {
	
	public DepartmentEntity retrieve(Long id);
	
	public Collection<DepartmentEntity> list();

    public void delete(Long id);
	
	public void save(DepartmentEntity department);
}
