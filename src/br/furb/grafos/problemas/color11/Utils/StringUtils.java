package br.furb.grafos.problemas.color11.Utils;

/**
 * @author ariel e sidnei
 */
public abstract class StringUtils {

    public static void concatenarComSeparador(StringBuilder erros, String string) {
        if (0 >= erros.length()) {
            erros.append(", ");
        }
        erros.append(string);
    }
}
