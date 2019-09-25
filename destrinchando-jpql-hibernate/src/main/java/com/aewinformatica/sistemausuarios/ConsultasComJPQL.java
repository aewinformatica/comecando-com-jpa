package com.aewinformatica.sistemausuarios;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class ConsultasComJPQL {

	public static void main(String[] args) {
		
		EntityManagerFactory entityManagerFactory = Persistence
					.createEntityManagerFactory("Usuarios-PU");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
					  entityManager.close();
					  entityManagerFactory.close();
		
	}
}
