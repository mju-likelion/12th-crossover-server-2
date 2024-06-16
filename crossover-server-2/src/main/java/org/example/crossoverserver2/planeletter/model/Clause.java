package org.example.crossoverserver2.planeletter.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "clause")
public class Clause extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, unique = true)
    private String content;

    @OneToMany(mappedBy = "clause", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserClause> userClause;
}
