package com.aewinformatica.sistemaseguranca;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import com.aewinformatica.sistemaseguranca.model.Grupo;
import com.aewinformatica.sistemaseguranca.model.Grupo_;
import com.aewinformatica.sistemaseguranca.model.Permissao;
import com.aewinformatica.sistemaseguranca.model.Permissao_;
import com.aewinformatica.sistemaseguranca.model.Usuario;
import com.aewinformatica.sistemaseguranca.model.Usuario_;



public class SistemaSeguranca {

	public static void main(String[] args) {
		
		EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory("Usuarios-PU");
	
	    EntityManager entityManager = entityManagerFactory.createEntityManager();
	    
	    Usuario usuario = new Usuario();
	    		usuario.setCodigo(1L);
	    		
	    consultarPermissoes(entityManager,usuario);

	
	}

	private static void consultarPermissoes(EntityManager entityManager,Usuario usuario) {
		
		//construtor de criteria
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		
		//construindo os criterios da pesquisa
		CriteriaQuery<String> criteria= criteriaBuilder.createQuery(String.class).distinct(true);
		
		Root<Usuario>root =  criteria.from(Usuario.class);
		Join<Usuario,Grupo>grupos =  root.join(Usuario_.grupos);
		Join<Grupo,Permissao>permissoes = grupos.join(Grupo_.permissoes);
		
//		List<Predicate> predicates = new ArrayList<Predicate>();
//						predicates.add(criteriaBuilder.equal(root.get(Usuario_.codigo), usuario.getCodigo()));
		 
				
		TypedQuery<String> typedQuery = entityManager.createQuery(
				criteria
				.multiselect(permissoes.get(Permissao_.nome))
				.where(criteriaBuilder.equal(root.get(Usuario_.codigo), usuario.getCodigo()))
				);

		/*
		List<String> permissoes = entityManager.createQuery(
				"select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario", String.class)
				.setParameter("usuario", usuario)
				.getResultList();
		*/
		System.out.println("Imprimindo lista de Permissoes :");		
//		typedQuery.getResultList().forEach(System.out::println);
		List<String> listaPermissoes = typedQuery.getResultList();
		
		listaPermissoes.forEach(p->System.out.println(p));
		
	}

}
