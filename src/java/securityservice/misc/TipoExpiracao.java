
package securityservice.misc;

/**
 * Representa a lista de valores para ser utilizada nos campos e par�metros
 * do tipo Tipo de Opera��o.
 */
public class TipoExpiracao {

  /**
   * Constante que representa o valor BLOQUEIO.
   */
  static public final int BLOQUEIO = 0;
  /**
   * Constante que representa o valor MUDAN�A DE SENHA.
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
                                                        "MUDAN�A DE SENHA"};
  /**
   * Lista de valores de pesquisa.
   */
  static public final String[] LOOKUP_LIST_FOR_PARAM = {"BLOQUEIO",
                                                        "MUDAN�A DE SENHA",
                                                        "TODOS"};

  private TipoExpiracao() {
  }

}
