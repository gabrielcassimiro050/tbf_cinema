package Cinemas;

public class Cineart extends Cinema {
    private static Cineart instancia;

    Cineart(int id, String nome, String local){
        super(id, nome, local);
    }

    public Cineart getInstance(int id, String nome, String local){
        if(instancia==null){
            instancia = new Cineart(id, nome, local);
        }
        return instancia;
    }
}
