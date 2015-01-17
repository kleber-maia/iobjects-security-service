
package securityservice.misc;

/**
 * Representa a lista de valores para ser utilizada nos campos e parâmetros
 * do tipo Tipo de Operação.
 */
public class TipoOperacaoLog {

  /**
   * Constante que representa o valor Inclusão.
   */
  static public final int INCLUSAO = 0;
  /**
   * Constante que representa o valor Alteração.
   */
  static public final int ALTERACAO = 1;
  /**
   * Constante que representa o valor Exclusão.
   */
  static public final int EXCLUSAO = 2;
  /**
   * Constante que representa o valor Execução.
   */
  static public final int EXECUCAO = 3;
  /**
   * Constante que representa o valor Todos.
   */
  static public final int TODOS = 4;
  /**
   * Lista de valores de pesquisa.
   */
  static public final String[] LOOKUP_LIST_FOR_FIELD = {"INCLUSÃO",
                                                        "ALTERAÇÃO",
                                                        "EXCLUSÃO",
                                                        "EXECUÇÃO"};
  /**
   * Lista de valores de pesquisa.
   */
  static public final String[] LOOKUP_LIST_FOR_PARAM = {"INCLUSÃO",
                                                        "ALTERAÇÃO",
                                                        "EXCLUSÃO",
                                                        "EXECUÇÃO",
                                                        "TODOS"};

  private TipoOperacaoLog() {
  }

}
