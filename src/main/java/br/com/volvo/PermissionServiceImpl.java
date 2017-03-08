package br.com.volvo;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.volvo.api.data.Permission;
import br.com.volvo.entity.PermissionEntity;
import br.com.volvo.persistence.PermissionDao;

@Singleton
public class PermissionServiceImpl implements PermissionService {

	@Inject
	private PermissionDao permissionDao;
	
	public Permission retrieve(Long id) {
		return entityToPermission(permissionDao.retrieve(id));
	}
	
	public Collection<Permission> list() {
		return entityToPermissions(permissionDao.list());
	}

    public void delete(Long id) {
    	permissionDao.delete(id);
    }

	public void save(Permission permission) {
		permissionDao.save(permissionToEntity(permission));
	}

	// =========== Helpers ================
	
	private Collection<Permission> entityToPermissions(Collection<PermissionEntity> entitys) {
		Collection<Permission> permissions = new ArrayList<Permission>();
		
		if (entitys != null && entitys.size() > 0) {
			for (PermissionEntity pe: entitys) {
				Permission permission = new Permission();
				permission.setId(pe.getId());
				permission.setName(pe.getName());
				permission.setDescription(pe.getDescription());
				permissions.add(permission);
			}
		}
		
		return permissions;
	}

	private Permission entityToPermission(PermissionEntity entity) {
		Permission permission = new Permission();

		if (entity != null) {
			permission.setId(entity.getId());
			permission.setName(entity.getName());
			permission.setDescription(entity.getDescription());
		}

		return permission;
	}

	private PermissionEntity permissionToEntity(Permission permission) {
		PermissionEntity entity = new PermissionEntity();

		if (permission != null) {
			entity.setId(permission.getId());
			entity.setName(permission.getName());
			entity.setDescription(permission.getDescription());
		}

		return entity;
	}
}
