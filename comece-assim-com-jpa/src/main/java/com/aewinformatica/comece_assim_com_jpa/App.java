package com.aewinformatica.comece_assim_com_jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	EntityManagerFactory entityManagerFactory = Persistence
    			.createEntityManagerFactory("Cliente-PU");
    	EntityManager entityManager = entityManagerFactory.createEntityManager(); 
       System.out.println( "Hello World!" );
    	 entityManager.close();
         entityManagerFactory.close();
    }
}
