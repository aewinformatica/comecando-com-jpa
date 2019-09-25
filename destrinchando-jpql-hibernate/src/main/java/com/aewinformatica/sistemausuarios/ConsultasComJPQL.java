package com.aewinformatica.sistemausuarios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.aewinformatica.sistemausuarios.model.Dominio;
import com.aewinformatica.sistemausuarios.model.Usuario;


public class ConsultasComJPQL {

	public static void main(String[] args) {
		
		EntityManagerFactory entityManagerFactory = Persistence
					.createEntityManagerFactory("Usuarios-PU");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
//		primeirasConsultas(entityManager);
		EscolhendoORetorno(entityManager);
		
					  entityManager.close();
					  entityManagerFactory.close();
		
	}
	
	public static void primeirasConsultas(EntityManager entityManager) {
		
		String jpql = "select u from Usuario u";
		
		TypedQuery<Usuario>typedQuery = entityManager.createQuery(jpql,Usuario.class);
	List<Usuario> lista =  typedQuery.getResultList();
				  lista.forEach(u-> System.out.println(u.getId() + ", " + u.getNome()));
		
//		Consulta Usando SingleResult		  
	    String jpqlSing = "select u from Usuario u where u.id = 1";
	    
	    TypedQuery<Usuario>typedQuerySing = entityManager.createQuery(jpqlSing,Usuario.class);
	     Usuario usuario = typedQuerySing.getSingleResult();
	     
	     System.out.println(usuario.getId() + " ," + usuario.getNome());
	     
//			Consulta Usando Query do javax.persistence temos que fazer Cast		  
		    String jpqlCast = "select u from Usuario u where u.id = 2";
		    
		    Query query = entityManager.createQuery(jpqlCast);
		     Usuario usuario2 = (Usuario) query.getSingleResult();
		     
		     System.out.println(usuario2.getId() + " ," + usuario2.getNome());
	    					
				
				
				
		
	}
	
	public static void EscolhendoORetorno(EntityManager entityManager) {
		
		String jpql = "select u.dominio from Usuario u where u.id = 1";
		
		TypedQuery<Dominio>typedQuery = entityManager.createQuery(jpql,Dominio.class);
		Dominio dominio =  typedQuery.getSingleResult();
		
				  System.out.println(dominio.getId() + ", " + dominio.getNome());
	}
}
