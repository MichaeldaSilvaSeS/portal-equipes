package br.com.michaeldasilvases.controller;

import br.com.michaeldasilvases.controller.request.PosicaoDto;
import br.com.michaeldasilvases.entity.PosicaoEntity;
import br.com.michaeldasilvases.repository.EquipeRepository;
import br.com.michaeldasilvases.repository.PosicaoRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.List;
import java.util.Random;

@Controller
public class PosicaoController {

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private PosicaoRepository posicaoRepository;

    @GetMapping("/posicao")
    public String listByNomeColaborador(){
        return "posicao";
    }

    @GetMapping("/titulos")
    public @ResponseBody List<String> listAllTitulos(Model model){
        return posicaoRepository.listAllTitulos();
    }

    @GetMapping("/alocacoes")
    public @ResponseBody List<String> listAllAlocacoes(Model model){
        return posicaoRepository.listAllAlocacoes();
    }

    @GetMapping("/posicao/colaborador")
    public @ResponseBody List<PosicaoEntity> listByNomeColaborador(@RequestParam String nomeColaborador){

        if(nomeColaborador == null || nomeColaborador.trim().length() < 3)
            return List.of();

        return posicaoRepository
                .listAllByNomeColaborador(equipeRepository, nomeColaborador.toLowerCase())
                .stream().limit(5).toList();
    }

    @GetMapping("/posicao/atualizar")
    public String atualizar(@RequestParam Integer codigoPosicao){
        return "posicao-atualizar";
    }

    @GetMapping("/posicao/adicionar")
    public String adicionar(){
        return "posicao-adicionar";
    }

    @PostMapping(value = "/posicao/adicionar", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public  @ResponseBody PosicaoEntity adicionarPosicao(@RequestBody PosicaoDto model){
        var posicao = PosicaoEntity.builder()
                            .codigo(new Random().nextInt())
                            .codigoVaga(model.getCodigoVaga())
                            .nomeColaborador(model.getNomeColaborador())
                            .tipoAlocacao(model.getTipoAlocacao())
                            .titulo(model.getTitulo())
                            .codigoEquipe(model.getCodigoEquipe())
                        .build();

        posicaoRepository.salvar(posicao);
        System.out.println(model);
        System.out.println(model.getCodigo());
        return posicao;
    }

}
