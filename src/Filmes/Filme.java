package Filmes;
import DAOs.FilmeDAO;
import Exceptions.IdExistenteException;

import static Main.Main.cinemaDAO;
import static Main.Main.filmeDAO;


public class Filme {
    private int id;
    private String nome;
    private long duracao_s;

    public Filme(int id, String nome, long duracao_s) {
        this.id = id;
        this.nome = nome;
        this.duracao_s = duracao_s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getDuracao_s() {
        return duracao_s;
    }

    public void setDuracao_s(long duracao_s) {
        this.duracao_s = duracao_s;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + '\n' + "Nome: " + this.nome + '\n' + "Duração: " + this.duracao_s + " segundos\n";
    }

}
