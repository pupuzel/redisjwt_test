package com.jockjock.token.domain.post;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "T_POST")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post implements Serializable{

	private static final long serialVersionUID = -1354270848012866727L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "varchar(30)", nullable = false)
	private String user_id;
	
	@Column(columnDefinition = "varchar(40)", nullable = false)
	private String title;
	
	@Setter
	@Column(columnDefinition = "varchar(1000)", nullable = false)
	private String content;
	
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime create_date;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime update_date;
    
    @Builder
    public Post(String user_id, String title, String content) {
    	this.user_id=user_id;
    	this.title=title;
    	this.content=content;
    }

}
