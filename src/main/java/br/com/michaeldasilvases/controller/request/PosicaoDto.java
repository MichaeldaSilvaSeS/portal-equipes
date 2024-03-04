package br.com.michaeldasilvases.controller.request;
import lombok.*;

import java.io.Serializable;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PosicaoDto implements Serializable{
    private Integer codigo;
    private String codigoVaga;
    private String titulo;
    private String tipoAlocacao;
    private String nomeColaborador;
    private Integer codigoEquipe;
}
