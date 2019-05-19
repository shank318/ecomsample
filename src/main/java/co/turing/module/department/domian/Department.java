package co.turing.module.department.domian;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    int departmentId;
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;
}
