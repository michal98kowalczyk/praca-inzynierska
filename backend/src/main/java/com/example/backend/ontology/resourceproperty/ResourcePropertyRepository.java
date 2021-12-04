package com.example.backend.ontology.resourceproperty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourcePropertyRepository extends JpaRepository<ResourceProperty,Long> {
    List<ResourceProperty> findAllByResourceId(Long resourceId);

    List<ResourceProperty> findAllByKey(String key);
    List<ResourceProperty> findAllByValue(String value);
}
