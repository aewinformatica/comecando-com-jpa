package com.aewinformatica.sistemausuarios;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.aewinformatica.sistemausuarios.dto.UsuarioDTO;
import com.aewinformatica.sistemausuarios.model.Configuracao;
import com.aewinformatica.sistemausuarios.model.Dominio;
import com.aewinformatica.sistemausuarios.model.Usuario;


public class ConsultasComCriteria {

	public static void main(String[] args) {
		
		EntityManagerFactory entityManagerFactory = Persistence
					.createEntityManagerFactory("Usuarios-PU");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
//		primeiraConsulta(entityManager);
//		EscolhendoORetorno(entityManager);
//		FazendoProjecoes(entityManager);
//		PassandoParametros(entityManager);
//		FazendoJoins(entityManager);
//		FazendoLeftJoin(entityManager);
//		carregamentoComJoinFetch(entityManager);
//		filtrandoRegistros(entityManager);
//		utilizandoOperadoresLogicos(entityManager);
//		utilizandoOperadorIn(entityManager);
//		ordenandoResultados(entityManager);
		paginandoResultados(entityManager);
		
		
					  entityManager.close();
					  entityManagerFactory.close();
		
	}
	






    public static void primeiraConsulta(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.select(root);

        TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));

        /*
        String jpql = "select u from Usuario u";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
        */
    }
	
	public static void EscolhendoORetorno(EntityManager entityManager) {
		
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        //retornar um Dominio
        CriteriaQuery<Dominio> criteriaQuery = criteriaBuilder.createQuery(Dominio.class);
        //tabela raiz usuario  onde tem o dominio_id
        Root<Usuario> root = criteriaQuery.from(Usuario.class);
        //dominio e o atributo da Classe usuario que foi mapeada / ideal usar metamodelGen 
        criteriaQuery.select(root.get("dominio"));
        
        //Criando a query tipada com os criterios de retorno
        TypedQuery<Dominio> typedQuery = entityManager.createQuery(criteriaQuery);
        
        //recuperando a lista de dominios
        List<Dominio> lista = typedQuery.getResultList();
        System.out.println("Retornando o Dominio:");
        //imprimindo a lista com codigo e nome dos dominios
        lista.forEach(d -> System.out.println(d.getId() + ", " + d.getNome()));
        
        
        //retornar uma String
        CriteriaQuery<String> criteriaQueryNom = criteriaBuilder.createQuery(String.class);
        //tabela raiz usuario  onde tem o nome
        Root<Usuario> rootNom = criteriaQueryNom.from(Usuario.class);
        //nome e o atributo da Classe usuario que foi mapeada / ideal usar metamodelGen 
        criteriaQueryNom.select(rootNom.get("nome"));
        
        //Criando a query tipada com os criterios de retorno
        TypedQuery<String> typedQueryNom = entityManager.createQuery(criteriaQueryNom);
        
        //recuperando a lista de nomes
        List<String> listaNom = typedQueryNom.getResultList();
        System.out.println("Retornando os Nomes:");
        //imprimindo a lista com codigo e nome dos dominios
        listaNom.forEach(nome -> System.out.println("Nome : " + nome));
        
		
		/*
		String jpql = "select u.dominio from Usuario u where u.id = 1";
		
		TypedQuery<Dominio>typedQuery = entityManager.createQuery(jpql,Dominio.class);
		Dominio dominio =  typedQuery.getSingleResult();
		
				  System.out.println(dominio.getId() + ", " + dominio.getNome());
				  
	    String jpqlNom = "select u.nome from Usuario u";
	    
	    //definindo o tipo de dado retornado String de Nomes e Listando usando ForEach e Lambda
	    TypedQuery<String> typedQueryNom = entityManager.createQuery(jpqlNom,String.class);
	    
	    List<String> ListaNom = typedQueryNom.getResultList();
	    			 ListaNom.forEach(nome->System.out.println(nome));
	    */
	}
	
	public static void FazendoProjecoes(EntityManager entityManager) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		
		//retorna um array de objects -  Object[]
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        //utilizar multiselect
        criteriaQuery.multiselect(root.get("id"),root.get("login"),root.get("nome"));
        
        //validando o tipo da Query
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> listaArr = typedQuery.getResultList();
        System.out.println("Imprimindo lista de Objects[] :");
        listaArr.forEach(arr->System.out.println(String.format("%s, %s, %s", arr)));
        
        //Projeções com DTO
        CriteriaQuery<UsuarioDTO> criteriaQueryDTO = criteriaBuilder.createQuery(UsuarioDTO.class);
        Root<Usuario> rootDTO = criteriaQueryDTO.from(Usuario.class);

        //utilizar select e construct do criateriaBuilder para contruir o DTO com seus atributos
        criteriaQueryDTO.select(criteriaBuilder.construct(UsuarioDTO.class,
        					 rootDTO.get("id"),rootDTO.get("login"),rootDTO.get("nome") ));
        
        //validando o tipo da Query recebendo lista de UsuarioDTO
        TypedQuery<UsuarioDTO> typedQueryDTO = entityManager.createQuery(criteriaQueryDTO);
        List<UsuarioDTO> listaDTO = typedQueryDTO.getResultList();
        System.out.println("Imprimindo lista de UsuariosDTO :");
        listaDTO.forEach(u -> System.out.println("DTO: " + u.getId() + ", " + u.getNome()));

        
        
		/*
		String jpqlArr = "select id, login, nome from Usuario";
		
		TypedQuery<Object[]>typedQueryArr = entityManager.createQuery(jpqlArr,Object[].class);
		List<Object[]>listaArr = typedQueryArr.getResultList();
		
		listaArr.forEach(arr->System.out.println(String.format("%s, %s, %s", arr)));
		
		String jpqlDto = "select new com.algaworks.sistemausuarios.dto.UsuarioDTO(id, login, nome)" +
                "from Usuario";
        TypedQuery<UsuarioDTO> typedQueryDto = entityManager.createQuery(jpqlDto, UsuarioDTO.class);
        List<UsuarioDTO> listaDto = typedQueryDto.getResultList();
        listaDto.forEach(u -> System.out.println("DTO: " + u.getId() + ", " + u.getNome()));*/
		
	}
	
	public static void PassandoParametros(EntityManager entityManager) {
		
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.select(root)
        			 //usando o criteriaBuilder para usar o comparador = e passando o atributo  id da classe usuario e o valor 1
        			 .where(criteriaBuilder.equal(root.get("id"), 1));
        
        //validando o tipo de query criada
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);
        Usuario usuario = typedQuery.getSingleResult();
        					System.out.println("Imprimindo usuario Consultado com paramentro id :");
        					System.out.println(usuario.getId() + ", " + usuario.getNome());
        					
        //passando o parametro login
        CriteriaQuery<Usuario> criteriaQueryLogin = criteriaBuilder.createQuery(Usuario.class);
        Root<Usuario> rootLogin = criteriaQueryLogin.from(Usuario.class);

        criteriaQueryLogin.select(root)
        			 //usando o criteriaBuilder para usar o comparador = e passando o atributo  login da classe usuario e o valor iria
        			 .where(criteriaBuilder.equal(rootLogin.get("login"), "ria"));
        
        //validando o tipo de query criada - criteriaQueryLogin
        TypedQuery<Usuario> typedQueryLogin = entityManager.createQuery(criteriaQueryLogin);
        Usuario usuarioLogin = typedQueryLogin.getSingleResult();
        					System.out.println("Imprimindo usuario Consultado com paramentro login ria :");
        					System.out.println(usuarioLogin.getId() + ", " + usuarioLogin.getNome());

		/*
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
		*/
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
	
	public static void ordenandoResultados(EntityManager entityManager) {
		
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.select(root)
        			 //passando atraves do criteriaBuilder o atributo que vai ser usado para ordenar
        			.orderBy(criteriaBuilder.asc(root.get("nome")));

        TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
		
		/*
		String jpql = "select u from Usuario u order by u.nome";
		
		TypedQuery<Usuario>typedQuery = entityManager.createQuery(jpql, Usuario.class);
		List<Usuario> lista = typedQuery.getResultList();
					  lista.forEach(u->System.out.println(u.getId() + ", " + u.getNome()));
					
		*/
	}
	
	public static void paginandoResultados(EntityManager entityManager) {
		
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.select(root);
        //no retorno do Typed query que se tem como paginar
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery)
							  //qual e o primeiro registro
							  .setFirstResult(0) //PRIMEIRO = (PAGINA -1) * QTDE_POR_PAG
							  //mostrar de 2 e 2
							  .setMaxResults(2);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
        
		/*
		String jpql = "select u from Usuario u";
		
		TypedQuery<Usuario>typedQuery = entityManager.createQuery(jpql, Usuario.class)
						  //qual e o primeiro registro
						  .setFirstResult(0)
						  //mostrar de 2 e 2
						  .setMaxResults(2);
		List<Usuario> lista = typedQuery.getResultList();
					  lista.forEach(u->System.out.println(u.getId() + ", " + u.getNome()));
		*/			
		
	}
}