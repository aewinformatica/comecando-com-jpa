package com.aewinformatica.sistemausuarios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.aewinformatica.sistemausuarios.model.Usuario;


public class ConsultasComJPQL {

	public static void main(String[] args) {
		
		EntityManagerFactory entityManagerFactory = Persistence
					.createEntityManagerFactory("Usuarios-PU");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		primeiraConsulta(entityManager);
					  entityManager.close();
					  entityManagerFactory.close();
		
	}
	
	public static void primeiraConsulta(EntityManager entityManager) {
		
		String jpql = "select u from Usuario u";
		
		TypedQuery<Usuario>typedQuery = entityManager.createQuery(jpql,Usuario.class);
				List<Usuario> lista =  typedQuery.getResultList();
				
				lista.forEach(u-> System.out.println(u.getId() + ", " + u.getNome()));
		
	}
}
