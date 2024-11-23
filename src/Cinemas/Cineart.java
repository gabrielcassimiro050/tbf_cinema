package Cinemas;

public class Cineart extends Cinema {
    private static Cineart instancia;

    private Cineart(int id, String nome, String local){
        super(id, nome, local);
    }

    public static Cineart getInstance(int id, String nome, String local){
        if(instancia == null){
            instancia = new Cineart(id, nome, local);
        }
        return instancia;
    }
}
