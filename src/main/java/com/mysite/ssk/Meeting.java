package com.mysite.ssk;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Meeting {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(length = 200)
    private String title;	// 회의제목
	
	private LocalDateTime date;	// 날짜
	
	// columnDefinition은 컬럼의 속성을 정의할 떄 사용한다. columnDefinition = "TEXT"은 "내용"처럼 글자 수를 제한할 수 없는 경우 사용한다.
	@Column(columnDefinition = "TEXT")
	private String data;	// 데이터(회의내용)
	
	@Column(columnDefinition = "TEXT")
	private String summary_data;	// 요약데이터
	
	// 회의는 한명의 사용자에게 여러개가 저장될 수 있는 구조이다. 따라서 회의록은 Many(많은 것)가 되고 사용자는 One(하나)이다
	@ManyToOne
	private Users user;	// 어떤 사용자의 회의록인지
	

}
