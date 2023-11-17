package de.ait.tp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @ManyToOne
    @JoinColumn(name = "test_id")
    @JsonIgnore
    private Test test;
    @Column(name = "score")
    private int score;
    @Column(name = "date", nullable = false, length = 10)
    private LocalDate date;
    @Column(name= " progress_percent", nullable = false)
    private double progressPercent;

}
