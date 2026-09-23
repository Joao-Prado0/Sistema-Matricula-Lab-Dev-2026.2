package interfaces;

import java.util.List;

public interface IArquivoRepository<T> {
    void salvar(T entidade);
    List<T> buscarTodos();
}
