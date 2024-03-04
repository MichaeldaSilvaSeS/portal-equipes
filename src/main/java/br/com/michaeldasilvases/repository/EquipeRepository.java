package br.com.michaeldasilvases.repository;

import br.com.michaeldasilvases.entity.EquipeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class EquipeRepository {

    private List<EquipeEntity> lista = new ArrayList<>();
    {
        var equipe = EquipeEntity.builder()
                    .nome("Equipe 1")
                    .proposito("Proposito 1")
                    .codigoEquipe(0)
                    .sistemas("AAA,BBB")
                    .linkEquipe("https://junit.org/junit5/")
                .build();
        lista.add(equipe);

        var equipe1 = EquipeEntity.builder()
                    .nome("Equipe 2")
                    .proposito("Proposito 2")
                    .codigoEquipe(1)
                    .sistemas("AAA,CCC")
                    .linkEquipe("https://jquery.com/")
                .build();
        lista.add(equipe1);
    }
    public List<EquipeEntity> listAll(PosicaoRepository posicaoRepository,String busca,String tipo ){
        if(busca.equals("0") || tipo.equals("Timao"))
            return lista;
        if(busca.equals("0"))
            return List.of();

        lista.forEach( equipeDto -> {
            var posicoes = posicaoRepository.listAllByEquipe(equipeDto.getCodigoEquipe());
            equipeDto.setPosicoes(posicoes);
        });

        if(tipo.equals("Equipe"))
            return lista.stream().filter(e->{
                return e.getNome().toLowerCase().contains(busca);
            }).toList();
        if(tipo.equals("Proposito"))
            return lista.stream().filter(e->{
                return e.getProposito().toLowerCase().contains(busca);
            }).toList();
        if(tipo.equals("Sistemas"))
            return lista.stream().filter(e->{
                return e.getSistemas().toLowerCase().contains(busca);
            }).toList();
        if(tipo.equals("Colaboradores"))
            return lista.stream().filter(e->{
                return e.getPosicoes().stream().filter(c ->{
                   return c.getNomeColaborador().toLowerCase().contains(busca);
                }).count() > 0;
            }).toList();

        return List.of();
    }

    public EquipeEntity listById(Integer codigo){
        var equipe = lista.stream().filter(equipeEntity ->{
            return equipeEntity.getCodigoEquipe().equals(codigo);
        }).findFirst().get();
        equipe.setPosicoes(null);

        return equipe;
    }
}
