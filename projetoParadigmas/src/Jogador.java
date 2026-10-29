import java.util.List;

public class Jogador {

    private static final int PONTOS_INICIAIS = 5;

    private String nome;
    private int pontos;
    private List<Carta> mao;
    private Team time;

    public Jogador(String nome, Team time, List<Carta> mao) {
        this.nome = nome;
        this.time = time;
        this.mao = mao;
        this.pontos = PONTOS_INICIAIS;
    }

    public String getNome() {
        return nome;
    }

    public int getPontos() {
        return pontos;
    }

    public List<Carta> getMao() {
        return mao;
    }

    public Team getTime() {
        return time;
    }

    public void ganharPontos(int quantidade) {
        pontos += quantidade;
    }

    public void perderPontos(int quantidade) {
        pontos -= quantidade;
    }

    // Coloca uma carta nova no lugar da carta da posição indicada e devolve a antiga (usado no Swap)
    public Carta substituirCarta(int posicao, Carta nova) {
        return mao.set(posicao, nova);
    }
}
