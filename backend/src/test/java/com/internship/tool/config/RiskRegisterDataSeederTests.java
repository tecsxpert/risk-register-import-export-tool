package com.internship.tool.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.internship.tool.entity.RiskRegister;
import com.internship.tool.repository.RiskRegisterRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.DefaultApplicationArguments;

class RiskRegisterDataSeederTests {

    private final RiskRegisterRepository riskRegisterRepository = Mockito.mock(RiskRegisterRepository.class);

    @Test
    void shouldSeedThirtyRiskRegistersWhenRepositoryIsEmpty() throws Exception {
        when(riskRegisterRepository.findAll()).thenReturn(List.of());

        RiskRegisterDataSeeder seeder = new RiskRegisterDataSeeder(riskRegisterRepository, true, 30);
        seeder.run(new DefaultApplicationArguments(new String[]{}));

        ArgumentCaptor<List<RiskRegister>> captor = ArgumentCaptor.forClass(List.class);
        verify(riskRegisterRepository).saveAll(captor.capture());
        assertEquals(30, captor.getValue().size());
    }

    @Test
    void shouldOnlyTopUpMissingSeedRecords() throws Exception {
        when(riskRegisterRepository.findAll()).thenReturn(List.of(existingRisk("RISK-001"), existingRisk("RISK-002")));

        RiskRegisterDataSeeder seeder = new RiskRegisterDataSeeder(riskRegisterRepository, true, 30);
        seeder.run(new DefaultApplicationArguments(new String[]{}));

        ArgumentCaptor<List<RiskRegister>> captor = ArgumentCaptor.forClass(List.class);
        verify(riskRegisterRepository).saveAll(captor.capture());
        assertEquals(28, captor.getValue().size());
    }

    @Test
    void shouldNotSeedWhenDisabled() throws Exception {
        RiskRegisterDataSeeder seeder = new RiskRegisterDataSeeder(riskRegisterRepository, false, 30);
        seeder.run(new DefaultApplicationArguments(new String[]{}));

        verify(riskRegisterRepository, never()).findAll();
        verify(riskRegisterRepository, never()).saveAll(anyList());
    }

    private RiskRegister existingRisk(String riskCode) {
        RiskRegister riskRegister = new RiskRegister();
        riskRegister.setRiskCode(riskCode);
        return riskRegister;
    }
}
