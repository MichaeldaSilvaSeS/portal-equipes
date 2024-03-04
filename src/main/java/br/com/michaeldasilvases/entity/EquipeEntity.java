package br.com.michaeldasilvases.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EquipeEntity implements Serializable {
    private Integer codigoEquipe;
    private String nome;
    private String proposito;
    private String sistemas;
    private String linkEquipe;

    private List<PosicaoEntity> posicoes;
}
