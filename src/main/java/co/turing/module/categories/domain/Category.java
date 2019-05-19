package co.turing.module.categories.domain;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int categoryId;
    @Column(name = "department_id")
    private int departmentId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

}
