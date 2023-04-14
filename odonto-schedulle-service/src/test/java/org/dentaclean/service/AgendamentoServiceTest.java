package org.dentaclean.service;

import org.dentaclean.entity.Agendamento;
import org.dentaclean.enums.Status;
import org.dentaclean.exceptions.NegocioException;
import org.dentaclean.repository.AgendamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private AgendamentoRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornaUmAgendamentoComSucesso() {

        when(repository.existeAgendamento(1L, 1,
                LocalDate.of(2023, 3, 13),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0))).thenReturn(0);

        when(repository.save(any())).thenReturn(umAgendamentoBuilder().agora());

        var result = service.create(umAgendamentoBuilder().agora());

        assertNotNull(result);
        assertEquals(Agendamento.class, result.getClass());
        assertEquals(umAgendamentoBuilder().agora(), result);
        assertEquals(1, result.getDentistaId(), "ID do dentista");
        assertEquals(Status.CRIADO, result.getStatus(), "Status");
        assertEquals("08:00", result.getHoraInicio().toString(), "Hora inicial");
        assertEquals("11:00", result.getHoraFim().toString(), "Hora fim");
        assertNull(result.getAgendamentoId());
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

        //Agendamento agendamentoMock = mock(Agendamento.class);

        // Chama o método setStatus() com o parâmetro Status.CANCELADO
        //agendamentoMock.setStatus(Status.CANCELADO);

        // Verifica se o método setStatus() foi chamado com o parâmetro correto
        //verify(agendamentoMock).setStatus(Status.CANCELADO);

        when(repository.findById(1L)).thenReturn(Optional.of(umAgendamentoBuilder().agora()));
        when(repository.save(any())).thenReturn(umAgendamentoBuilder().cancelado());

        var result = service.cancel(1L);

        assertEquals(Status.CANCELADO, result.getStatus(), "Status");
        assertEquals(1, result.getAgendamentoId().getId(), "AgendamentoId");
    }

    @Test
    void deveRetornaNegocioExceptionAoCancelarUmAgendamentoJaCancelado() {

        when(repository.findById(1L)).thenReturn(umAgendamentoBuilder().agendamentoCancel());

        Exception ex = assertThrows(NegocioException.class, () -> service.cancel(1L));

        assertEquals(NegocioException.class, ex.getClass());
        assertEquals("Agendamento já foi cancelado ou finalizado anteriormente.", ex.getMessage());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void deveRetornaUmNovoAgendamentoAoRemarcar() {

        Agendamento agendamentoARemarcar = umAgendamentoBuilder().comStatus(Status.CRIADO).agora();
        Agendamento agendamentoACancelado = umAgendamentoBuilder().comStatus(Status.CANCELADO).agora();

        when(repository.existeAgendamento(1L, 1,
                LocalDate.of(2023, 3, 13),
                LocalTime.of(8, 0),
                LocalTime.of(11, 0))).thenReturn(0);

        when(service.create(agendamentoACancelado)).thenReturn(agendamentoARemarcar);

        var result = service.remarcar(agendamentoACancelado);

        assertNotNull(result);
        assertEquals(Status.CRIADO, result.getStatus());
    }

    @Test
    void deveRetornaNegocioExceptionAoRemarcarUmAgendamentoComStatusCriado() {

        Agendamento agendamento = umAgendamentoBuilder().comStatus(Status.CANCELADO).agora();
        when(service.create(agendamento)).thenReturn(agendamento);

        try {
            Agendamento agendamentoARemarcar = umAgendamentoBuilder().comStatus(Status.CRIADO).agora();
            service.remarcar(agendamentoARemarcar);
            fail("NegocioException deveria ter sido lançada");
        } catch (NegocioException e) {
            assertEquals("Não é possível remarcar um agendamento sem ele estiver cancelado.", e.getMessage());
        }
    }

}