
package securityservice.misc;

/**
 * Representa a lista de valores para ser utilizada nos campos e par�metros
 * do tipo Tipo de Opera��o.
 */
public class TipoOperacaoLog {

  /**
   * Constante que representa o valor Inclus�o.
   */
  static public final int INCLUSAO = 0;
  /**
   * Constante que representa o valor Altera��o.
   */
  static public final int ALTERACAO = 1;
  /**
   * Constante que representa o valor Exclus�o.
   */
  static public final int EXCLUSAO = 2;
  /**
   * Constante que representa o valor Execu��o.
   */
  static public final int EXECUCAO = 3;
  /**
   * Constante que representa o valor Todos.
   */
  static public final int TODOS = 4;
  /**
   * Lista de valores de pesquisa.
   */
  static public final String[] LOOKUP_LIST_FOR_FIELD = {"INCLUS�O",
                                                        "ALTERA��O",
                                                        "EXCLUS�O",
                                                        "EXECU��O"};
  /**
   * Lista de valores de pesquisa.
   */
  static public final String[] LOOKUP_LIST_FOR_PARAM = {"INCLUS�O",
                                                        "ALTERA��O",
                                                        "EXCLUS�O",
                                                        "EXECU��O",
                                                        "TODOS"};

  private TipoOperacaoLog() {
  }

}
