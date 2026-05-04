package com.internship.tool.config;

import com.internship.tool.entity.RiskRegister;
import com.internship.tool.repository.RiskRegisterRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RiskRegisterDataSeeder implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(RiskRegisterDataSeeder.class);

    private final RiskRegisterRepository riskRegisterRepository;
    private final boolean seedEnabled;
    private final int targetRiskCount;

    public RiskRegisterDataSeeder(
        RiskRegisterRepository riskRegisterRepository,
        @Value("${app.seed.enabled:true}") boolean seedEnabled,
        @Value("${app.seed.target-risk-count:30}") int targetRiskCount
    ) {
        this.riskRegisterRepository = riskRegisterRepository;
        this.seedEnabled = seedEnabled;
        this.targetRiskCount = targetRiskCount;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!seedEnabled) {
            LOGGER.info("Risk register seed is disabled.");
            return;
        }

        List<RiskRegister> seedRecords = buildSeedRecords();
        Set<String> existingRiskCodes = riskRegisterRepository.findAll()
            .stream()
            .map(RiskRegister::getRiskCode)
            .collect(Collectors.toCollection(LinkedHashSet::new));

        if (existingRiskCodes.size() >= targetRiskCount) {
            LOGGER.info("Risk register seed skipped because {} records already exist.", existingRiskCodes.size());
            return;
        }

        int recordsNeeded = Math.min(targetRiskCount, seedRecords.size()) - existingRiskCodes.size();
        if (recordsNeeded <= 0) {
            LOGGER.info("Risk register seed skipped because no additional records are needed.");
            return;
        }

        List<RiskRegister> missingRecords = seedRecords.stream()
            .filter(record -> !existingRiskCodes.contains(record.getRiskCode()))
            .limit(recordsNeeded)
            .toList();

        if (missingRecords.isEmpty()) {
            LOGGER.info("Risk register seed skipped because all seed records already exist.");
            return;
        }

        riskRegisterRepository.saveAll(missingRecords);
        LOGGER.info("Seeded {} risk register records.", missingRecords.size());
    }

    private List<RiskRegister> buildSeedRecords() {
        List<RiskRegister> records = new ArrayList<>();

        records.add(createRisk("RISK-001", "Import file schema mismatch", "Data", "Open", "High", "High", "Medium", "Anagha", "anagha@example.com", 2, "CSV Import"));
        records.add(createRisk("RISK-002", "Duplicate row ingestion", "Data", "Open", "Medium", "Medium", "High", "Rohan", "rohan@example.com", 4, "Bulk Upload"));
        records.add(createRisk("RISK-003", "JWT token expiry confusion", "Security", "Mitigated", "Medium", "Medium", "Medium", "Priya", "priya@example.com", 6, "Authentication"));
        records.add(createRisk("RISK-004", "Redis cache stale data", "Infrastructure", "Open", "High", "High", "Low", "Aman", "aman@example.com", 3, "Caching"));
        records.add(createRisk("RISK-005", "Flyway migration ordering issue", "Database", "Open", "High", "High", "Low", "Neha", "neha@example.com", 8, "Database"));
        records.add(createRisk("RISK-006", "Notification emails marked as spam", "Communication", "Open", "Medium", "Medium", "Medium", "Kiran", "kiran@example.com", 9, "Email Service"));
        records.add(createRisk("RISK-007", "Risk owner email typo", "Operations", "Closed", "Low", "Low", "Medium", "Divya", "divya@example.com", 1, "Manual Entry"));
        records.add(createRisk("RISK-008", "Large XLSX import timeout", "Performance", "Open", "High", "High", "High", "Arjun", "arjun@example.com", 5, "Excel Import"));
        records.add(createRisk("RISK-009", "Invalid status mapping from source", "Data", "Open", "Medium", "Medium", "Medium", "Meera", "meera@example.com", 7, "ETL"));
        records.add(createRisk("RISK-010", "Role-based access misconfiguration", "Security", "Mitigated", "High", "High", "Low", "Rahul", "rahul@example.com", 10, "Authorization"));
        records.add(createRisk("RISK-011", "Missing audit timestamps", "Compliance", "Closed", "Medium", "Low", "Medium", "Sneha", "sneha@example.com", 2, "API Layer"));
        records.add(createRisk("RISK-012", "API validation message inconsistency", "UX", "Open", "Low", "Low", "High", "Isha", "isha@example.com", 3, "Validation"));
        records.add(createRisk("RISK-013", "Partial import rollback failure", "Database", "Open", "High", "High", "Medium", "Vikram", "vikram@example.com", 4, "Transaction Manager"));
        records.add(createRisk("RISK-014", "CSV encoding issue", "Data", "Mitigated", "Medium", "Medium", "High", "Shreya", "shreya@example.com", 6, "CSV Import"));
        records.add(createRisk("RISK-015", "Inactive risk still searchable", "Logic", "Closed", "Low", "Low", "Low", "Nikhil", "nikhil@example.com", 1, "Search"));
        records.add(createRisk("RISK-016", "Docker healthcheck false negative", "Infrastructure", "Open", "Medium", "Medium", "Medium", "Pooja", "pooja@example.com", 11, "Docker Compose"));
        records.add(createRisk("RISK-017", "Refresh token reuse vulnerability", "Security", "Open", "High", "High", "Medium", "Sanjay", "sanjay@example.com", 12, "JWT Refresh"));
        records.add(createRisk("RISK-018", "Pagination sort mismatch", "UX", "Mitigated", "Low", "Low", "Medium", "Lavanya", "lavanya@example.com", 5, "REST API"));
        records.add(createRisk("RISK-019", "Background scheduler overlap", "Operations", "Open", "Medium", "Medium", "Low", "Harish", "harish@example.com", 13, "Scheduler"));
        records.add(createRisk("RISK-020", "Database connection pool saturation", "Performance", "Open", "High", "High", "Medium", "Maya", "maya@example.com", 14, "PostgreSQL"));
        records.add(createRisk("RISK-021", "Risk code manual collision", "Data", "Closed", "Medium", "Medium", "Low", "Ajay", "ajay@example.com", 2, "Manual Entry"));
        records.add(createRisk("RISK-022", "Actuator endpoint exposure concern", "Security", "Mitigated", "Medium", "Medium", "Low", "Ritika", "ritika@example.com", 9, "Monitoring"));
        records.add(createRisk("RISK-023", "Frontend empty list dependency on null", "Integration", "Open", "Medium", "Low", "High", "Farah", "farah@example.com", 4, "Frontend API"));
        records.add(createRisk("RISK-024", "Mail SMTP credential rotation", "Communication", "Open", "Medium", "Medium", "Medium", "Dev", "dev@example.com", 15, "Email Service"));
        records.add(createRisk("RISK-025", "Incorrect risk priority defaults", "Logic", "Mitigated", "Low", "Low", "Medium", "Tina", "tina@example.com", 6, "Service Layer"));
        records.add(createRisk("RISK-026", "Unsupported XLS mime type", "Data", "Open", "Medium", "Medium", "High", "Manoj", "manoj@example.com", 7, "File Upload"));
        records.add(createRisk("RISK-027", "Seed data drift across environments", "Operations", "Open", "Low", "Low", "Medium", "Keerthi", "keerthi@example.com", 16, "Deployment"));
        records.add(createRisk("RISK-028", "Timezone mismatch in reminders", "Communication", "Open", "Medium", "Medium", "High", "Aarti", "aarti@example.com", 2, "Scheduler"));
        records.add(createRisk("RISK-029", "Slow keyword search under load", "Performance", "Open", "High", "Medium", "High", "Joseph", "joseph@example.com", 17, "Search API"));
        records.add(createRisk("RISK-030", "Admin bootstrap dependency", "Security", "Mitigated", "Medium", "Medium", "Low", "Naveen", "naveen@example.com", 3, "Auth Setup"));

        return records;
    }

    private RiskRegister createRisk(
        String riskCode,
        String title,
        String category,
        String status,
        String priority,
        String impactLevel,
        String likelihoodLevel,
        String ownerName,
        String ownerEmail,
        int targetResolutionOffsetDays,
        String sourceSystem
    ) {
        RiskRegister riskRegister = new RiskRegister();
        riskRegister.setRiskCode(riskCode);
        riskRegister.setTitle(title);
        riskRegister.setDescription(title + " may affect import/export flow if not addressed quickly.");
        riskRegister.setCategory(category);
        riskRegister.setStatus(status);
        riskRegister.setPriority(priority);
        riskRegister.setImpactLevel(impactLevel);
        riskRegister.setLikelihoodLevel(likelihoodLevel);
        riskRegister.setOwnerName(ownerName);
        riskRegister.setOwnerEmail(ownerEmail);
        riskRegister.setMitigationPlan("Monitor the issue, validate inputs, and apply mitigation steps through the backend workflow.");
        riskRegister.setTargetResolutionDate(LocalDate.now().plusDays(targetResolutionOffsetDays));
        riskRegister.setSourceSystem(sourceSystem);
        riskRegister.setActive(Boolean.TRUE);
        return riskRegister;
    }
}
