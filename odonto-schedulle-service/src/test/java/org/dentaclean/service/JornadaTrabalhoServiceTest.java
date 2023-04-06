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

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class JornadaTrabalhoServiceTest {

    @InjectMocks
    private JornadaTrabalhoService service;

    @Mock
    private JornadaTrabalhoRepository repository;

    private JornadaTrabalho jornadaTrabalho;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateWithSucessoJornadaTrabalho() {
        // Cria instâncias necessarias para o teste
        jornadaTrabalho = new JornadaTrabalho(1L, 1L, 1L, 0, LocalTime.of(1, 1), LocalTime.of(1, 1));

        Optional<JornadaTrabalho> optional = Optional.of(jornadaTrabalho);

        when(repository.existeJornadaTrabalho(jornadaTrabalho.getDentistaId(), jornadaTrabalho.getDiaSemana(),
                jornadaTrabalho.getHoraInicio(), jornadaTrabalho.getHoraFim()))
                .thenReturn(optional);

        var jornada = JornadaTrabalho.builder().dentistaId(2L).clinicaId(1L).diaSemana(0).horaInicio(
                LocalTime.of(1, 1)).horaFim(LocalTime.of(1, 1)).build();

        when(repository.save(any())).thenReturn(jornada);

        JornadaTrabalho result = service.create(jornada);

        assertNotNull(result);
        assertEquals(JornadaTrabalho.class, result.getClass());
        assertEquals(jornada, result);
        assertEquals(2, result.getDentistaId(), "ID do dentista");
        assertEquals(1, result.getClinicaId(),"ID do clinica");
        assertEquals(0, result.getDiaSemana(),"Dia semana");
        assertEquals("01:01", result.getHoraInicio().toString(),"Hora inicial");
        assertEquals("01:01", result.getHoraFim().toString(),"Hora fim");

    }

    @Test
    public void testCreateWithExistingJornadaTrabalho() {

        jornadaTrabalho = JornadaTrabalho.builder().id(1L).dentistaId(1L).diaSemana(0).horaInicio(
                LocalTime.of(1, 1)).horaFim(LocalTime.of(1, 1)).build();

        Optional<JornadaTrabalho> optional = Optional.of(jornadaTrabalho);

        when(repository.existeJornadaTrabalho(jornadaTrabalho.getDentistaId(), jornadaTrabalho.getDiaSemana(),
                jornadaTrabalho.getHoraInicio(), jornadaTrabalho.getHoraFim()))
                .thenReturn(optional);

        try {
            service.create(JornadaTrabalho.builder().dentistaId(1L).diaSemana(0).horaInicio(
                    LocalTime.of(1, 1)).horaFim(LocalTime.of(1, 1)).build());
            fail("Verificar falha na implementação desse teste");
        } catch (DataIntegrityViolationException ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals("Jornada de trabalho já cadastrada", ex.getMessage());
        }
    }




















  /*  @Test
    public void testExisteJornadaTrabalho() {
        // Crie uma instância de JornadaTrabalho para teste
        JornadaTrabalho jornada = new JornadaTrabalho();
        jornada.setDentistaId(1L);
        jornada.setDiaSemana(0);
        jornada.setHoraInicio(LocalTime.of(8, 0));
        jornada.setHoraFim(LocalTime.of(11, 0));

        // Crie um objeto Optional com uma instância de JornadaTrabalho existente
        Optional<JornadaTrabalho> jornadaExistente = Optional.of(jornadaTrabalho);

        // Crie um mock do repositório e configure-o para retornar o objeto Optional criado acima
        when(repository.existeJornadaTrabalho(jornada.getDentistaId(), jornada.getDiaSemana(),
                jornada.getHoraInicio(), jornada.getHoraFim())).thenReturn(jornadaExistente);


        // Chame o método a ser testado com a instância de JornadaTrabalho criada acima
        // Certifique-se de que o método lança uma exceção de DataIntegrityViolationException
        assertThrows(DataIntegrityViolationException.class, () -> service.existeJornadaTrabalho(jornada));
    }
*/


}