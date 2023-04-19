package org.dentaclean.service;

import org.dentaclean.builders.AgendamentoBuilder;
import org.dentaclean.entity.Agendamento;
import org.dentaclean.entity.Historico;
import org.dentaclean.enums.Status;
import org.dentaclean.exceptions.NegocioException;
import org.dentaclean.repository.AgendamentoRepository;
import org.dentaclean.repository.HistoricoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.dentaclean.builders.AgendamentoBuilder.umAgendamentoBuilder;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AgendamentoServiceTest.class)
class AgendamentoServiceTest {

    @InjectMocks
    private AgendamentoService service;

    @Mock
    private HistoricoService historicoService;

    @Mock
    private HistoricoRepository historicoRepository;

    @Mock
    private Agendamento agendamento;

    @Mock
    private AgendamentoRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornaUmAgendamentoComSucesso() {

        agendamento = umAgendamentoBuilder().agora();

        when(repository.existeAgendamento(1L, 1,
                LocalDate.of(2023, 3, 13),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0))).thenReturn(0);

        when(repository.save(any())).thenReturn(umAgendamentoBuilder().agora());

        when(historicoRepository.save(any())).thenReturn(getHistorico());

        var result = service.create(umAgendamentoBuilder().agora());

        assertNotNull(result);
        assertEquals(Agendamento.class, result.getClass());
        assertEquals(umAgendamentoBuilder().agora(), result);
        assertEquals(1, result.getDentistaId(), "ID do dentista");
        assertEquals(Status.CRIADO, result.getStatus(), "Status");
        assertEquals("08:00", result.getHoraInicio().toString(), "Hora inicial");
        assertEquals("11:00", result.getHoraFim().toString(), "Hora fim");
        assertNull(result.getAgendamentoId());

        verify(repository, times(1)).save(umAgendamentoBuilder().agora());
    }

    @Test
    void deveRetornaUmaDataIntegrityViolationExceptionComIdJaExistente() {

        when(repository.existeAgendamento(1L, 1,
                LocalDate.of(2023, 3, 13),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0))).thenReturn(1);

        Exception ex = assertThrows(Exception.class, () -> service.create(umAgendamentoBuilder().agora()));
        assertEquals(DataIntegrityViolationException.class, ex.getClass());
        assertEquals("Agendamento indisponível.", ex.getMessage());
    }

    @Test
    void deveRertornaUmAgendamentoComIdCadastrado() {

        when(repository.findById(1L)).thenReturn(umAgendamentoBuilder().optionalAgendamento());

        Agendamento result = service.findById(1L);

        assertNotNull(result);
        assertEquals(Agendamento.class, result.getClass());
        assertEquals(1, result.getId(), "ID");
    }

    @Test
    void deveRetornaUmaEmptyResultDataAccessExceptionComIdInexistente() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, () -> service.findById(-1L));

        assertEquals(EmptyResultDataAccessException.class, ex.getClass());
        verify(repository, times(1)).findById(-1L);
        verify(repository, never()).save(any());
    }

    @Test
    void deveRetornaUmAgendamentoCanceladoComSuccess() {

        agendamento = AgendamentoBuilder.umAgendamentoBuilder().agora();

        when(repository.findById(1L)).thenReturn(Optional.of(agendamento));
        when(repository.save(agendamento)).thenReturn(agendamento);

        Historico historico = getHistorico();

        when(historicoService.create(historico)).thenReturn(historico);

        Agendamento agendamentoCancelado = service.cancel(1L);

        assertEquals(Status.CANCELADO, agendamentoCancelado.getStatus());
        assertNotNull(agendamentoCancelado.getAgendamentoId().getId());
        verify(repository, times(1)).save(agendamento);
    }

    @Test
    void deveRetornaNegocioExceptionAoCancelarUmAgendamentoJaCancelado() {

        when(repository.findById(1L)).thenReturn(umAgendamentoBuilder().agendamentoCancel());

        Exception ex = assertThrows(NegocioException.class, () -> service.cancel(1L));

        assertEquals(NegocioException.class, ex.getClass());
        assertEquals("Agendamento já foi cancelado ou finalizado anteriormente.", ex.getMessage());
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any());
    }

    @Test
    void deveRetornaUmNovoAgendamentoAoRemarcar() {

        agendamento = umAgendamentoBuilder().agora();

        when(repository.findById(1L)).thenReturn(Optional.of(agendamento));
        when(repository.save(agendamento)).thenReturn(umAgendamentoBuilder().cancelado());

        when(repository.existeAgendamento(1L, 1,
                LocalDate.of(2023, 3, 13),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0))).thenReturn(0);

        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setDataAgendamento(LocalDate.now().plusDays(1));
        novoAgendamento.setId(5L);
        novoAgendamento.setStatus(Status.CRIADO);

        when(repository.save(agendamento)).thenReturn(novoAgendamento);

        var result = service.remarcar(1L, agendamento);
        assertNull(agendamento.getId());
        assertEquals(5, novoAgendamento.getId());
        assertNotNull(result);
        assertEquals(Status.CRIADO, result.getStatus());
    }

    @Test
    void deveCriarUmNovoHistoricoComSucesso() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Agendamento agendamento = umAgendamentoBuilder().agora();

        Historico historicoEsperado = getHistorico();

        when(historicoService.create(any(Historico.class))).thenReturn(historicoEsperado);

        Historico historico = service.createHistorico(agendamento);

        ArgumentCaptor<Historico> historicoCaptor = ArgumentCaptor.forClass(Historico.class);
        verify(historicoService).create(historicoCaptor.capture());

        Historico historicoRetornado = historicoCaptor.getValue();

        assertNotNull(historico);
        assertEquals(Status.CRIADO, historicoRetornado.getStatus());
        assertEquals(LocalDateTime.now().format(formatter), historicoRetornado.getData().format(formatter));
        assertEquals(agendamento, historicoRetornado.getAgendamento());
    }

    private Historico getHistorico() {
        var historico = new Historico();
        historico.setData(LocalDateTime.of(2023, 4, 13, 8, 0));
        historico.setStatus(agendamento.getStatus());
        historico.setAgendamento(agendamento);
        return historico;
    }

}