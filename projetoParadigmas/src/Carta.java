import java.util.Objects;

public class Carta {

    private String nome;
    private int id;
    private Type tipo;
    private Team time;
    private Direcao direcao;

    public Carta(String nome, int id, Type tipo, Team time, Direcao direcao) {
        this.nome = nome;
        this.id = id;
        this.tipo = tipo;
        this.time = time;
        this.direcao = direcao;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public Type getTipo() {
        return tipo;
    }

    public Team getTime() {
        return time;
    }

    public Direcao getDirecao() {
        return direcao;
    }

    // A carta passa para o time adversário (captura)
    public void trocarTime() {
        if (time == Team.AZUL) {
            time = Team.VERDE;
        } else {
            time = Team.AZUL;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carta carta = (Carta) o;
        return id == carta.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("#%02d %s (%s) [%s]", id, nome, tipo, direcao);
    }
}
