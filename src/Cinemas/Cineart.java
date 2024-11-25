package Cinemas;

import Exceptions.IdExistenteException;

import static Main.Main.cinemaDAO;
import static Main.Main.cinemas;

public class Cineart extends Cinema {
    private static Cineart instancia;

    Cineart(int id, String nome, String local){
        super(id, nome, local);
        try{
            cinemaDAO.existe(id);
            cinemas.add(this);
            cinemaDAO.salvar(this);
        }catch (IdExistenteException e){

        }

    }

    public static Cineart getInstance(int id, String nome, String local){
        if(instancia==null){
            instancia = new Cineart(id, nome, local);
        }
        return instancia;
    }
}
