package com.idle.kb_i_dle_backend.domain.finance.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="stock_list_price" , catalog="product")
public class StockProductPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer index;

    @Column(name = "standardCode")
    private String standardCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    @Column(name = "1m_b_price")
    private Integer oneMonthAgoPrice;

    @Column(name = "2m_b_price")
    private Integer twoMonthsAgoPrice;

    @Column(name = "3m_b_price")
    private Integer threeMonthsAgoPrice;

    @Column(name = "4m_b_price")
    private Integer fourMonthsAgoPrice;

    @Column(name = "5m_b_price")
    private Integer fiveMonthsAgoPrice;

    @Column(name = "6m_b_price")
    private Integer sixMonthsAgoPrice;

    @Column(name = "short_code")
    private String shortCode;
}
