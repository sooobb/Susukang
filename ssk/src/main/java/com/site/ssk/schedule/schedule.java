package com.site.ssk.schedule;

import java.time.LocalDateTime;

import com.site.ssk.account.Account;
import com.site.ssk.meeting.Meeting;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity 
public class schedule {
	@Id
    private Integer id;
	
	@Column(length = 20)
	private String title;
	
    @Column(columnDefinition = "TEXT")
    private String time;	// 시간
    	
    @Column(columnDefinition = "TEXT")
    private String date;	// 년 월 일

    @ManyToOne
    private Account account;

	public void patch(schedule schedule) {
		if(schedule.title!=null)
			this.title = schedule.title;
		if(schedule.time!=null)
			this.time = schedule.time;
		if(schedule.date!=null)
			this.date = schedule.date;
	}
	
}
