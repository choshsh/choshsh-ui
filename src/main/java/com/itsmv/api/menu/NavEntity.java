package com.itsmv.api.menu;

import com.itsmv.config.BaseDateEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "nav", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "url"}))
public class NavEntity extends BaseDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String _name;

    @Column(nullable = false)

    private String name;

    @Column(name = "url", nullable = false)
    private String to;

    @Column(nullable = false)
    private String icon;
}