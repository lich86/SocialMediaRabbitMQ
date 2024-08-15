package com.chervonnaya.publicationsservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "com_id_seq")
    @SequenceGenerator(name = "com_id_seq", sequenceName = "com_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "time_created", updatable = false)
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime timeCreated;

    @Column(name = "time_updated")
    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime timeUpdated;

    @ManyToOne
    @JoinColumn(name = "publication_id", referencedColumnName = "id")
    @JsonIgnoreProperties("comments")
    private Publication publication;

    @Column(name = "text")
    private String text;

}
