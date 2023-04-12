package org.dentaclean.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status {

    CRIADO("CRIADO"),
    CANCELADO("CANCELADO"),
    FINALIZADO("FINALIZADO");

    private String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }

}
