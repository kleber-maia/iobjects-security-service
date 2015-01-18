/*
The MIT License (MIT)

Copyright (c) 2008 Kleber Maia de Andrade

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/   

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
