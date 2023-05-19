package com.site.ssk.account;

import java.util.List;

import com.site.ssk.meeting.Meeting;

import java.time.LocalDateTime;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(unique = true)
    private String accountid;

    @Column(length = 20)
    private String password;

    @Column(length = 20)
    private String name;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
    private List<Meeting> meetingList;
}
