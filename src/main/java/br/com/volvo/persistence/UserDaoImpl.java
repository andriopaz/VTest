package br.com.volvo.persistence;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.volvo.entity.UserEntity;

@Singleton
public class UserDaoImpl implements UserDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persist-unit");
	
	public UserEntity retrieve(Long id) {
		EntityManager em = emf.createEntityManager();

		UserEntity entity = null;

		try {
			entity = em.find(UserEntity.class, id);
		} finally {
			em.close();
		}
		
		return entity;
	}

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            UserEntity entity = em.find(UserEntity.class, id);

            if(entity == null) {
                System.out.println("Error Deleting user: user not found");
            }
            else {
                em.remove(entity);
            }

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error Deleting user: " + e.getMessage());

            transaction.rollback();
        } finally {
            em.close();
        }
    }
    
    public Collection<UserEntity> list() {
    	EntityManager em = emf.createEntityManager();

    	Collection<UserEntity> entitys = new ArrayList<UserEntity>();
    	Query q = em.createQuery("select u from UserEntity u");

		try {
			entitys = q.getResultList();
		} finally {
			em.close();
		}
		
		return entitys;
    }

	public void save(UserEntity user) {
		EntityManager em = emf.createEntityManager();

		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();

            em.merge(user);

			transaction.commit();
		} catch (Exception e) {
			System.out.println("Error Saving user: " + e.getMessage());

			transaction.rollback();
		} finally {
			em.close();
		}
	}
}
