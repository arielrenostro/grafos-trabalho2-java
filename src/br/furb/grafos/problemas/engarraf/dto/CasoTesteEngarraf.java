package br.furb.grafos.problemas.engarraf.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ariel e sidnei
 */
public class CasoTesteEngarraf {

    private int quantidadeLocais;
    private int quantidadeRuas;

    private int localInicio;
    private int localFim;

    private final Map<Integer, Map<Integer, Integer>> ruasPorLocalizacao = new HashMap<>();

    public int getQuantidadeLocais() {
        return quantidadeLocais;
    }

    public void setQuantidadeLocais(int quantidadeLocais) {
        this.quantidadeLocais = quantidadeLocais;
    }

    public int getQuantidadeRuas() {
        return quantidadeRuas;
    }

    public void setQuantidadeRuas(int quantidadeRuas) {
        this.quantidadeRuas = quantidadeRuas;
    }

    public int getLocalInicio() {
        return localInicio;
    }

    public void setLocalInicio(int localInicio) {
        this.localInicio = localInicio;
    }

    public int getLocalFim() {
        return localFim;
    }

    public void setLocalFim(int localFim) {
        this.localFim = localFim;
    }

    public Map<Integer, Map<Integer, Integer>> getRuasPorLocalizacao() {
        return ruasPorLocalizacao;
    }
}
