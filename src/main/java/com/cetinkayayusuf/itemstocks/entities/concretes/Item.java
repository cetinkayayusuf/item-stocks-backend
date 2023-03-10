package com.cetinkayayusuf.itemstocks.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(name = "items")
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private long id;

    @NonNull
    @NotBlank
    @Size(max = 50)
    @Column(name = "code")
    private String code;

    @NonNull
    @NotBlank
    @Size(max = 50)
    @Column(name = "name")
    private String name;

    @NonNull
    @NotBlank
    @Size(max = 120)
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ItemStock> stocks;
}
