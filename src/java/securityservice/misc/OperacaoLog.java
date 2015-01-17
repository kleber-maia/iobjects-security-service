package securityservice.misc;

/**
 * Representa a lista de valores para ser utilizada nos campos e par�metros
 * do tipo Natureza.
 */
public class OperacaoLog {

  /**
   * Constante que representa o valor Entrada por Compra.
   */
  static public final int INSER��O   = 0;
  /**
   * Constante que representa o valor Entrada por Ajuste.
   */
  static public final int ATUALIZA��O = 1;
  /**
   * Constante que representa o valor Entrada por Devolu��o.
   */
  static public final int DELE��O   = 2;
  /**
   * Constante que representa o valor Todos.
   */
  static public final int TODOS     = 3;
  /**
   * Lista de valores de pesquisa.
   */
  static public final String[] LOOKUP_LIST_FOR_FIELD = {"INSER��O",
                                                        "ATUALIZA��O",
                                                        "DELE��O"};
  /**
   * Lista de valores de pesquisa.
   */
  static public final String[] LOOKUP_LIST_FOR_PARAM = {"INSER��O",
                                                        "ATUALIZA��O",
                                                        "DELE��O",
                                                        "TODOS"};

  private OperacaoLog() {
  }

}
