package com.spring.study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.study.bean.Member;
import com.spring.study.repository.MemberDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("memberService")
public class MemberService implements UserDetailsService {
	@Autowired
	private MemberDao memberDao;
	
	public void test() {
		log.info("test log �ⷰ");
	}

	@Override
	public Member loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("username : " + username);
		
		Member rMember = new Member();
		rMember.setId(username);
		
		Member member = memberDao.getMemberById(rMember);
		
		if (member == null) {
			throw new UsernameNotFoundException("������ ������ ã�� �� ����");
		}
		
		log.info("member : " + member);
		
		return member;
	}
}
