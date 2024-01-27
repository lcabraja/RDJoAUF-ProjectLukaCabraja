package hr.lcabraja.dynasty8.service;

import hr.lcabraja.dynasty8.domain.LogEntry;
import hr.lcabraja.dynasty8.repository.LogEntryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogEntryServiceImpl implements LogEntryService {

    private final LogEntryRepository logEntryRepository;

    @Override
    public LogEntry saveLog(LogEntry logEntry) {
        return logEntryRepository.save(logEntry);
    }
}
