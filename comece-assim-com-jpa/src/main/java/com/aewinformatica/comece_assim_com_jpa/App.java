package com.aewinformatica.comece_assim_com_jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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


//        Inserindo um registro com persist.(que não nasceu gerenciado)
//		Cliente cliente = new Cliente();
//				cliente.setId(1);
//				cliente.setNome("Padaria nossa Alegria");
		
//      Inserindo com o merge objeto gerenciado @GeneratedValue(strategy = GenerationType.IDENTITY)
      Cliente cliente = new Cliente();
//    Atualizando um objeto (que não nasceu gerenciado) com o merge.
      cliente.setId(1);
      
      cliente.setNome("Construtora Medeiros");
				
				entityManager.getTransaction().begin();
				
//		        Inserindo um registro com persist.(que não nasceu gerenciado)
//				entityManager.persist(cliente);
				
//		        Inserindo com o merge objeto gerenciado @GeneratedValue(strategy = GenerationType.IDENTITY)
		        entityManager.merge(cliente);
				
				entityManager.getTransaction().commit();
				
//		        Buscando um registro.
//		        Cliente cliente = entityManager.find(Cliente.class, 1);
//		        System.out.println(cliente.getNome());
				
		entityManager.close();
		entityManagerFactory.close();
    }
}
