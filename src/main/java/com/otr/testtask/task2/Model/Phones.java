package com.otr.testtask.task2.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "phones")
public class Phones {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "num")
    private String num;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user = new Users();

    public Phones() { }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phones phones = (Phones) o;
        return Objects.equals(id, phones.id) &&
                Objects.equals(num, phones.num) &&
                Objects.equals(user, phones.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, num, user);
    }
}
