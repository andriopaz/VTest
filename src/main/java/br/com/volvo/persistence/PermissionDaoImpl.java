package br.com.volvo.persistence;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.volvo.entity.PermissionEntity;

@Singleton
public class PermissionDaoImpl implements PermissionDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persist-unit");
	
	public PermissionEntity retrieve(Long id) {
		EntityManager em = emf.createEntityManager();

		PermissionEntity entity = null;

		try {
			entity = em.find(PermissionEntity.class, id);
		} finally {
			em.close();
		}
		
		return entity;
	}
	
	public Collection<PermissionEntity> list() {
    	EntityManager em = emf.createEntityManager();

    	Collection<PermissionEntity> entitys = new ArrayList<PermissionEntity>();
    	Query q = em.createQuery("select p from PermissionEntity p");

		try {
			entitys = q.getResultList();
		} finally {
			em.close();
		}
		
		return entitys;
	}

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            PermissionEntity entity = em.find(PermissionEntity.class, id);

            if(entity == null) {
                System.out.println("Error Deleting Permission: Permission not found");
            }
            else {
                em.remove(entity);
            }

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error Deleting Permission: " + e.getMessage());

            transaction.rollback();
        } finally {
            em.close();
        }
    }

	public void save(PermissionEntity permission) {
		EntityManager em = emf.createEntityManager();

		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();

            em.merge(permission);

			transaction.commit();
		} catch (Exception e) {
			System.out.println("Error Saving Permission: " + e.getMessage());

			transaction.rollback();
		} finally {
			em.close();
		}
	}
}
