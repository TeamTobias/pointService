package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Mypoint;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Mypoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MypointRepository extends MongoRepository<Mypoint, String> {
    Iterable<Mypoint> findByUserid(String userid);
}
