package securityservice.misc;

/**
 * Representa a lista de valores para ser utilizada nos campos e parâmetros
 * do tipo Natureza.
 */
public class OperacaoLog {

  /**
   * Constante que representa o valor Entrada por Compra.
   */
  static public final int INSERÇÃO   = 0;
  /**
   * Constante que representa o valor Entrada por Ajuste.
   */
  static public final int ATUALIZAÇÃO = 1;
  /**
   * Constante que representa o valor Entrada por Devolução.
   */
  static public final int DELEÇÃO   = 2;
  /**
   * Constante que representa o valor Todos.
   */
  static public final int TODOS     = 3;
  /**
   * Lista de valores de pesquisa.
   */
  static public final String[] LOOKUP_LIST_FOR_FIELD = {"INSERÇÃO",
                                                        "ATUALIZAÇÃO",
                                                        "DELEÇÃO"};
  /**
   * Lista de valores de pesquisa.
   */
  static public final String[] LOOKUP_LIST_FOR_PARAM = {"INSERÇÃO",
                                                        "ATUALIZAÇÃO",
                                                        "DELEÇÃO",
                                                        "TODOS"};

  private OperacaoLog() {
  }

}
