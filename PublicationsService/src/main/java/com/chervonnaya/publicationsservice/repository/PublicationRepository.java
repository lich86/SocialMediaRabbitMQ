package com.chervonnaya.publicationsservice.repository;

import com.chervonnaya.publicationsservice.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
