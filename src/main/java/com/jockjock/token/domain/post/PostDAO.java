package com.jockjock.token.domain.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDAO extends JpaRepository<Post, Long>{
	/**
	 * @Query 사용시 JPQL 문법으로 SQL을 작성해야된다.
	 * 엔티티 중심 쿼리라서 실제 SQL과는 다르고 테이블 대신 앤티티 클래스 명을 지정
	 * 
	 * nativeQuery = true 지정시 네이티브 SQL 쿼리로 작성할 수 있다.
	 * 
	 * @Modifying 선언시 delete update 쿼리 사용
	 * */
	
	
	@Modifying
	@Query("UPDATE Post p SET p.content = ?2 WHERE p.id = ?1")
	int updateContent(Long id, String content);
	
	@Modifying
	@Query(value="update Post p set p.title = :#{#vo.title} WHERE p.id = :#{#vo.id}", nativeQuery=false)
	int updateTitle(@Param("vo") Post post );
	
	@Query("select p from Post p")
	List<Post> selectList();
	
	@Query(value = "select * from t_post p", nativeQuery = true)
	List<Post> selectListNative();
}