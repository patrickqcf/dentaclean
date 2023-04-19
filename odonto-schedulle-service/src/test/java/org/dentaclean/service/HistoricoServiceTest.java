package org.dentaclean.service;

import org.dentaclean.entity.Agendamento;
import org.dentaclean.entity.Historico;
import org.dentaclean.enums.Status;
import org.dentaclean.repository.HistoricoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.dentaclean.builders.AgendamentoBuilder.umAgendamentoBuilder;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = HistoricoServiceTest.class)
class HistoricoServiceTest {

    @InjectMocks
    private HistoricoService service;

    @Mock
    private HistoricoRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {

        Agendamento agendamento = umAgendamentoBuilder().agora();

        Historico historico = new Historico();
        historico.setData(agendamento.getDataAgendamento().atStartOfDay());
        historico.setStatus(agendamento.getStatus());
        historico.setAgendamento(agendamento);

        when(repository.save(historico)).thenReturn(historico);

        var result = service.create(historico);

        assertNotNull(result);
        assertEquals(Historico.class, result.getClass());
        assertEquals("2023-03-13T00:00", result.getData().toString());
        assertEquals(Status.CRIADO, result.getStatus(), "Status");

    }
}