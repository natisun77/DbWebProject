package com.nataliia.model;

public class Code {

    private String value;
    private Long userId;
    private Long goodId;

    public Code(String value, Long userId, Long goodId) {
        this.value = value;
        this.userId = userId;
        this.goodId = goodId;
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
