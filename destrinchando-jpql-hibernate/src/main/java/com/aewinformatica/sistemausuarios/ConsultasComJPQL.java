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
//		EscolhendoORetorno(entityManager);
//		FazendoProjecoes(entityManager);
		PassandoParametros(entityManager);
		
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
				  
	    String jpqlNom = "select u.nome from Usuario u";
	    
	    //definindo o tipo de dado retornado String de Nomes e Listando usando ForEach e Lambda
	    TypedQuery<String> typedQueryNom = entityManager.createQuery(jpqlNom,String.class);
	    
	    List<String> ListaNom = typedQueryNom.getResultList();
	    
	   ListaNom.forEach(nome->System.out.println(nome));
	}
	
	public static void FazendoProjecoes(EntityManager entityManager) {
		
		String jpqlArr = "select id, login, nome from Usuario";
		
		TypedQuery<Object[]>typedQueryArr = entityManager.createQuery(jpqlArr,Object[].class);
		List<Object[]>listaArr = typedQueryArr.getResultList();
		
		listaArr.forEach(arr->System.out.println(String.format("%s, %s, %s", arr)));
	}
	
	public static void PassandoParametros(EntityManager entityManager) {
		
		//parametro IdUsuario
		String jpql = "select u from Usuario u where u.id = :idUsuario";
		
		//encavalando os metodos
		TypedQuery<Usuario>typedQuery = entityManager.
				createQuery(jpql,Usuario.class)
				//nome do paramentro dentro da JPQL	
				.setParameter("idUsuario", 1);
					Usuario usuario = typedQuery.getSingleResult();
					
	   System.out.println(usuario.getId() + ", " + usuario.getNome());
	   
	   
		//parametro loginUsuario
		String jpqlLog = "select u from Usuario u where u.login = :loginUsuario";
		
		//encavalando os metodos
		TypedQuery<Usuario>typedQueryLog = entityManager.
				createQuery(jpqlLog,Usuario.class)
				//nome do paramentro dentro da JPQL	
				.setParameter("loginUsuario", "ria");
					Usuario usuarioLog = typedQueryLog.getSingleResult();
					
	   System.out.println(usuarioLog.getId() + ", " + usuarioLog.getNome());
		
	}
}
