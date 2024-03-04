package br.com.michaeldasilvases.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.io.Serializable;

@ToString(exclude = {"equipe"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PosicaoEntity implements Serializable{
    private Integer codigo;
    private String codigoVaga;
    private String titulo;
    private String tipoAlocacao;
    private String nomeColaborador;
    private Integer codigoEquipe;
    private String historico;

    @JsonManagedReference
    private EquipeEntity equipe;
}
