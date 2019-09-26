package com.aewinformatica.sistemausuarios;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.aewinformatica.sistemausuarios.dto.UsuarioDTO;
import com.aewinformatica.sistemausuarios.model.Configuracao;
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
//		PassandoParametros(entityManager);
//		FazendoJoins(entityManager);
//		FazendoLeftJoin(entityManager);
//		carregamentoComJoinFetch(entityManager);
//		filtrandoRegistros(entityManager);
//		utilizandoOperadoresLogicos(entityManager);
		utilizandoOperadorIn(entityManager);
		
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
		
		String jpqlDto = "select new com.algaworks.sistemausuarios.dto.UsuarioDTO(id, login, nome)" +
                "from Usuario";
        TypedQuery<UsuarioDTO> typedQueryDto = entityManager.createQuery(jpqlDto, UsuarioDTO.class);
        List<UsuarioDTO> listaDto = typedQueryDto.getResultList();
        listaDto.forEach(u -> System.out.println("DTO: " + u.getId() + ", " + u.getNome()));
		
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
	
	public static void FazendoJoins(EntityManager entityManager) {
		//o uso do inner é facultativo
		String jpql="select u from Usuario u inner join u.dominio d where d.id = 1";
		
		TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
			List<Usuario>listaUsuarios = typedQuery.getResultList();
			
			listaUsuarios.forEach(u->System.out.println(u.getId() + ", " + u.getNome()));
		
	}
	
	public static void FazendoLeftJoin(EntityManager entityManager) {
		
		//o uso do outer é facultativo
		String jpql = "select u, c from Usuario u left outer join u.configuracao c";
		
		TypedQuery<Object[]>typedQuery = entityManager.createQuery(jpql, Object[]	.class);
		
		List<Object[]>lista = typedQuery.getResultList();
		lista.forEach(arr->{
						//fazendo Cast nos objetos
			String out = ((Usuario) arr[0]).getNome();
						 if(arr[1] == null) {
							 out += ", NULL";
						 }
						 else {
							 //fazendo Cast nos objetos
							 out += ", " + ((Configuracao) arr[1]).getId();
						 }
						 System.out.println(out);
		});
		
		
	}
	
	public static void carregamentoComJoinFetch(EntityManager entityManager) {
		
		String jpql = "select u from Usuario u join fetch u.configuracao join fetch u.dominio";
		
		TypedQuery<Usuario>typedQuery = entityManager.createQuery(jpql, Usuario.class);
		List<Usuario> lista = typedQuery.getResultList();
					  lista.forEach(u->System.out.println(u.getId() + ", " + u.getNome()));
					
		
	}
	
	public static void filtrandoRegistros(EntityManager entityManager) {
		
		//TIPOS DE FILTROS : LIKE , IS NULL , IS EMPTY, BETWEEN, >, <, >=, <=, =, <>
		// IS NULL = select u from Usuario u where u.senha is null
		// IS EMPTY = select d from Dominio d where d.usuarios is empty
		
//String jpql = "select u from Usuario u where u.nome like :nomeUsuario";// LIKE sem Concat do JPQL
		String jpql = "select u from Usuario u where u.nome like concat (:nomeUsuario, '%')";
		
		TypedQuery<Usuario>typedQuery = entityManager.createQuery(jpql, Usuario.class).
				setParameter("nomeUsuario", "cal");
//				setParameter("nomeUsuario", "cal%");//sem Concat do JPQL
		
		List<Usuario> lista = typedQuery.getResultList();
		System.out.println("Select com LIKE e concat:");
					  lista.forEach(u->System.out.println(u.getId() + ", " + u.getNome()));
	
		//FILTRO BETWEEN			  
	   String jpqlBet = "select u from Usuario u where u.ultimoAcesso between :ontem and :hoje";
	   TypedQuery<Usuario>typedQueryBet = entityManager.createQuery(jpqlBet,Usuario.class)
               .setParameter("ontem", LocalDateTime.now().minusDays(1))
               .setParameter("hoje", LocalDateTime.now());
	   List<Usuario> listaBet = typedQueryBet.getResultList();
	   System.out.println("Select com Between:");
       listaBet.forEach(ub -> System.out.println(ub.getId() + ", " + ub.getNome()));
	   					
		
	}
	
	public static void utilizandoOperadoresLogicos(EntityManager entityManager) {
		
		//UTILIZANDO >
		String jpql = "select u from Usuario u where u.ultimoAcesso > :ontem and ultimoAcesso < :hoje";
		
		TypedQuery<Usuario>typedQuery = entityManager.createQuery(jpql, Usuario.class)
	                       .setParameter("ontem", LocalDateTime.now().minusDays(1))
	                       .setParameter("hoje", LocalDateTime.now());
		List<Usuario> lista = typedQuery.getResultList();
		System.out.println("Select com > :");
					  lista.forEach(u->System.out.println(u.getId() + ", " + u.getNome()));
					  
					  
		//UTILIZANDO OR e IS NULL
		String jpqlOR = "select u from Usuario u where"
				+ " (u.ultimoAcesso > :ontem and ultimoAcesso < :hoje)"
				+ "or ultimoAcesso is null";
		
		TypedQuery<Usuario>typedQueryOR = entityManager.createQuery(jpqlOR, Usuario.class)
	                       .setParameter("ontem", LocalDateTime.now().minusDays(1))
	                       .setParameter("hoje", LocalDateTime.now());
		List<Usuario> listaOR = typedQueryOR.getResultList();
		 System.out.println("Select com OR e IS NULL:");
					  listaOR.forEach(uo->System.out.println(uo.getId() + ", " + uo.getNome()));
		
	}
	public static void utilizandoOperadorIn(EntityManager entityManager) {
		
		String jpql = "select u from Usuario u where u.id in (:ids)";
		
		TypedQuery<Usuario>typedQuery = entityManager.createQuery(jpql, Usuario.class)
							//passando uma lista de arrays para filtrar o usuario de codigo 1 e 2
						   .setParameter("ids", Arrays.asList(1,2));
		List<Usuario> lista = typedQuery.getResultList();
					  lista.forEach(u->System.out.println(u.getId() + ", " + u.getNome()));
		
	}
}