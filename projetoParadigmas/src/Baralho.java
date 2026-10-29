import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Guarda as cartas do jogo e sorteia a mão de cada jogador.
// Formato de cada linha: nome,tipo,cima,baixo,esquerda,direita
public class Baralho {

    private static final int CARTAS_POR_MAO = 5;

    private static final String[] CARTAS = {
            "Geezard,GRASS,1,1,5,4",
            "Funguar,GRASS,5,3,1,1",
            "Bite Bug,EARTH,1,5,3,3",
            "Red Bat,EARTH,6,2,1,1",
            "Blobra,WATER,2,5,3,3",
            "Gayla,ELECTRIC,2,4,1,4",
            "Gesper,EARTH,1,2,4,5",
            "Fastitocalon,EARTH,3,5,2,1",
            "Blood Soul,WATER,2,6,1,1",
            "Caterchipillar,GRASS,4,2,4,3",
            "Cockatrice,ELECTRIC,2,6,2,1",
            "Grat,GRASS,7,2,1,1",
            "Buel,EARTH,6,3,2,2",
            "Mesmerize,FIRE,3,4,5,4",
            "Glacial Eye,WATER,5,6,3,2",
            "Belhelmel,FIRE,3,5,3,4",
            "Thrustaevis,ELECTRIC,5,3,2,5",
            "Anacondaur,GRASS,5,5,3,1",
            "Creeps,ELECTRIC,5,2,5,3",
            "Grendel,ELECTRIC,4,4,5,2",
            "Jelleye,FIRE,3,2,1,7",
            "Grand Mantis,EARTH,5,2,5,5",
            "Forbidden,DRAGON,6,6,5,3",
            "Armadodo,DRAGON,6,3,6,5"
    };

    private List<Integer> disponiveis = new ArrayList<>();

    public Baralho() {
        for (int i = 0; i < CARTAS.length; i++) {
            disponiveis.add(i);
        }
        Collections.shuffle(disponiveis);
    }


    public List<Carta> sortearMao(Team time) {
        List<Carta> mao = new ArrayList<>();
        for (int i = 0; i < CARTAS_POR_MAO; i++) {
            int posicao = disponiveis.remove(0);
            mao.add(criarCarta(posicao, time));
        }
        return mao;
    }

    private Carta criarCarta(int posicao, Team time) {
        String[] partes = CARTAS[posicao].split(",");

        String nome = partes[0];
        Type tipo = Type.valueOf(partes[1]);
        int cima = Integer.parseInt(partes[2]);
        int baixo = Integer.parseInt(partes[3]);
        int esquerda = Integer.parseInt(partes[4]);
        int direita = Integer.parseInt(partes[5]);

        return new Carta(nome, posicao + 1, tipo, time, new Direcao(cima, baixo, esquerda, direita));
    }
}
