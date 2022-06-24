package web.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import web.domain.member.MemberEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class LoginDto implements UserDetails { // 로그인 세션에 넣을 DTO
    private int mno; // 회원번호
    private String mid; // 회원아이디
    private String mpassword; // 회원비밀번호
    private String mname; //회원 이름

    private final Set<GrantedAuthority> authorities; // 인증

    public LoginDto(MemberEntity memberEntity, Collection<? extends GrantedAuthority> authorityList) {
        this.mno = memberEntity.getMno();
        this.mid = memberEntity.getMid();
        this.mpassword = memberEntity.getMpassword();
        this.mname = memberEntity.getMname();
        this.authorities = Collections.unmodifiableSet(new LinkedHashSet<>(authorityList));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.mpassword;
    }

    @Override
    public String getUsername() {
        return this.mid;
    }

    //계정 인증 만료 여부 확인. true 는 만료되지 않음
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 잠겨있는지 여부 확인. true 는 잠겨있지 않음
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호가 만료되었는지 여부 확인. true 는 만료되지 않음
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정 사용 가능한지 여부 확인. true 는 잠겨있지 않음
    @Override
    public boolean isEnabled() {
        return true;
    }
}
