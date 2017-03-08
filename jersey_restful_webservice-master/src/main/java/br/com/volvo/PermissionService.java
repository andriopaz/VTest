package br.com.volvo;

import java.util.Collection;

import br.com.volvo.api.data.Permission;

public interface PermissionService {

	public Permission retrieve(Long id);
	
	public Collection<Permission> list();

    public void delete(Long id);
	
	public void save(Permission permission);
}
