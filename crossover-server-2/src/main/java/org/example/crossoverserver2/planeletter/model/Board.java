package org.example.crossoverserver2.planeletter.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "board")
public class Board extends BaseEntity{

    @Setter
    @Column(length = 20, nullable = false)// 길이는 20자 이하이고, 비어있을 수 없다.
    private String title;

    @Setter
    @Column(length = 140, nullable = false)// 길이는 140자 이하이고, 비어있을 수 없다.
    private String content;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Comment> comments;

}
