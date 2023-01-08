package com.cetinkayayusuf.itemstocks.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "item_stocks")
@NoArgsConstructor
@AllArgsConstructor
public class ItemStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private long id;

    @NonNull
    @Column(name = "user_id")
    private long userId;

    @NonNull
    @ManyToOne()
    @JoinColumn(name = "item_id")
    private Item item;

    @NonNull
    @Column(name = "amount")
    private int amount;

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
}
