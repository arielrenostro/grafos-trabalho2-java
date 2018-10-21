package br.furb.grafos.problemas.color11;

import br.furb.grafos.problemas.color11.dto.Coordenada;
import br.furb.grafos.problemas.color11.dto.ParametrosColor11DTO;
import br.furb.grafos.problemas.color11.problema.Color11;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author ariel e sidnei
 */
public class Main {

    private static final String CAMINHO_ARQUIVO_PARAMETROS = "C:\\temp\\entrada.in";

    public static void main(String[] args) throws Exception {
        System.out.println("Problema de grafos Color11");
        System.out.println("Resolução por: Ariel e Sidnei");
        System.out.println();

        ParametrosColor11DTO parametrosColor11 = new ParametrosColor11DTO();
        if (isTemArquivoConfiguracao()) {
            popularParametrosPorArquivo(parametrosColor11);
        } else {
            popularParametrosPorTerminal(parametrosColor11);
        }

        int quantidade = new Color11().resolver(parametrosColor11);
        System.out.println("Resultado: " + quantidade);
    }

    private static boolean isTemArquivoConfiguracao() {
        Path path = Paths.get(CAMINHO_ARQUIVO_PARAMETROS);
        return Files.exists(path);
    }

    private static void popularParametrosPorArquivo(ParametrosColor11DTO parametrosColor11) throws IOException {
        Path path = Paths.get(CAMINHO_ARQUIVO_PARAMETROS);
        BufferedReader bufferedReader = Files.newBufferedReader(path);

        String linha1 = bufferedReader.readLine();
        popularParametrosLinha1(parametrosColor11, linha1);

        int linhaAtual = 0;
        while (linhaAtual < parametrosColor11.getQuantidadeQuadradosPintados()) {
            String leitura = bufferedReader.readLine();
            if (null == leitura || leitura.isEmpty()) {
                System.out.println("Linha " + (linhaAtual + 2));
                erroParametrosInvalidos();
            }
            String[] parametros = leitura.split("\\s");
            if (parametros.length != 2) {
                System.out.println("Linha " + (linhaAtual + 2));
                erroParametrosInvalidos();
            }
            Integer linha = Integer.valueOf(parametros[0]) - 1;
            Integer coluna = Integer.valueOf(parametros[1]) - 1;
            parametrosColor11.adicionarCoordenada(linha, coluna);
            linhaAtual++;
        }

        bufferedReader.close();
    }

    private static void popularParametrosPorTerminal(ParametrosColor11DTO parametrosColor11) {
        System.out.println("Informe os parâmetros abaixo na mesma linha separados por espaço:");
        System.out.println(" - Número de linhas");
        System.out.println(" - Número de colunas");
        System.out.println(" - Coordenada da linha onde a criança irá iniciar o desenho");
        System.out.println(" - Coordenada da coluna onde a criança irá iniciar o desenho");
        System.out.println(" - Quantidade de quadrados pintados");

        Scanner scanner = new Scanner(System.in);

        lerParametrosLinha1Terminal(scanner, parametrosColor11);
        lerParametrosCoordenadasTerminal(scanner, parametrosColor11);

        scanner.close();
    }

    private static void lerParametrosCoordenadasTerminal(Scanner scanner, ParametrosColor11DTO parametrosColor11) {
        int linhaAtual = 0;
        while (linhaAtual < parametrosColor11.getQuantidadeQuadradosPintados()) {
            System.out.print("Informe a linha " + (linhaAtual + 1) + ": ");

            String leitura = scanner.nextLine();
            if (null == leitura || leitura.isEmpty()) {
                System.out.println("Informe novamente!");
                continue;
            }

            String[] parametros = leitura.split("\\s");
            if (parametros.length != 2) {
                System.out.println("Informe novamente!");
                continue;
            }

            Integer linha = Integer.valueOf(parametros[0]) - 1;
            Integer coluna = Integer.valueOf(parametros[1]) - 1;
            parametrosColor11.adicionarCoordenada(linha, coluna);
            linhaAtual++;
        }
    }

    private static void lerParametrosLinha1Terminal(Scanner scanner, ParametrosColor11DTO parametrosColor11) {
        String leitura = scanner.nextLine();
        if (null == leitura || leitura.isEmpty()) {
            erroParametrosInvalidos();
        }

        popularParametrosLinha1(parametrosColor11, leitura);
    }

    private static void popularParametrosLinha1(ParametrosColor11DTO parametrosColor11, String leitura) {
        String[] parametros = leitura.split("\\s");
        if (parametros.length != 5) {
            erroParametrosInvalidos();
        }

        try {
            int numeroLinhas = Integer.valueOf(parametros[0]);
            int numeroColunas = Integer.valueOf(parametros[1]);
            int quantidadeQuadradosPintados = Integer.valueOf(parametros[4]);

            int linha = Integer.valueOf(parametros[2]) - 1;
            int coluna = Integer.valueOf(parametros[3]) - 1;
            Coordenada coordenadaInicio = new Coordenada(linha, coluna);

            parametrosColor11.setNumeroLinhas(numeroLinhas);
            parametrosColor11.setNumeroColunas(numeroColunas);
            parametrosColor11.setCoordenadaInicio(coordenadaInicio);
            parametrosColor11.setQuantidadeQuadradosPintados(quantidadeQuadradosPintados);
        } catch (Exception e) {
            e.printStackTrace();
            erroParametrosInvalidos();
        }
    }

    private static void erroParametrosInvalidos() {
        System.out.println("Parâmetros inválidos");
        System.exit(1);
    }
}
