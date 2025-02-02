package com.pembayaran.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "history_transaction")
public class HistoryTransaction implements Serializable {

    private static final long serializeVersioUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "service_provide_id")
    private Long serviceProvideId;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "date_transaction")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTransaction;

    @Column(name = "invoice")
    private String invoice;

    @Column(name = "service_provide_name")
    private String serviceProvideName;

    public Map getJsonForInvoice(){
        Map map = new HashMap<>();
        map.put("invoice_number", getInvoice());
        map.put("transaction_type", getTransactionType());
        map.put("total_amount", getTotalAmount());
        if (getTransactionType().equalsIgnoreCase("TOP_UP")){
            map.put("description","Top Up balance");
        }else {
            map.put("description", getServiceProvideName());
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        map.put("created_on",format.format(getDateTransaction()));
        return map;
    }

}
