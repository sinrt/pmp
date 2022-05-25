package com.pmp.nwms.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class NwmsUser extends org.springframework.security.core.userdetails.User {
    private Long id;
    private String firstName;
    private String lastName;

    public NwmsUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id, String firstName, String lastName) {
        super(username, password, authorities);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public NwmsUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Long id, String firstName, String lastName) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "NwmsUser{" +
            "id=" + id +
            ", username='" + getUsername() + '\'' +
            '}';
    }
}
