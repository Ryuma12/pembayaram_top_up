package com.pembayaran.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "service_provide")
public class ServiceProvide implements Serializable {

    private static final long serializeVersioUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "url_icon")
    private String urlIcon;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "active")
    private Boolean active;


    public Map getJsonForList(){
        Map map = new HashMap<>();
        map.put("id", getId());
        map.put("service_code", getCode());
        map.put("service_name", getName());
        map.put("service_icon", getUrlIcon());
        map.put("service_tariff", getPrice());

        return map;
    }
}
