
package securityservice.misc;

/**
 * Representa a lista de valores para ser utilizada nos campos e parâmetros
 * do tipo Tipo de Operação.
 */
public class TipoExpiracao {

  /**
   * Constante que representa o valor BLOQUEIO.
   */
  static public final int BLOQUEIO = 0;
  /**
   * Constante que representa o valor MUDANÇA DE SENHA.
   */
  static public final int MUDANCA_SENHA = 1;
  /**
   * Constante que representa o valor Todos.
   */
  static public final int TODOS = 3;
  /**
   * Lista de valores de pesquisa.
   */
  static public final String[] LOOKUP_LIST_FOR_FIELD = {"BLOQUEIO",
                                                        "MUDANÇA DE SENHA"};
  /**
   * Lista de valores de pesquisa.
   */
  static public final String[] LOOKUP_LIST_FOR_PARAM = {"BLOQUEIO",
                                                        "MUDANÇA DE SENHA",
                                                        "TODOS"};

  private TipoExpiracao() {
  }

}
