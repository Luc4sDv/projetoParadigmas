public class Tabuleiro {

    private static final int TAMANHO = 3;
    private static final int LARGURA_CELULA = 9;

    // Para cada lado da carta jogada (cima, baixo, esquerda, direita):
    // quanto andar na linha/coluna para achar a vizinha e qual lado dela encosta na carta jogada
    private static final int[] DESLOCAMENTO_LINHA = {-1, 1, 0, 0};
    private static final int[] DESLOCAMENTO_COLUNA = {0, 0, -1, 1};
    private static final int[] LADO_OPOSTO = {
            Direcao.BAIXO, Direcao.CIMA, Direcao.DIREITA, Direcao.ESQUERDA
    };

    private Carta[][] tabuleiro;

    public Tabuleiro() {
        tabuleiro = new Carta[TAMANHO][TAMANHO];
    }

    public boolean posicionarCarta(Carta carta, int linha, int coluna) {
        if (linha < 0 || linha >= TAMANHO || coluna < 0 || coluna >= TAMANHO) {
            return false;
        }
        if (tabuleiro[linha][coluna] != null) {
            return false;
        }
        tabuleiro[linha][coluna] = carta;
        return true;
    }

    public Carta getCarta(int linha, int coluna) {
        if (linha < 0 || linha >= TAMANHO || coluna < 0 || coluna >= TAMANHO) {
            return null;
        }
        return tabuleiro[linha][coluna];
    }

    public boolean estaCheio() {
        for (int linha = 0; linha < TAMANHO; linha++) {
            for (int coluna = 0; coluna < TAMANHO; coluna++) {
                if (tabuleiro[linha][coluna] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    // Quantas cartas de um time estão no tabuleiro
    public int contarCartas(Team time) {
        int total = 0;
        for (int linha = 0; linha < TAMANHO; linha++) {
            for (int coluna = 0; coluna < TAMANHO; coluna++) {
                Carta carta = tabuleiro[linha][coluna];
                if (carta != null && carta.getTime() == time) {
                    total++;
                }
            }
        }
        return total;
    }

    // Regra básica: a carta jogada captura cada vizinha adversária
    // cujo lado encostado tenha valor MENOR. Devolve quantas cartas foram capturadas.
    public int capturarAdjacentes(int linha, int coluna) {
        Carta jogada = tabuleiro[linha][coluna];
        int capturas = 0;

        for (int lado = 0; lado < 4; lado++) {
            Carta vizinha = getCarta(linha + DESLOCAMENTO_LINHA[lado],
                                     coluna + DESLOCAMENTO_COLUNA[lado]);

            if (vizinha == null || vizinha.getTime() == jogada.getTime()) {
                continue; // não tem carta ali ou a carta já é nossa
            }

            int valorJogada = jogada.getDirecao().getValor(lado);
            int valorVizinha = vizinha.getDirecao().getValor(LADO_OPOSTO[lado]);

            if (valorJogada > valorVizinha) {
                vizinha.trocarTime();
                capturas++;
            }
        }
        return capturas;
    }

    // Regra Ascension: todas as cartas do tabuleiro com o mesmo tipo ganham +1 em cada lado.
    // Devolve quantas cartas foram afetadas.
    public int aplicarAscension(Type tipo) {
        int afetadas = 0;
        for (int linha = 0; linha < TAMANHO; linha++) {
            for (int coluna = 0; coluna < TAMANHO; coluna++) {
                Carta carta = tabuleiro[linha][coluna];
                if (carta != null && carta.getTipo() == tipo) {
                    carta.getDirecao().aumentarUmEmCadaLado();
                    afetadas++;
                }
            }
        }
        return afetadas;
    }

    // Desenho do tabuleiro: cada célula tem 4 linhas de texto (cima / esquerda e direita / baixo / time)
    @Override
    public String toString() {
        String separador = "+---------+---------+---------+\n";
        StringBuilder texto = new StringBuilder(separador);

        for (int linha = 0; linha < TAMANHO; linha++) {
            for (int linhaDoTexto = 0; linhaDoTexto < 4; linhaDoTexto++) {
                for (int coluna = 0; coluna < TAMANHO; coluna++) {
                    texto.append("|").append(textoDaCelula(linha, coluna, linhaDoTexto));
                }
                texto.append("|\n");
            }
            texto.append(separador);
        }
        return texto.toString();
    }

    private String textoDaCelula(int linha, int coluna, int linhaDoTexto) {
        Carta carta = tabuleiro[linha][coluna];

        if (carta == null) {
            // célula vazia: mostra só a posição (linha,coluna) que o jogador deve digitar
            if (linhaDoTexto == 1) {
                return centralizar("(" + (linha + 1) + "," + (coluna + 1) + ")");
            }
            return centralizar("");
        }

        Direcao direcao = carta.getDirecao();
        switch (linhaDoTexto) {
            case 0:
                return centralizar(Direcao.simbolo(direcao.getCima()));
            case 1:
                return String.format("  %s   %s  ",
                        Direcao.simbolo(direcao.getEsquerda()),
                        Direcao.simbolo(direcao.getDireita()));
            case 2:
                return centralizar(Direcao.simbolo(direcao.getBaixo()));
            default:
                return centralizar(carta.getTime().toString());
        }
    }

    private String centralizar(String texto) {
        int espacos = LARGURA_CELULA - texto.length();
        int esquerda = espacos / 2;
        return " ".repeat(esquerda) + texto + " ".repeat(espacos - esquerda);
    }
}
