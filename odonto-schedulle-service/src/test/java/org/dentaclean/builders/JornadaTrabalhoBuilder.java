package org.dentaclean.builders;

import org.dentaclean.entity.JornadaTrabalho;

import java.time.LocalTime;

class JornadaTrabalhoBuilder {

    private JornadaTrabalho jornadaTrabalho;

    private JornadaTrabalhoBuilder(){}

    public static JornadaTrabalhoBuilder umJornadaTrabalhoBuilder(){
        JornadaTrabalhoBuilder builder = new JornadaTrabalhoBuilder();
        builder.jornadaTrabalho = new JornadaTrabalho();
        builder.jornadaTrabalho.setId(1L);
        builder.jornadaTrabalho.setDentistaId(1L);
        builder.jornadaTrabalho.setClinicaId(1L);
        builder.jornadaTrabalho.setDiaSemana(0);
        builder.jornadaTrabalho.setHoraInicio(LocalTime.of(8,00));
        builder.jornadaTrabalho.setHoraFim(LocalTime.of(12,00));
        return builder;
    }

    public JornadaTrabalho agora(){
        return jornadaTrabalho;
    }




}