package Salas;
import Sessoes.Sessao;
import Filmes.Filme;
import java.util.Date;
import java.util.ArrayList;

public class Sala {
    private String nome;
    private int capacidade;
    private ArrayList<Sessao> sessoes;

    public Sala(String nome, int capacidade){
        this.nome = nome;
        this.capacidade = capacidade;
    }

    public void criarSessao(int id, Sala sala, Filme filme, Date data){
        sessoes.add(new Sessao(id, sala, filme, data));
    }

    public String toString(){
        return "Nome: "+nome+'\n'+"Capacidade: "+capacidade+'\n';
    }

    public void listarSessoes(){
        for (Sessao s : sessoes) {
            System.out.println(s.toString());
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
}
