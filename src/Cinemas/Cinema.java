package Cinemas;

import Connections.Conexao;
import DAOs.MasterDAO;
import Salas.Sala;

import java.sql.*;
import java.util.ArrayList;

public class Cinema {
    private int id;
    private String nome, local;
    private ArrayList<Sala> salas;

    public Cinema(int id, String nome, String local) {
        this.id = id;
        this.nome = nome;
        this.local = local;
        this.salas = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLocal() {
        return local;
    }



    public void criarSala(String nome, int capacidade) {
        Sala novaSala = new Sala(nome, capacidade);
    }


    public void listarSalas() {
        ArrayList<Object> salasDoBanco = salaDAO.buscarTodas();

        if (salasDoBanco == null || salasDoBanco.isEmpty()) {
            System.out.println("Não há salas cadastradas.");
            return;
        }

        // Imprime as salas
        for (Object obj : salasDoBanco) {
            if (obj instanceof Sala) {
                Sala sala = (Sala) obj;
                System.out.println(sala.toString());
            }
        }
    }


    @Override
    public String toString() {
        return "ID: " + this.id + '\n' + "Nome: " + this.nome + '\n' + "Local: " + this.local + '\n';
    }
}
