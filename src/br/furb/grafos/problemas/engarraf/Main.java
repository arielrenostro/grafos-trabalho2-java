package br.furb.grafos.problemas.engarraf;

import br.furb.grafos.problemas.engarraf.dto.CasoTesteEngarraf;
import br.furb.grafos.problemas.engarraf.problema.Engarraf;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ariel e sidnei
 */
public class Main {

    private static final String CAMINHO_ARQUIVO_PARAMETROS;

    private static final int CABECALHO = 0;
    private static final int RUAS = 1;
    private static final int FINAL = 2;

    static {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            CAMINHO_ARQUIVO_PARAMETROS = "C:\\temp\\entrada.in";
        } else {
            CAMINHO_ARQUIVO_PARAMETROS = "/tmp/entrada.in";
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Problema de grafos Engarraf");
        System.out.println("Resolução por: Ariel e Sidnei");
        System.out.println();

        List<CasoTesteEngarraf> casosTesteEngarraf = new ArrayList<>();
        if (isTemArquivoConfiguracao()) {
            popularParametrosPorArquivo(casosTesteEngarraf);
        } else {
            popularParametrosPorTerminal(casosTesteEngarraf);
        }

        List<Integer> resultado = new Engarraf().resolver(casosTesteEngarraf);
        System.out.println("Resultado:");
        resultado.forEach(r -> System.out.println(r));
    }

    private static boolean isTemArquivoConfiguracao() {
        Path path = Paths.get(CAMINHO_ARQUIVO_PARAMETROS);
        return Files.exists(path);
    }

    private static void popularParametrosPorArquivo(List<CasoTesteEngarraf> casosTesteEngarraf) throws IOException {
        Path path = Paths.get(CAMINHO_ARQUIVO_PARAMETROS);
        BufferedReader bufferedReader = Files.newBufferedReader(path);

        CasoTesteEngarraf casoTesteEngarraf = null;
        AtomicInteger passoAtual = new AtomicInteger(CABECALHO);
        AtomicInteger idxLinha = new AtomicInteger(0);
        AtomicBoolean continuar = new AtomicBoolean(true);
        while (continuar.get()) {
            idxLinha.incrementAndGet();
            String[] leitura = bufferedReader.readLine().split("\\s");
            casoTesteEngarraf = tratarLeitura(casosTesteEngarraf, casoTesteEngarraf, passoAtual, idxLinha, continuar, leitura);
        }

        bufferedReader.close();
    }

    private static void popularLocalizacoesInicioFim(CasoTesteEngarraf casoTesteEngarraf, String[] leitura, int idxLinha) {
        if (leitura.length != 2) {
            System.out.println("Linha " + idxLinha);
            erroParametrosInvalidos();
        }

        Integer localInicio = Integer.valueOf(leitura[0]);
        Integer localFim = Integer.valueOf(leitura[1]);

        if (1 > localInicio || localFim > casoTesteEngarraf.getQuantidadeLocais()) {
            erroParametrosInvalidos();
        }

        casoTesteEngarraf.setLocalInicio(localInicio);
        casoTesteEngarraf.setLocalFim(localFim);
    }

    private static void popularRuas(CasoTesteEngarraf casoTesteEngarraf, String[] leitura, int idxLinha) {
        if (leitura.length != 3) {
            System.out.println("Linha " + idxLinha);
            erroParametrosInvalidos();
        }
        int localOrigem = Integer.valueOf(leitura[0]);
        int localDestino = Integer.valueOf(leitura[1]);
        int tempo = Integer.valueOf(leitura[2]);

        if (1 > localOrigem
                || localDestino > casoTesteEngarraf.getQuantidadeLocais()
                || localDestino == localOrigem
                || 1 > tempo
                || 1000 < tempo) {
            System.out.println("Linha " + idxLinha);
            erroParametrosInvalidos();
        }

        Map<Integer, Integer> temposPorDestino = casoTesteEngarraf.getRuasPorLocalizacao().get(localOrigem);
        if (null == temposPorDestino) {
            temposPorDestino = new HashMap<>();
            casoTesteEngarraf.getRuasPorLocalizacao().put(localOrigem, temposPorDestino);
        }

        if (temposPorDestino.containsKey(localDestino)) {
            System.out.println("Linha " + idxLinha);
            System.out.println("Rua duplicada");
            System.exit(1);
        }

        temposPorDestino.put(localDestino, tempo);
    }

    private static CasoTesteEngarraf criarCasoTesteEngarraf(String[] leitura, int idxLinha) {
        CasoTesteEngarraf casoTesteEngarraf = new CasoTesteEngarraf();
        if (leitura.length != 2) {
            System.out.println("Linha " + idxLinha);
            erroParametrosInvalidos();
        }

        Integer quantidadeLocais = Integer.valueOf(leitura[0]);
        Integer quantidadeRuas = Integer.valueOf(leitura[1]);

        if ((1 > quantidadeLocais || 100 < quantidadeLocais || 0 > quantidadeRuas || 10000 < quantidadeLocais)
                && !(0 == quantidadeLocais && 0 == quantidadeRuas)) {
            System.out.println("Linha " + idxLinha);
            erroParametrosInvalidos();
        }

        casoTesteEngarraf.setQuantidadeLocais(quantidadeLocais);
        casoTesteEngarraf.setQuantidadeRuas(quantidadeRuas);

        return casoTesteEngarraf;
    }

    private static void popularParametrosPorTerminal(List<CasoTesteEngarraf> casosTesteEngarraf) {
        Scanner scanner = new Scanner(System.in);

        CasoTesteEngarraf casoTesteEngarraf = null;
        AtomicInteger passoAtual = new AtomicInteger(CABECALHO);
        AtomicInteger idxLinha = new AtomicInteger(0);
        AtomicBoolean continuar = new AtomicBoolean(true);
        while (continuar.get()) {
            idxLinha.incrementAndGet();
            printCabecalhoPasso(passoAtual.get());
            String[] leitura = scanner.nextLine().split("\\s");
            casoTesteEngarraf = tratarLeitura(casosTesteEngarraf, casoTesteEngarraf, passoAtual, idxLinha, continuar, leitura);
        }

        scanner.close();
    }

    private static void printCabecalhoPasso(int passoAtual) {
        switch (passoAtual) {
            case CABECALHO:
                System.out.println("Informe os parâmetros abaixo na mesma linha separados por espaço. Informe \"0 0\" para concluir:");
                System.out.println(" - Número de locais");
                System.out.println(" - Número de ruas");
                break;
            case RUAS:
                System.out.println("Informe os parâmetros abaixo na mesma linha separados por espaço:");
                System.out.println(" - Origem");
                System.out.println(" - Destino");
                System.out.println(" - Tempo");
                break;
            case FINAL:
                System.out.println("Informe os parâmetros abaixo na mesma linha separados por espaço:");
                System.out.println(" - Local inicial");
                System.out.println(" - Local final");
                break;
            default:
                break;
        }
    }

    private static CasoTesteEngarraf tratarLeitura(List<CasoTesteEngarraf> casosTesteEngarraf, CasoTesteEngarraf casoTesteEngarraf, AtomicInteger passoAtual, AtomicInteger idxLinha, AtomicBoolean continuar, String[] leitura) {
        switch (passoAtual.get()) {
            case CABECALHO:
                casoTesteEngarraf = criarCasoTesteEngarraf(leitura, idxLinha.get());
                continuar.set(!(0 == casoTesteEngarraf.getQuantidadeLocais() && 0 == casoTesteEngarraf.getQuantidadeRuas()));

                if (continuar.get()) {
                    casosTesteEngarraf.add(casoTesteEngarraf);
                }

                passoAtual.set(RUAS);
                break;

            case RUAS:
                popularRuas(casoTesteEngarraf, leitura, idxLinha.get());

                int qntdRuas = casoTesteEngarraf.getRuasPorLocalizacao().values().stream()
                        .map(Map::size)
                        .reduce(Integer::sum)
                        .orElse(0);

                if (qntdRuas == casoTesteEngarraf.getQuantidadeRuas()) {
                    passoAtual.set(FINAL);
                }
                break;
            case FINAL:
                popularLocalizacoesInicioFim(casoTesteEngarraf, leitura, idxLinha.get());

                passoAtual.set(CABECALHO);
                break;
            default:
        }
        return casoTesteEngarraf;
    }

    private static void erroParametrosInvalidos() {
        System.out.println("Parâmetros inválidos");
        System.exit(1);
    }
}
