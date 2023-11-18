package de.ait.tp.models;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TestTotalResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "total_score")
    private int totalCorrectAnswer;
    @Column(name = "test_taken")
    private int testTaken;

}
