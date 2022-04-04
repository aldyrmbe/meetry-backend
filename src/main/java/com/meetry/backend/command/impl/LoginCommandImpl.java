package com.meetry.backend.command.impl;

import com.meetry.backend.command.LoginCommand;
import com.meetry.backend.command.model.LoginCommandRequest;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.user.AccountOfficer;
import com.meetry.backend.entity.user.ERIC;
import com.meetry.backend.entity.user.Mitra;
import com.meetry.backend.entity.user.Peneliti;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.repository.user.AccountOfficerRepository;
import com.meetry.backend.repository.user.ERICRepository;
import com.meetry.backend.repository.user.MitraRepository;
import com.meetry.backend.repository.user.PenelitiRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.response.LoginWebResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
@AllArgsConstructor
public class LoginCommandImpl implements LoginCommand {

    private final AuthHelper authHelper;
    private final ERICRepository ericRepository;
    private final AccountOfficerRepository accountOfficerRepository;
    private final PenelitiRepository penelitiRepository;
    private final MitraRepository mitraRepository;

    @Override
    public LoginWebResponse execute(LoginCommandRequest loginCommandRequest) {

        Optional<ERIC> eric = ericRepository.findByEmail(loginCommandRequest.getEmail());
        Optional<AccountOfficer> accountOfficer = accountOfficerRepository.findByEmail(loginCommandRequest.getEmail());
        Optional<Peneliti> peneliti = penelitiRepository.findByEmail(loginCommandRequest.getEmail());
        Optional<Mitra> mitra = mitraRepository.findByEmail(loginCommandRequest.getEmail());

        if(eric.isPresent()){
            return getERIC(loginCommandRequest, eric.get());
        } else if(accountOfficer.isPresent()){
            return getAccountOfficer(loginCommandRequest, accountOfficer.get());
        } else if(peneliti.isPresent()){
            return getPeneliti(loginCommandRequest, peneliti.get());
        } else if(mitra.isPresent()){
            return getMitra(loginCommandRequest, mitra.get());
        } else {
            throw new BaseException("Akun tidak ditemukan.");
        }

    }

    private void validatePassword(String password, String encryptedPassword){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isPasswordMatches = passwordEncoder.matches(password, encryptedPassword);
        if(!isPasswordMatches) throw new BaseException("Password salah.");
    }

    private LoginWebResponse getERIC(LoginCommandRequest loginCommandRequest, ERIC eric){

        validatePassword(loginCommandRequest.getPassword(), eric.getPassword());
        return toLoginWebResponse(
            eric.getId(),
            eric.getRole(),
            loginCommandRequest.getHttpServletResponse()
        );
    }

    private LoginWebResponse getAccountOfficer(LoginCommandRequest loginCommandRequest, AccountOfficer accountOfficer){

        validatePassword(loginCommandRequest.getPassword(), accountOfficer.getPassword());
        return toLoginWebResponse(
            accountOfficer.getId(),
            accountOfficer.getRole(),
            loginCommandRequest.getHttpServletResponse()
        );
    }

    private LoginWebResponse getMitra(LoginCommandRequest loginCommandRequest, Mitra mitra){

        validatePassword(loginCommandRequest.getPassword(), mitra.getPassword());
        return toLoginWebResponse(
            mitra.getId(),
            mitra.getRole(),
            loginCommandRequest.getHttpServletResponse()
        );
    }

    private LoginWebResponse getPeneliti(LoginCommandRequest loginCommandRequest, Peneliti peneliti){

        validatePassword(loginCommandRequest.getPassword(), peneliti.getPassword());
        return toLoginWebResponse(
            peneliti.getId(),
            peneliti.getRole(),
            loginCommandRequest.getHttpServletResponse()
        );
    }

    private LoginWebResponse toLoginWebResponse(String id, Role role, HttpServletResponse httpServletResponse){

        authHelper.setSessionCookie(httpServletResponse, id, role);
        return LoginWebResponse.builder()
            .id(id)
            .role(role)
            .build();
    }


}
