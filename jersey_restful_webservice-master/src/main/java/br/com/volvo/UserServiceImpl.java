package br.com.volvo;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.volvo.api.data.Department;
import br.com.volvo.api.data.User;
import br.com.volvo.entity.DepartmentEntity;
import br.com.volvo.entity.UserEntity;
import br.com.volvo.persistence.DepartmentDao;
import br.com.volvo.persistence.UserDao;

@Singleton
public class UserServiceImpl implements UserService {

	@Inject
	private UserDao userDao;
	
	@Inject
	private DepartmentDao departmentDao;
	
	public User retrieve(Long id) {
		return entityToUser(userDao.retrieve(id));
	}
	
	public Collection<User> list() {
		return entityToUsers(userDao.list());
	}

    public void delete(Long id) {
        userDao.delete(id);
    }

	public void save(User user) {
		departmentDao.save(departmentToEntity(user.getDepartment()));
		userDao.save(userToEntity(user));
	}

	// =========== Helpers ================
	
	private Collection<User> entityToUsers(Collection<UserEntity> entitys) {
		Collection<User> users = new ArrayList<User>();
		
		if (entitys != null && entitys.size() > 0) {
			for (UserEntity de: entitys) {
				User user = new User();
				user.setId(de.getId());
				user.setName(de.getName());
				user.setDescription(de.getDescription());
				if (de.getDepartment() != null) {
					user.setDepartment(new Department(de.getDepartment().getId(), de.getDepartment().getName(), de.getDepartment().getDescription()));
				}
					users.add(user);
			}
		}
		
		return users;
	}

	private User entityToUser(UserEntity entity) {
		User user = new User();

		if (entity != null) {
			user.setId(entity.getId());
			user.setName(entity.getName());
			user.setDescription(entity.getDescription());
			if (entity.getDepartment() != null) {
				user.setDepartment(new Department(entity.getDepartment().getId(), entity.getDepartment().getName(), entity.getDepartment().getDescription()));
			}
		}

		return user;
	}

	private UserEntity userToEntity(User user) {
		UserEntity entity = new UserEntity();

		if (user != null) {
			entity.setId(user.getId());
			entity.setName(user.getName());
			entity.setDescription(user.getDescription());
			if (user.getDepartment() != null) {
				entity.setDepartment(new DepartmentEntity(user.getDepartment().getId(), user.getDepartment().getName(), user.getDepartment().getDescription()));
			}
		}

		return entity;
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
