package nl.gerimedica.repository;

import nl.gerimedica.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record,Long> {
}
