package Sessoes;

import Salas.Sala;
import Filmes.Filme;
import java.util.Date;

public class Sessao {
    private int id;
    private Sala sala;
    private Filme filme;
    private Date data; // Horário de início
    private Date horarioFim; // Horário de término

    public Sessao(int id, Sala sala, Filme filme, Date data, Date horarioFim) {
        this.id = id;
        this.sala = sala;
        this.filme = filme;
        this.data = data;
        this.horarioFim = horarioFim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sala getSala() {
        return sala;
    }

    public Filme getFilme() {
        return filme;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(Date horarioFim) {
        this.horarioFim = horarioFim;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + '\n' +
                "Sala: " + this.sala.getNome() + '\n' +
                "Filme: " + this.filme.getNome() + '\n' +
                "Início: " + this.data + '\n' +
                "Término: " + this.horarioFim + '\n';
    }
}
