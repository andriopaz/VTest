package br.com.volvo.persistence;

import java.util.Collection;

import br.com.volvo.entity.UserEntity;

public interface UserDao {
	
	public UserEntity retrieve(Long id);
	
	public Collection<UserEntity> list();

    public void delete(Long id);
	
	public void save(UserEntity user);
}
