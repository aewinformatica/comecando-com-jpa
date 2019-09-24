package com.aewinformatica.comece_assim_com_jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.aewinformatica.comece_assim_com_jpa.model.Cliente;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Cliente-PU");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		System.out.println("Hello World!");
		
		Cliente cliente = new Cliente();
				cliente.setId(1);
				cliente.setNome("Padaria nossa Alegria");
				
				entityManager.getTransaction().begin();
				
//		        Inserindo um registro com persist.(que não nasceu gerenciado)
				entityManager.persist(cliente);
				
				entityManager.getTransaction().commit();
				
		entityManager.close();
		entityManagerFactory.close();
    }
}
