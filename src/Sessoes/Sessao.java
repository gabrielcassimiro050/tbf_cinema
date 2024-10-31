package Sessoes;

import Salas.Sala;
import Filmes.Filme;
import java.util.Date;

public class Sessao {
    private int id;
    private Sala sala;
    private Filme filme;
    private Date data;

    public Sessao(int id, Sala sala, Filme filme, Date data){
        this.id = id;
        this.sala = sala;
        this.filme = filme;
        this.data = data;
    }

    public String toString(){
        return "ID: "+this.id+'\n'+"Nome: "+this.sala.getNome()+'\n'+"Filmes.Filme: "+this.filme.getNome()+'\n';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
