package hayashi.userservice.config.security;

import hayashi.userservice.domain.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@ToString
@Getter
@AllArgsConstructor(staticName = "of")
public class AuthUser implements UserDetails {

    private String id;
    private String email;
    private String password;
    private String name;
    Collection<? extends GrantedAuthority> authorities;

    public static AuthUser from(UserEntity user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        GrantedAuthority authority = new SimpleGrantedAuthority("USER");
        authorities.add(authority);

        return AuthUser.of(user.getId(), user.getEmail(), user.getPassword(), user.getName(), authorities);

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream().toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
