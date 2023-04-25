package org.dentaclean.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dentaclean.entity.JornadaTrabalho;
import org.dentaclean.service.JornadaTrabalhoService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JsonListener {

    private final JornadaTrabalhoService jornadaTrabalhoService;

    @KafkaListener(topics = "schedulle", groupId = "create-jornada", containerFactory = "jsonContainerFactory")
    public void sendMessageJornadaTrabalho(@Payload JornadaTrabalho jornadaTrabalho) {
        log.info("Menssagem recebida com sucesso");
        jornadaTrabalhoService.create(jornadaTrabalho);
    }
}
