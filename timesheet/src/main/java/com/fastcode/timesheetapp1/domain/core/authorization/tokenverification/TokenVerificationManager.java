package com.fastcode.timesheetapp1.domain.core.authorization.tokenverification;

import org.springframework.stereotype.Component;
import java.time.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenVerificationManager implements ITokenVerificationManager {

	@NonNull private final ITokenverificationRepository _tokenRepository;
	
	public TokenverificationEntity findByTokenAndType(String token, String tokenType) {
		return  _tokenRepository.findByTokenAndTokenType(token, tokenType);
	}
	
	public TokenverificationEntity save(TokenverificationEntity entity) {
		return  _tokenRepository.save(entity);
	}
	
	public TokenverificationEntity findByUsersIdAndType(Long id, String tokenType) {

		return  _tokenRepository.findByUsersIdAndTokenType(id, tokenType);
	}
	
	public void delete(TokenverificationEntity entity) {
		 _tokenRepository.delete(entity);
	}
	
}

