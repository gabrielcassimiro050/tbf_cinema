package Vendas;

import Cinemas.Cinema;
import Sessoes.Sessao;

public class Venda {
    private int id;
    private Cinema cinema;
    private Sessao sessao;
    private float preco;

    public Venda(int id, Cinema cinema, Sessao sessao, float preco){
        this.id = id;
        this.cinema = cinema;
        this.sessao = sessao;
        this.preco = preco;

    }

    public int getId() {
        return id;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public float getPreco() {

    }
}
