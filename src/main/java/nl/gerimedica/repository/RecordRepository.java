package nl.gerimedica.repository;

import nl.gerimedica.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record,Long> {

    @Query("select record from Record record where record.code = ?1")
    Optional<Record> findRecordByCode(String code);
}
