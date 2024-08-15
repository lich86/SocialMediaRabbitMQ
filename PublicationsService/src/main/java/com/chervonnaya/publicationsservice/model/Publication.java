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
@Table(name = "publications")
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pub_id_seq")
    @SequenceGenerator(name = "pub_id_seq", sequenceName = "pub_id_seq", allocationSize = 1)
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

    @Column(name = "text")
    private String text;

    @OneToMany(mappedBy = "publication")
    @JsonIgnoreProperties("publication")
    private Set<Comment> comments;

    @ElementCollection
    @Column(name = "subscriber_id")
    private List<Long> likes;

}
