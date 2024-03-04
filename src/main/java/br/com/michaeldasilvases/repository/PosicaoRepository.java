package br.com.michaeldasilvases.repository;

import br.com.michaeldasilvases.entity.PosicaoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PosicaoRepository {
    private List<PosicaoEntity> lista = new ArrayList<>();
    {
        var posicaoTechLead = PosicaoEntity.builder()
                .codigo(1)
                .codigoVaga("01")
                .titulo("Tech lead")
                .tipoAlocacao("Interno")
                .nomeColaborador("Joao")
                .codigoEquipe(0)
                .historico("dsdsadasdvv efsfsdvdvsdv efafaddsvdsv dvsdv")
                .build();
        lista.add(posicaoTechLead);

        var posicaoTechLead1 = PosicaoEntity.builder()
                .codigo(2)
                .codigoVaga("02")
                .titulo("Tech lead")
                .tipoAlocacao("Interno")
                .nomeColaborador("Carlos")
                .codigoEquipe(1)
                .historico("dsdsadasdvv efsfsdvdvsdv efafadsaddasdasd")
                .build();
        lista.add(posicaoTechLead1);

        var posicaoTeamMember = PosicaoEntity.builder()
                .codigo(3)
                .codigoVaga("04")
                .titulo("Team Member")
                .tipoAlocacao("Consultor")
                .nomeColaborador("Daviv")
                .codigoEquipe(1)
                .historico("dsdsadasdvv efsfsdvdvsdv efafad")
                .build();
        lista.add(posicaoTeamMember);
    }

    public List<PosicaoEntity> listAllByEquipe(Integer codigoEquipe){
        var posicoes = lista.stream().filter( e -> e.getCodigoEquipe().equals(codigoEquipe)).toList();
        posicoes.forEach(e ->{
            e.setEquipe(null);
        });
        return posicoes;
    }
    public List<String> listAllTitulos(){
        return List.of("Team Member","Scrum Master", "Product Owner", "Tech Lead");
    }
    public List<String> listAllAlocacoes(){
        return List.of("Interna","Consultor");
    }

    public void salvar(PosicaoEntity posicao){
        lista.add(posicao);
    }

    public List<PosicaoEntity> listAllByNomeColaborador(EquipeRepository equipeRepository, String nomeColaborador){
        var listaPosicoes = lista.stream()
                .filter(e -> e.getNomeColaborador().toLowerCase().contains(nomeColaborador))
                .toList();
        listaPosicoes.forEach( e ->{
            e.setEquipe(equipeRepository.listById(e.getCodigoEquipe()));
        });
        return listaPosicoes;
    }

}
