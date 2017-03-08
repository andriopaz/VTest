package br.com.volvo.config;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import br.com.volvo.DepartmentService;
import br.com.volvo.DepartmentServiceImpl;
import br.com.volvo.PermissionService;
import br.com.volvo.PermissionServiceImpl;
import br.com.volvo.UserService;
import br.com.volvo.UserServiceImpl;
import br.com.volvo.persistence.DepartmentDao;
import br.com.volvo.persistence.DepartmentDaoImpl;
import br.com.volvo.persistence.PermissionDao;
import br.com.volvo.persistence.PermissionDaoImpl;
import br.com.volvo.persistence.UserDao;
import br.com.volvo.persistence.UserDaoImpl;

public class ApplicationBinder extends AbstractBinder {
	
    @Override
    protected void configure() {
    	// services
        bind(UserServiceImpl.class).to(UserService.class).in(Singleton.class);
        bind(DepartmentServiceImpl.class).to(DepartmentService.class).in(Singleton.class);
        bind(PermissionServiceImpl.class).to(PermissionService.class).in(Singleton.class);
        
        // dao
        bind(UserDaoImpl.class).to(UserDao.class).in(Singleton.class);
        bind(DepartmentDaoImpl.class).to(DepartmentDao.class).in(Singleton.class);
        bind(PermissionDaoImpl.class).to(PermissionDao.class).in(Singleton.class);
    }
}