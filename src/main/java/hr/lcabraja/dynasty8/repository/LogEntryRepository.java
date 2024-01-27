package hr.lcabraja.dynasty8.repository;

import hr.lcabraja.dynasty8.domain.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
}
