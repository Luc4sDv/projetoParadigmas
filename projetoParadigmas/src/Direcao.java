public class Direcao {


    public static final int CIMA = 0;
    public static final int BAIXO = 1;
    public static final int ESQUERDA = 2;
    public static final int DIREITA = 3;

    private static final int VALOR_MAXIMO = 10; // o 10 é mostrado como 'A'

    private int cima;
    private int baixo;
    private int esquerda;
    private int direita;

    public Direcao(int cima, int baixo, int esquerda, int direita) {
        if (cima < 0 || cima > VALOR_MAXIMO ||
                baixo < 0 || baixo > VALOR_MAXIMO ||
                esquerda < 0 || esquerda > VALOR_MAXIMO ||
                direita < 0 || direita > VALOR_MAXIMO) {

            throw new IllegalArgumentException("Os valores devem estar entre 0 e 10.");
        }

        this.cima = cima;
        this.baixo = baixo;
        this.esquerda = esquerda;
        this.direita = direita;
    }

    public int getCima() {
        return cima;
    }

    public int getBaixo() {
        return baixo;
    }

    public int getEsquerda() {
        return esquerda;
    }

    public int getDireita() {
        return direita;
    }

    // Devolve o valor de um lado, usando as constantes CIMA, BAIXO, ESQUERDA e DIREITA
    public int getValor(int lado) {
        switch (lado) {
            case CIMA:
                return cima;
            case BAIXO:
                return baixo;
            case ESQUERDA:
                return esquerda;
            default:
                return direita;
        }
    }

    // Regra Ascension: cada lado ganha 1 ponto (máximo 10, que é o 'A')
    public void aumentarUmEmCadaLado() {
        cima = Math.min(VALOR_MAXIMO, cima + 1);
        baixo = Math.min(VALOR_MAXIMO, baixo + 1);
        esquerda = Math.min(VALOR_MAXIMO, esquerda + 1);
        direita = Math.min(VALOR_MAXIMO, direita + 1);
    }

    // Transforma o número em texto: 10 vira "A"
    public static String simbolo(int valor) {
        return valor == VALOR_MAXIMO ? "A" : String.valueOf(valor);
    }

    @Override
    public String toString() {
        return "cima " + simbolo(cima) +
                " | baixo " + simbolo(baixo) +
                " | esq " + simbolo(esquerda) +
                " | dir " + simbolo(direita);
    }
}
