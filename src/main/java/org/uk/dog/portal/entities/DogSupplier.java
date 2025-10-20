package org.uk.dog.portal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "suppliers")
public class DogSupplier implements Serializable {

    public DogSupplier() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "dogSupplier", cascade = CascadeType.ALL)
    private List<Dog> dogList = new ArrayList<>();

    public List<Dog> getDogList() {
        return dogList;
    }

    public void setDogList(List<Dog> dogList) {
        this.dogList = dogList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}