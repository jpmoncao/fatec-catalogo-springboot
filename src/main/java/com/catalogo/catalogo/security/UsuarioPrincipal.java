package com.catalogo.catalogo.security;

import com.catalogo.catalogo.models.UsuarioModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioPrincipal implements UserDetails {

    private final Long idUsuario;
    private final String username;
    private final String password;
    private final String nomeCompleto;
    private final Collection<? extends GrantedAuthority> authorities;

    public UsuarioPrincipal(
            Long idUsuario,
            String username,
            String password,
            String nomeCompleto,
            Collection<? extends GrantedAuthority> authorities) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.nomeCompleto = nomeCompleto;
        this.authorities = authorities;
    }

    public static UsuarioPrincipal from(UsuarioModel usuario) {
        var role = "ROLE_" + usuario.getPerfil();
        return new UsuarioPrincipal(
                usuario.getIdUsuario(),
                usuario.getUsername(),
                usuario.getSenha(),
                usuario.getNomeCompleto(),
                List.of(new SimpleGrantedAuthority(role)));
    }

    public String getNomeExibicao() {
        if (nomeCompleto != null && !nomeCompleto.isBlank()) {
            return nomeCompleto.trim();
        }
        return username;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
