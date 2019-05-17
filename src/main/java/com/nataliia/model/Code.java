package com.nataliia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;

@Entity
@Table(name = "confirmation_code")
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "value_code")
    private String value;

    @Column(name = "user_Id")
    private Long userId;

    @Column(name = "good_Id")
    private Long goodId;

    @Column(name = "creation_date")
    LocalTime creationDate;


    public Code() {
    }

    public Code(String value, Long userId, Long goodId) {
        this.value = value;
        this.userId = userId;
        this.goodId = goodId;
        creationDate = LocalTime.now();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Code code = (Code) o;

        if (value != code.value) return false;
        if (userId != null ? !userId.equals(code.userId) : code.userId != null) return false;
        return goodId != null ? goodId.equals(code.goodId) : code.goodId == null;
    }


    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (goodId != null ? goodId.hashCode() : 0);
        return result;
    }
}
