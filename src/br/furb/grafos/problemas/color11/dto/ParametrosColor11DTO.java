package br.furb.grafos.problemas.color11.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ariel e sidnei
 */
public class ParametrosColor11DTO {

    private int numeroLinhas;
    private int numeroColunas;
    private Coordenada coordenadaInicio;
    private int quantidadeQuadradosPintados;
    private List<Coordenada> coordenadas = new ArrayList<>();

    public int getNumeroLinhas() {
        return numeroLinhas;
    }

    public void setNumeroLinhas(int numeroLinhas) {
        this.numeroLinhas = numeroLinhas;
    }

    public int getNumeroColunas() {
        return numeroColunas;
    }

    public void setNumeroColunas(int numeroColunas) {
        this.numeroColunas = numeroColunas;
    }

    public Coordenada getCoordenadaInicio() {
        return coordenadaInicio;
    }

    public void setCoordenadaInicio(Coordenada coordenadaInicio) {
        this.coordenadaInicio = coordenadaInicio;
    }

    public int getQuantidadeQuadradosPintados() {
        return quantidadeQuadradosPintados;
    }

    public void setQuantidadeQuadradosPintados(int quantidadeQuadradosPintados) {
        this.quantidadeQuadradosPintados = quantidadeQuadradosPintados;
    }

    public List<Coordenada> getCoordenadas() {
        return coordenadas;
    }

    public void adicionarCoordenada(int linha, int coluna) {
        coordenadas.add(new Coordenada(linha, coluna));
    }

}
