package org.dentaclean.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dentaclean.api.controller.SuccessResponse;
import org.dentaclean.api.dtos.JornadaTrabalhoDTO;
import org.dentaclean.api.mapper.JornadaTrabalhoMapper;
import org.dentaclean.domain.model.JornadaTrabalho;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.Serializable;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JornadaTrabalhoService {

    private final JornadaTrabalhoMapper mapper;

    private final KafkaTemplate<String, Serializable> kafkaTemplate;

    public SuccessResponse sendJornadaTrabalho(JornadaTrabalhoDTO obj) {
        String ID = UUID.randomUUID().toString();
        JornadaTrabalho jornadaTrabalho = mapper.mapToEntity(obj);
        jornadaTrabalho.setUuid(ID);

        ListenableFuture<SendResult<String, Serializable>> future = kafkaTemplate.send("schedulle-topic", jornadaTrabalho);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Serializable>>() {
            @Override
            public void onSuccess(SendResult<String, Serializable> result) {
                log.info("Mensagem [{}] entregue com sucesso offset {}",
                        jornadaTrabalho,
                        result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.warn("Não foi possível entregar a mensagem [{}]. {}",
                        jornadaTrabalho,
                        ex.getMessage());
            }
        });

        return SuccessResponse.aply("Mensagem entregue com sucesso!", ID);
    }
}
