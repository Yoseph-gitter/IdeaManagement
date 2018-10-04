package com.codingDojo.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingDojo.mvc.models.Idea;

@Repository
public interface IdeaRepo extends CrudRepository<Idea, Long> {

	
	List<Idea> findAll();
	
	@Query(value="SELECT creator_id FROM ideas idea "
			+ "WHERE idea.id = ?1" , nativeQuery=true )
	Long findTaskCreatorById(Long id);
	
	@Query(value="SELECT * FROM ideas  ORDER BY  content ASC ",  nativeQuery=true)
	List<Idea> findAllIdeasAscending();
	
	@Query(value="SELECT * FROM ideas  ORDER BY  content DESC", nativeQuery=true)
	List<Idea> findAllIdeasDescending();
}



