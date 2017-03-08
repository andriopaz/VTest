package br.com.volvo.persistence;

import java.util.Collection;

import br.com.volvo.entity.PermissionEntity;

public interface PermissionDao {
	
	public PermissionEntity retrieve(Long id);
	
	public Collection<PermissionEntity> list();

    public void delete(Long id);
	
	public void save(PermissionEntity permission);
}
