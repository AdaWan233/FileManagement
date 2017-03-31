package com.adawan.api.rest.v1.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MetadataRepository extends JpaRepository<Metadata, Long> {
    @Query("from Metadata m where m.id=:id")
    Metadata getMetaDataById(@Param("id") String id);

    @Query("from Metadata m where m.author=:author")
    List<Metadata> getMetaDatasByAuthor(@Param("author") String author);

}
