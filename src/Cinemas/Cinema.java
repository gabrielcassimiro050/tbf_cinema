package Cinemas;
import Salas.Sala;

import java.util.ArrayList;

public abstract class Cinema {
    private int id;
    private String nome, local;
    private static ArrayList<Cinema> cinemas;
    private ArrayList<Sala> salas;

    public Cinema(int id, String nome, String local){
        this.id = id;
        this.nome = nome;
        this.local = local;
    }

    public void criarSala(String nome, int capacidade){
        salas.add(new Sala(nome, capacidade));
    }

    public void listarSalas(){
        for(Sala s : salas){
            System.out.println(s.toString());
        }
    }

    public String toString(){
        return "ID: "+this.id+'\n'+"Nome: "+this.nome+'\n'+"Local: "+this.local+'\n';
    }

     public static void listarCinemas(){
        for(Cinema c : cinemas){
            System.out.println(c.toString());
        }
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

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
