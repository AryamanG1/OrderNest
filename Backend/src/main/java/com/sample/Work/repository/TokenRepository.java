package com.sample.Work.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sample.Work.model.TokenEntity;

public interface TokenRepository extends JpaRepository<TokenEntity,Long>{

	@Query("""
	        SELECT t 
	        FROM TokenEntity t 
	        WHERE t.user.userId = :userId
	        """)
	List<TokenEntity> findAllTokenByUser(@Param("userId")Long uuid);
	
	Optional<TokenEntity> findByToken(String token);
	
	
}
