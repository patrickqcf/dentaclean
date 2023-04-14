package org.dentaclean.service;

import org.dentaclean.entity.JornadaTrabalho;
import org.dentaclean.repository.JornadaTrabalhoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.dentaclean.builders.JornadaTrabalhoBuilder.umJornadaTrabalhoBuilder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = JornadaTrabalhoServiceTest.class)
class JornadaTrabalhoServiceTest {

    @InjectMocks
    private JornadaTrabalhoService service;

    @Mock
    private JornadaTrabalhoRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornaUmaJornadaTrabalhoComId() {

        when(repository.existeJornadaTrabalho(2L, 0,
                LocalTime.of(14, 0), LocalTime.of(17, 0)))
                .thenReturn(umJornadaTrabalhoBuilder().optionalTrabalho());

        when(repository.save(any())).thenReturn(umJornadaTrabalhoBuilder().agora());

        var result = service.create(umJornadaTrabalhoBuilder().agora());

        assertNotNull(result);
        assertEquals(JornadaTrabalho.class, result.getClass());
        assertEquals(umJornadaTrabalhoBuilder().agora(), result);
        assertEquals(2, result.getDentistaId(), "ID do dentista");
        assertEquals(1, result.getClinicaId(), "ID do clinica");
        assertEquals(0, result.getDiaSemana(), "Dia semana");
        assertEquals("08:00", result.getHoraInicio().toString(), "Hora inicial");
        assertEquals("12:00", result.getHoraFim().toString(), "Hora fim");

    }

    @Test
    void deveRetornaUmaDataIntegrityViolationExceptionComIdJaCadastrado() {

        when(repository.existeJornadaTrabalho(2L, 0,
                LocalTime.of(8, 0), LocalTime.of(12, 0)))
                .thenReturn(umJornadaTrabalhoBuilder().jornadaTrabalhoExistente());

        try {
            service.create(umJornadaTrabalhoBuilder().agora());
            fail("Verificar falha na implementação desse teste");
        } catch (DataIntegrityViolationException ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals("Jornada de trabalho já cadastrada", ex.getMessage());
        }
    }

    @Test
    void deveRertornaUmaJorandaTrabalhoComIdCadastrado() {
        when(repository.findById(1L)).thenReturn(umJornadaTrabalhoBuilder().optionalTrabalho());
        JornadaTrabalho result = service.findById(1L);
        assertNotNull(result);
        assertEquals(JornadaTrabalho.class, result.getClass());
        assertEquals(1, result.getId(), "ID");
    }

    @Test
    void deveRetornaUmaEmptyResultDataAccessExceptionComIdInexistente() {
        when(repository.findById(1L)).thenReturn(umJornadaTrabalhoBuilder().optionalTrabalho());
        try {
            service.findById(-1L);
            fail("Verificar falha na implementação desse teste");
        } catch (EmptyResultDataAccessException ex) {
            assertEquals(EmptyResultDataAccessException.class, ex.getClass());
            verify(repository, times(1)).findById(-1L);
            verify(repository, never()).save(any());
        }
    }

    @Test
    void deveRetornaUmaListaComIdDoDentistaCadastrado() {
        when(repository.findAllByDentistaId(1L)).thenReturn(List.of(umJornadaTrabalhoBuilder().agora()));
        List<JornadaTrabalho> result = service.findByDentistaId(1L);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(JornadaTrabalho.class, result.get(0).getClass());
    }

    @Test
    void deveRetornaUmaListaVaziaComIdDoDentistaInexistente() {
        when(repository.findAllByDentistaId(1L)).thenReturn(List.of(umJornadaTrabalhoBuilder().agora()));
        List<JornadaTrabalho> result = service.findByDentistaId(-1L);
        assertTrue(result.isEmpty());
    }

    @Test
    void deveRetornaUmaJornadaTrabalhoEditada() {
        JornadaTrabalho jt = umJornadaTrabalhoBuilder().validationIdUpdate().agora();
        JornadaTrabalho jtAtualizada = umJornadaTrabalhoBuilder().jornadaTrabalhoEditada();

        when(repository.findById(1L)).thenReturn(Optional.of(umJornadaTrabalhoBuilder().agora()));
        when(repository.save(jt)).thenReturn(jtAtualizada);

        var result = service.update(1L, jt);

        assertNotNull(result);
        assertEquals(JornadaTrabalho.class, result.getClass());
        assertEquals(jt.getId(), result.getId(), "ID");
        assertEquals(2, result.getDentistaId(), "ID do dentista");
        assertEquals(2, result.getClinicaId(), "ID do clinica");
        assertEquals(7, result.getDiaSemana(), "Dia semana");
        assertEquals("14:00", result.getHoraInicio().toString(), "Hora inicial");
        assertEquals("17:00", result.getHoraFim().toString(), "Hora fim");
    }

    @Test
    void deveRetornaUmaExceptionJornadaTrabalhoEditadaJaCadastrada() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(umJornadaTrabalhoBuilder().agora()));
        when(repository.existeJornadaTrabalho(2L, 0,
                LocalTime.of(8, 0), LocalTime.of(12, 0)))
                .thenReturn(umJornadaTrabalhoBuilder().jornadaTrabalhoExistente());

        assertThrows(DataIntegrityViolationException.class, () -> {
            service.update(id, umJornadaTrabalhoBuilder().agora());
        });

        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }


}