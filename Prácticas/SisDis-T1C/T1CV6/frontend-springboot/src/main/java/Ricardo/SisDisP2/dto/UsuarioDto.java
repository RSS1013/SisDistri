package Ricardo.SisDisP2.dto;

import java.util.Set;

public class UsuarioDto {
    private Long id;
    private String username;
    private String password;
    private Set<Long> roles; // IDs de roles

    public UsuarioDto() {}

    public UsuarioDto(String username, String password, Set<Long> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Long> getRoles() {
        return roles;
    }

    public void setRoles(Set<Long> roles) {
        this.roles = roles;
    }
}

