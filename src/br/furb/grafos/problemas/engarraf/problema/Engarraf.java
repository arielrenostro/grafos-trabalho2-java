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
        Map<Integer, Map<Integer, Integer>> ruasPorLocalizacao = casoTeste.getRuasPorLocalizacao();

        // TODO CONTINUAR
        Map<Integer, Integer> localizacaoPaiPorFilho = new HashMap<>();
        Map<Integer, Integer> temposPorLocalizacaoDikstra = new HashMap<>();

        Integer localizacaoPai = casoTeste.getLocalInicio();

        Integer tempoPai = temposPorLocalizacaoDikstra.get(localizacaoPai);
        if (null == tempoPai) {
            tempoPai = 0;
        }
        List<Map.Entry<Integer, Integer>> temposPorLocalizacao = ruasPorLocalizacao.get(localizacaoPai).entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .collect(Collectors.toList());

        for (Map.Entry<Integer, Integer> entry : temposPorLocalizacao) {
            Integer localizacao = entry.getKey();
            Integer tempo = entry.getValue() + tempoPai;

            Integer tempoPorLocalizacaoDikstra = temposPorLocalizacaoDikstra.get(localizacao);
            if (tempoPorLocalizacaoDikstra == null || tempoPorLocalizacaoDikstra > tempo) {
                localizacaoPaiPorFilho.put(localizacao, localizacaoPai);
                temposPorLocalizacaoDikstra.put(localizacao, tempo);
            }
        }

    }
}
