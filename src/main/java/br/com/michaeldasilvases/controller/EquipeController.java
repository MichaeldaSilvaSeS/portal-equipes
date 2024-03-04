package br.com.michaeldasilvases.controller;

import br.com.michaeldasilvases.entity.EquipeEntity;
import br.com.michaeldasilvases.repository.EquipeRepository;
import br.com.michaeldasilvases.repository.PosicaoRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.TextUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class EquipeController {

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private PosicaoRepository posicaoRepository;

    @GetMapping("/equipe")
    public String getEquipes(@RequestParam String busca, @RequestParam String tipo){
        return "equipe";
    }

    @GetMapping("/equipes")
    public @ResponseBody List<EquipeEntity> getEquipe(@RequestParam String busca, @RequestParam String tipo){
        System.out.println(busca);
        System.out.println(tipo);

        return equipeRepository.listAll(posicaoRepository,busca.toLowerCase(),tipo);
    }

    @GetMapping("/download")
    public HttpEntity<byte[]> download(@RequestParam String busca, @RequestParam String tipo){
        System.out.println(busca);
        System.out.println(tipo);

        var lista = equipeRepository.listAll(posicaoRepository,busca.toLowerCase(),tipo);
        var listaMap = lista.stream().map(equipeEntity ->{
            var linhas = equipeEntity.getPosicoes() == null ?
                    List.of(System.lineSeparator()) :
                    equipeEntity.getPosicoes().stream().map(posicaoEntity -> {
                        return StringUtils.arrayToDelimitedString(new Object[]{
                                equipeEntity.getNome(),
                                posicaoEntity.getNomeColaborador(),
                                posicaoEntity.getTitulo(),
                                posicaoEntity.getTipoAlocacao(),
                                posicaoEntity.getCodigoVaga(),
                                System.lineSeparator()
                        },";");
                    }).toList();
            return StringUtils.arrayToDelimitedString(linhas.toArray(),"");
        });

        StringBuffer sb = new StringBuffer();
        sb.append("Equipe;Nome Colaborador;Titulo;Alocacao;Vaga;"+System.lineSeparator());
        listaMap.forEach(e ->{
            sb.append(e);
        });

        var documentBody = sb.toString().getBytes(StandardCharsets.UTF_8);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf("text/csv"));
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=exportacao_times.csv");
        header.setContentLength(documentBody.length);

        System.out.println(documentBody.length);

        return new HttpEntity<byte[]>(documentBody, header);
    }

}
