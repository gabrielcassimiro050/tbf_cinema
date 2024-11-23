package Filmes;

import Connections.Conexao;
import DAOs.FilmeDAO;
import DAOs.MasterDAO;

import java.sql.*;
import java.util.ArrayList;

public class Filme {
    private int id;
    private String nome;
    private long duracao_s;
    private FilmeDAO filmeDAO;

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
