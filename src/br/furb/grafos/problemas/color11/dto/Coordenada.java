package br.furb.grafos.problemas.color11.dto;

/**
 * @author ariel e sidnei
 */
public class Coordenada {

    private final int linha;
    private final int coluna;

    public Coordenada(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return this.linha;
    }

    public int getColuna() {
        return this.coluna;
    }

    public String toString() {
        return "Coluna [" + coluna + "], Linha [" + linha + "]";
    }
}