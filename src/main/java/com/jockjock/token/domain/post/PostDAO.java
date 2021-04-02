package com.jockjock.token.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDAO extends JpaRepository<Post, Long>{

	@Modifying
	@Query("UPDATE Post SET content = ?2 WHERE id = ?1")
	int updateContent(Long id, String content);
}
