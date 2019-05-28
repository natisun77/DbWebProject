package com.nataliia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable (name="good_order", joinColumns ={@JoinColumn(name= "order_id", nullable = false, updatable = false)},
            inverseJoinColumns ={@JoinColumn (name = "good_id", nullable = false,updatable = false)} )
    private List<Good> cart;

    @OneToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public Order() {

    }

    public Order(List<Good> cart, User user) {
        this.cart = cart;
        this.user = user;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Good> getCart() {
        return cart;
    }

    public void setCart(List<Good> cart) {
        this.cart = cart;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        return cart != null ? cart.equals(order.cart) : order.cart == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (cart != null ? cart.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cart=" + cart +
                '}';
    }
}
