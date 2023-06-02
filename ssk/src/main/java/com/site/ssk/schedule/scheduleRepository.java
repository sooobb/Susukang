package com.site.ssk.schedule;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.site.ssk.account.Account;
import com.site.ssk.meeting.Meeting;

public interface scheduleRepository extends JpaRepository<schedule, Integer> {
	List<schedule> findByAccountId(String accountid);
	List<schedule> findByDateLike(String date);
	Optional<schedule> findById(String id);
}
