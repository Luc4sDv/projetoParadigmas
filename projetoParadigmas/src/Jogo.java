import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Controla a partida. Regras adicionais escolhidas (sempre ativas):
//  - All Open : as mãos dos dois jogadores ficam visíveis
//  - Order    : as cartas só podem ser jogadas na ordem em que aparecem na mão
//  - Swap     : no início, uma carta de cada jogador é trocada
//  - Ascension: ao jogar uma carta, todas as cartas do mesmo tipo no tabuleiro ganham +1 em cada lado
public class Jogo {

    private Scanner teclado = new Scanner(System.in);
    private Tabuleiro tabuleiro = new Tabuleiro();
    private Jogador jogadorAzul;
    private Jogador jogadorVerde;

    public void iniciar() {
        System.out.println("=== TRIPLE TRIAD ===");
        criarJogadores();
        trocarCartaInicial(); // regra Swap

        Jogador atual = jogadorAzul;
        Jogador oponente = jogadorVerde;

        while (!tabuleiro.estaCheio()) {
            jogarTurno(atual, oponente);

            Jogador auxiliar = atual;
            atual = oponente;
            oponente = auxiliar;
        }

        mostrarResultado();
    }

    private void criarJogadores() {
        Baralho baralho = new Baralho();

        System.out.print("Nome do jogador AZUL: ");
        String nomeAzul = teclado.nextLine();
        System.out.print("Nome do jogador VERDE: ");
        String nomeVerde = teclado.nextLine();

        jogadorAzul = new Jogador(nomeAzul, Team.AZUL, baralho.sortearMao(Team.AZUL));
        jogadorVerde = new Jogador(nomeVerde, Team.VERDE, baralho.sortearMao(Team.VERDE));
    }

    // Regra Swap: uma carta sorteada de cada mão troca de dono
    private void trocarCartaInicial() {
        Random sorteio = new Random();
        int posicaoAzul = sorteio.nextInt(jogadorAzul.getMao().size());
        int posicaoVerde = sorteio.nextInt(jogadorVerde.getMao().size());

        Carta cartaAzul = jogadorAzul.getMao().get(posicaoAzul);
        Carta cartaVerde = jogadorVerde.getMao().get(posicaoVerde);

        jogadorAzul.substituirCarta(posicaoAzul, cartaVerde);
        jogadorVerde.substituirCarta(posicaoVerde, cartaAzul);

        // cada carta agora pertence ao outro time
        cartaAzul.trocarTime();
        cartaVerde.trocarTime();

        System.out.println("\nSwap! " + jogadorAzul.getNome() + " deu " + cartaAzul.getNome()
                + " e recebeu " + cartaVerde.getNome() + ".");
    }

    private void jogarTurno(Jogador atual, Jogador oponente) {
        System.out.println("\n==============================================");
        System.out.println("Vez de " + atual.getNome() + " (" + atual.getTime() + ")");
        System.out.println(tabuleiro);

        mostrarMao(atual);
        mostrarMao(oponente); // regra All Open

        // regra Order: sempre a primeira carta da mão
        Carta carta = atual.getMao().get(0);
        System.out.println("\nCarta da vez: " + carta);

        int linha;
        int coluna;
        do {
            linha = lerNumero("Linha (1 a 3): ") - 1;
            coluna = lerNumero("Coluna (1 a 3): ") - 1;
        } while (!posicaoValida(carta, linha, coluna));

        atual.getMao().remove(0);

        int afetadas = tabuleiro.aplicarAscension(carta.getTipo());
        if (afetadas > 0) {
            System.out.println("Ascension! " + afetadas + " carta(s) do tipo "
                    + carta.getTipo() + " ganharam +1 em cada lado.");
        }

        int capturas = tabuleiro.capturarAdjacentes(linha, coluna);
        atual.ganharPontos(capturas);
        oponente.perderPontos(capturas);
        System.out.println(atual.getNome() + " capturou " + capturas + " carta(s).");
    }

    // Tenta colocar a carta; se não der, avisa o jogador
    private boolean posicaoValida(Carta carta, int linha, int coluna) {
        boolean colocou = tabuleiro.posicionarCarta(carta, linha, coluna);
        if (!colocou) {
            System.out.println("Posição inválida ou já ocupada. Tente de novo.");
        }
        return colocou;
    }

    private void mostrarMao(Jogador jogador) {
        System.out.println("Mão de " + jogador.getNome() + " (" + jogador.getTime() + "):");
        List<Carta> mao = jogador.getMao();
        for (int i = 0; i < mao.size(); i++) {
            System.out.println("  " + (i + 1) + ") " + mao.get(i));
        }
    }

    // Lê um número de 1 a 3, repetindo a pergunta se a entrada for inválida
    private int lerNumero(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = teclado.nextLine().trim();
            try {
                int numero = Integer.parseInt(entrada);
                if (numero >= 1 && numero <= 3) {
                    return numero;
                }
            } catch (NumberFormatException e) {
                // cai na mensagem abaixo
            }
            System.out.println("Digite um número de 1 a 3.");
        }
    }

    private void mostrarResultado() {
        int azul = tabuleiro.contarCartas(Team.AZUL);
        int verde = tabuleiro.contarCartas(Team.VERDE);

        System.out.println("\n=== FIM DE JOGO ===");
        System.out.println(tabuleiro);
        System.out.println(jogadorAzul.getNome() + " (AZUL): " + azul
                + " células | " + jogadorAzul.getPontos() + " pontos");
        System.out.println(jogadorVerde.getNome() + " (VERDE): " + verde
                + " células | " + jogadorVerde.getPontos() + " pontos");

        if (azul > verde) {
            System.out.println("Vencedor: " + jogadorAzul.getNome() + "!");
        } else if (verde > azul) {
            System.out.println("Vencedor: " + jogadorVerde.getNome() + "!");
        } else {
            System.out.println("Empate!");
        }
    }
}
