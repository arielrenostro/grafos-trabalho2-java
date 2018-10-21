package br.furb.grafos.problemas.engarraf.problema;

import br.furb.grafos.problemas.engarraf.dto.CasoTesteEngarraf;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ariel e sidnei
 */
public class Engarraf {

    public List<Integer> resolver(List<CasoTesteEngarraf> casosTesteEngarraf) {
        List<Integer> tempos = new ArrayList<>();

        for (CasoTesteEngarraf casoTeste : casosTesteEngarraf) {
            int tempo = getTempoCasoTeste(casoTeste);
            tempos.add(tempo);
        }

        return tempos;
    }

    private int getTempoCasoTeste(CasoTesteEngarraf casoTeste) {
        // Localizacao, <Localizacao adjacente, tempo>
        Map<Integer, Map<Integer, Integer>> ruasPorLocalizacao = casoTeste.getRuasPorLocalizacao();
        Map<Integer, Integer> tempoPorLocalizacaoOtimizado = new HashMap<>();

        List<Integer> fila = getFilaLocalizacoes(casoTeste);
        while (!fila.isEmpty()) {
            Integer localizacao = getLocalizacaoMenorTempo(tempoPorLocalizacaoOtimizado, fila, casoTeste);
            Integer tempoPai = getTempoPorLocalizacao(tempoPorLocalizacaoOtimizado, localizacao);
            List<Map.Entry<Integer, Integer>> temposPorLocalizacao = getTemposPorLocalizacao(ruasPorLocalizacao, localizacao);

            for (Map.Entry<Integer, Integer> entry : temposPorLocalizacao) {
                Integer localizacaoAdjacente = entry.getKey();
                Integer tempo = entry.getValue() + tempoPai;

                Integer tempoOtimizado = tempoPorLocalizacaoOtimizado.get(localizacaoAdjacente);
                if (tempoOtimizado == null || tempoOtimizado > tempo) {
                    tempoPorLocalizacaoOtimizado.put(localizacaoAdjacente, tempo);
                }
            }
        }

        return getMenorTempoCasoTeste(casoTeste, tempoPorLocalizacaoOtimizado);
    }

    private int getMenorTempoCasoTeste(CasoTesteEngarraf casoTeste, Map<Integer, Integer> temposPorLocalizacaoOtimizada) {
        Integer tempo = temposPorLocalizacaoOtimizada.get(casoTeste.getLocalFim());
        if (null == tempo) {
            return -1;
        }
        return tempo;
    }

    private Integer getLocalizacaoMenorTempo(Map<Integer, Integer> tempoPorLocalizacao, List<Integer> localizacoes, CasoTesteEngarraf casoTeste) {
        Integer menorLocalizacao = null;
        Integer tempoMenorLocalizacao = null;

        if (tempoPorLocalizacao.isEmpty()) {
            return casoTeste.getLocalInicio();
        }

        for (Integer localizacao : localizacoes) {
            Integer tempoLocalizacao = tempoPorLocalizacao.get(localizacao);
            if (null == tempoMenorLocalizacao || (null != tempoLocalizacao && tempoMenorLocalizacao > tempoLocalizacao)) {
                menorLocalizacao = localizacao;
                tempoMenorLocalizacao = tempoLocalizacao;
            }
        }

        if (null == menorLocalizacao) {
            menorLocalizacao = localizacoes.remove(0);
        }

        localizacoes.remove(menorLocalizacao);
        return menorLocalizacao;
    }

    private List<Integer> getFilaLocalizacoes(CasoTesteEngarraf casoTeste) {
        List<Integer> fila = new ArrayList<>();

        for (int i = 1; i <= casoTeste.getQuantidadeLocais(); i++) {
            if (i == casoTeste.getLocalInicio()) {
                fila.add(0, i);
            } else {
                fila.add(i);
            }
        }

        return fila;
    }

    private List<Map.Entry<Integer, Integer>> getTemposPorLocalizacao(Map<Integer, Map<Integer, Integer>> ruasPorLocalizacao, Integer localizacao) {
        Map<Integer, Integer> temposPorLocalizacao = ruasPorLocalizacao.get(localizacao);
        if (null == temposPorLocalizacao) {
            return Collections.emptyList();
        }
        return temposPorLocalizacao.entrySet().stream()
                    .sorted(Comparator.comparing(Map.Entry::getValue))
                    .collect(Collectors.toList());
    }

    private Integer getTempoPorLocalizacao(Map<Integer, Integer> tempoPorLocalizacao, Integer localizacao) {
        Integer tempoPai = tempoPorLocalizacao.get(localizacao);
        if (null == tempoPai) {
            tempoPai = 0;
        }
        return tempoPai;
    }
}
