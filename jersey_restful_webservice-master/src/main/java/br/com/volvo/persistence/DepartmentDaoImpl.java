package br.com.volvo.persistence;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.volvo.entity.DepartmentEntity;

@Singleton
public class DepartmentDaoImpl implements DepartmentDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persist-unit");
	
	public DepartmentEntity retrieve(Long id) {
		EntityManager em = emf.createEntityManager();

		DepartmentEntity entity = null;

		try {
			entity = em.find(DepartmentEntity.class, id);
		} finally {
			em.close();
		}
		
		return entity;
	}
	
	public Collection<DepartmentEntity> list() {
    	EntityManager em = emf.createEntityManager();

    	Collection<DepartmentEntity> entitys = new ArrayList<DepartmentEntity>();
    	Query q = em.createQuery("select p from DepartmentEntity p");

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

            DepartmentEntity entity = em.find(DepartmentEntity.class, id);

            if(entity == null) {
                System.out.println("Error Deleting Department: Department not found");
            }
            else {
                em.remove(entity);
            }

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error Deleting Department: " + e.getMessage());

            transaction.rollback();
        } finally {
            em.close();
        }
    }

	public void save(DepartmentEntity department) {
		EntityManager em = emf.createEntityManager();

		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();

            em.merge(department);

			transaction.commit();
		} catch (Exception e) {
			System.out.println("Error Saving Department: " + e.getMessage());

			transaction.rollback();
		} finally {
			em.close();
		}
	}
}
