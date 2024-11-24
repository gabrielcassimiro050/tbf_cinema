package DAOs;
import Cinemas.Cinema;
import Sessoes.Sessao;

import java.util.ArrayList;

public interface MasterDAO<T> {
    T salvar(T objeto);
    T buscarPorId(int id);
    ArrayList<T> buscarTodos();
    void atualizar(T object);
    void deletar(T object);
    void criarTabela();
}
