package br.furb.grafos.problemas.color11.problema;

import br.furb.grafos.problemas.color11.Utils.StringUtils;
import br.furb.grafos.problemas.color11.dto.Coordenada;
import br.furb.grafos.problemas.color11.dto.ParametrosColor11DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ariel e sidnei
 */
public class Color11 {

    private static final byte NAO_PINTADO = 0x00;
    private static final byte PINTADO = 0x01;
    private static final byte CONTADO = 0x02;

    public int resolver(ParametrosColor11DTO parametrosColor11) throws Exception {
        byte[][] matriz = determinarMatrizPorParametros(parametrosColor11);

        int resultado = 0;
        List<Coordenada> fila = new ArrayList<>();
        Coordenada coordenadaInicio = parametrosColor11.getCoordenadaInicio();
        adicionarFila(matriz, fila, coordenadaInicio);

        while (!fila.isEmpty()) {
            Coordenada coordenada = fila.remove(0);
            resultado++;

            List<Coordenada> adjacentes = getAdjacentes(matriz, coordenada);
            for (Coordenada adjacente : adjacentes) {
                byte valorCoordenada = getValorPorCoordenada(matriz, adjacente);
                if (NAO_PINTADO == valorCoordenada) {
                    adicionarFila(matriz, fila, adjacente);
                }
            }
        }

        return resultado;
    }

    private void adicionarFila(byte[][] matriz, List<Coordenada> fila, Coordenada coordenada) {
        fila.add(coordenada);
        setValorPorCoordenada(matriz, coordenada, CONTADO);
    }

    private void setValorPorCoordenada(byte[][] matriz, Coordenada coordenada, byte valor) {
        matriz[coordenada.getLinha()][coordenada.getColuna()] = valor;
    }

    private byte getValorPorCoordenada(byte[][] matriz, Coordenada coordenada) {
        return matriz[coordenada.getLinha()][coordenada.getColuna()];
    }

    private List<Coordenada> getAdjacentes(byte[][] matriz, Coordenada coordenada) {
        List<Coordenada> coordenadasAjacentes = new ArrayList<>();

        int idxLinhaMax = matriz.length - 1;
        int idxColunaMax = matriz[0].length - 1;

        int linha = coordenada.getLinha();
        int coluna = coordenada.getColuna();

        for (int idxLinha = linha - 1; idxLinha <= linha + 1; idxLinha++) {
            for (int idxColuna = coluna - 1; idxColuna <= coluna + 1; idxColuna++) {
                if (0 > idxLinha
                        || 0 > idxColuna
                        || idxColunaMax < idxColuna
                        || idxLinhaMax < idxLinha
                        || (linha == idxLinha
                            && coluna == idxColuna)) {
                    continue;
                }

                Coordenada coordenadaAdjacente = new Coordenada(idxLinha, idxColuna);
                coordenadasAjacentes.add(coordenadaAdjacente);
            }
        }

        return coordenadasAjacentes;
    }

    private byte[][] determinarMatrizPorParametros(ParametrosColor11DTO parametrosColor11) throws Exception {
        validarParametros(parametrosColor11);

        int linhas = parametrosColor11.getNumeroLinhas();
        int colunas = parametrosColor11.getNumeroColunas();
        byte[][] matriz = new byte[linhas][colunas];

        popularMatrizComCoordenadas(matriz, parametrosColor11);

        return matriz;
    }

    private void popularMatrizComCoordenadas(byte[][] matriz, ParametrosColor11DTO parametrosColor11) {
        for (Coordenada coordenada : parametrosColor11.getCoordenadas()) {
            setValorPorCoordenada(matriz, coordenada, PINTADO);
        }
    }

    private void validarParametros(ParametrosColor11DTO parametrosColor11) throws Exception {
        StringBuilder erros = new StringBuilder();
        if (0 >= parametrosColor11.getNumeroColunas()) {
            StringUtils.concatenarComSeparador(erros, "Número de colunas inválido");
        }
        if (0 >= parametrosColor11.getNumeroLinhas()) {
            StringUtils.concatenarComSeparador(erros, "Número de linhas inválido");
        }
        if (parametrosColor11.getNumeroLinhas() < parametrosColor11.getCoordenadaInicio().getLinha()) {
            StringUtils.concatenarComSeparador(erros, "Coordenada da linha maior que número de linhas");
        }
        if (parametrosColor11.getNumeroColunas() < parametrosColor11.getCoordenadaInicio().getColuna()) {
            StringUtils.concatenarComSeparador(erros, "Coordenada da coluna maior que número de colunas");
        }

        if (0 > erros.length()) {
            throw new Exception(erros.toString());
        }
    }
}
