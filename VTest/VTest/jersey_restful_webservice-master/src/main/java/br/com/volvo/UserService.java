package br.com.volvo;

import java.util.Collection;

import br.com.volvo.api.data.User;

public interface UserService {

	public User retrieve(Long id);
	
	public Collection<User> list();

    public void delete(Long id);
	
	public void save(User user);
}
