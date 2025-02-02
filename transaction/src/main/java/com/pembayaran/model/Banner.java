package com.pembayaran.model;

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
@Table(name = "banner")
public class Banner implements Serializable {

    private static final long serializeVersioUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url_image")
    private String urlImage;

    @Column(name = "information")
    private String information;

    public Map getJsonForList(){
        Map map = new HashMap<>();
        map.put("id", getId());
        map.put("banner_name", getName());
        map.put("banner_image", getUrlImage());
        map.put("description", getInformation());

        return map;
    }
}
