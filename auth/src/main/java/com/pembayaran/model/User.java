package com.pembayaran.model;

import com.pembayaran.dto.RegisDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User implements Serializable {

    private static final long serializeVersioUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "url_image")
    private String urlImage;

    @Column(name = "roles")
    private String roles;

    @Column(name = "active")
    private Boolean active;

    public User(RegisDto regis){
        this.email = regis.getEmail();
        this.firstName = regis.getFirstName();
        this.lastName = regis.getLastName();
    }

    public Map getJsonForProfile(){
        Map map = new HashMap<>();
        map.put("email", getEmail());
        map.put("first_name", getFirstName());
        map.put("last_name", getLastName());
        map.put("profile_image", getUrlImage());
        return map;
    }
}
