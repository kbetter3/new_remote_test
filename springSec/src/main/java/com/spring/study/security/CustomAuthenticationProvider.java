package com.spring.study.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.spring.study.bean.Member;
import com.spring.study.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private MemberService memberService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String id = authentication.getName();
		String pw = (String) authentication.getCredentials();
		
		log.info("memberService : " + memberService);
		
		Member member = null;
		try {
			member = memberService.loadUserByUsername(id);
			
			log.info("member : " + member);
			
			if (!pw.equals(member.getPassword())) {
				throw new BadCredentialsException("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			}
		} catch (UsernameNotFoundException e) {
			log.info(e.toString());
			throw new UsernameNotFoundException(e.getMessage());
		} catch (BadCredentialsException e) {
			log.info(e.toString());
			throw new BadCredentialsException(e.getMessage());
		} catch (Exception e) {
			log.info(e.toString());
			throw new RuntimeException(e.getMessage());
		}
		
		return new UsernamePasswordAuthenticationToken(member.getId(), member.getPw(), member.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}