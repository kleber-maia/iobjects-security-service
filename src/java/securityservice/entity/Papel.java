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

package securityservice.entity;

import java.sql.*;
import java.io.*;
import java.util.*;

import iobjects.*;
import iobjects.entity.*;
import iobjects.util.*;

import securityservice.*;

/**
 * Representa a entidade Papel no banco de dados da aplicação.
 */
public class Papel extends Entity {

  // identificação da classe
  static public final String CLASS_NAME = "securityservice.entity.Papel";
  // nossa ajuda
  static public final String HELP = "Este é o principal recurso para a definição da política "
                                  + "de segurança da aplicação. Uma boa idéia é criar um Papel "
                                  + "para cada departamento ou cargo da empresa e definir seus "
                                  + "direitos. Por padrão um novo Papel não possui nenhum direito.";
  // nossas ações
  static public final Action ACTION                = new Action("papel", "Papel", "Mantém o cadastro de Papéis que podem ser exercidos pelos Usuários da aplicação e seus direitos.", HELP, "entity/papel.jsp", "Security Service", "", Action.CATEGORY_ENTITY, false, false);
  static public final Action ACTION_CADASTRO       = new Action("papelCadastro", "", "", "", "entity/papelcadastro.jsp", "", "", Action.CATEGORY_ENTITY, false, false);
  static public final Action ACTION_TABELA_HORARIO = new Action("papelTabelaHorario", "", "", "", "entity/papeltabelahorario.jsp", "", "", Action.CATEGORY_ENTITY, false, false);
  // nossos comandos
  static public final Command COMMAND_DELETE = ACTION.addCommand(new Command(Command.COMMAND_DELETE, "Excluir", "Exclui o Papel selecionado."));
  static public final Command COMMAND_EDIT   = ACTION.addCommand(new Command(Command.COMMAND_EDIT,   "Editar",  "Edita o Papel selecionado."));
  static public final Command COMMAND_INSERT = ACTION.addCommand(new Command(Command.COMMAND_INSERT, "Inserir", "Insere um novo Papel."));
  static public final Command COMMAND_SAVE   = ACTION.addCommand(new Command(Command.COMMAND_SAVE,   "Salvar",  "Salva o Papel que está sendo editado ou inserido."));
  static public final Command COMMAND_SAVE_TABELA_HORARIO = ACTION_TABELA_HORARIO.addCommand(new Command(Command.COMMAND_SAVE, "Salvar", "Salva os Horários que estão sendo editados."));
  // nossos campos
  static public final EntityField FIELD_PAPEL_ID = new EntityField("in_papel_id", "Papel Id", "", "papelId", Types.INTEGER, 4, 0, true, EntityField.ALIGN_RIGHT, false, true, EntityField.FORMAT_INTEGER, "", true);
  static public final EntityField FIELD_NOME = new EntityField("va_nome", "Nome", "Informe o Nome.", "nome", Types.VARCHAR, 255, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_NONE, "", true, "", "value != ''", "Obrigatório informar o Nome;");
  static public final EntityField FIELD_DESCRICAO = new EntityField("va_descricao", "Descrição", "Informe a Descrição.", "descricao", Types.VARCHAR, 255, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_NONE, "", false);
  static public final EntityField FIELD_PRIVILEGIADO = new EntityField("sm_privilegiado", "Privilegiado", "Selecione se o Papel é Privilegiado. Se sim, usuários associados ao Papel terão direitos de administrador da aplicação.", "privilegiado", Types.SMALLINT, 2, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_INTEGER, "", false);
  static public final EntityField FIELD_TABELA_HORARIO = new EntityField("ch_tabela_horario", "Tabela de Horários", "Marque os horários nos quais os usuários associados ao Papel poderão efetuar logon.", "tabelaHorario", Types.CHAR, 21, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_NONE, "", false);
  static public final EntityField FIELD_HASH = new EntityField("in_hash", "Hash", "", "hash", Types.INTEGER, 4, 0, false, EntityField.ALIGN_RIGHT, true, true, EntityField.FORMAT_INTEGER, "", false);

  static public final String TABELA_HORARIO_VAZIA     = "000000000000000000000000000000000000000000";
  static public final String TABELA_HORARIO_COMERCIAL = "00000003ff0003ff0003ff0003ff0003ff00000000";

  static public final String[] NAO_SIM = {"Não", "Sim"};
  static public final int NAO = 0;
  static public final int SIM = 1;

  {
    // define os valores de pesquisa dos campos
    FIELD_PRIVILEGIADO.setLookupList(NAO_SIM);
  }

  /**
   * Construtor padrão.
   */
  public Papel() {
    // nossas ações
    actionList().add(ACTION);
    actionList().add(ACTION_CADASTRO);
    actionList().add(ACTION_TABELA_HORARIO);
    // nossa tabela
    setTableName("securityservice_papel");
    // nossos campos
    fieldList().add(FIELD_PAPEL_ID);
    fieldList().add(FIELD_NOME);
    fieldList().add(FIELD_DESCRICAO);
    fieldList().add(FIELD_PRIVILEGIADO);
    fieldList().add(FIELD_TABELA_HORARIO);
    fieldList().add(FIELD_HASH);
  }

  /**
   * Retorna true se é permitido efetuar logon na 'dataHora' especificada
   * de acordo com 'tabelaHorario'.
   * @param tabelaHorario String Tabela de Horário.
   * @param dataHora Date Data/hora.
   * @return boolean Retorna true se é permitido efetuar logon na 'dataHora'
   *                especificada de acordo com 'tabelaHorario'.
   */
  static public boolean canLogonAtDateTime(String         tabelaHorario,
                                           java.util.Date dataHora) {
    // nosso calendário
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(dataHora);
    // dia da semana
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
    // hora do dia
    int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
    // obtém a representação numérica (hexadecimal) do dia da semana
    int intDay = Integer.decode("0x" + tabelaHorario.substring((dayOfWeek*6), (dayOfWeek*6)+6)).intValue();
    // bit que representa a hora desejada durante o dia
    int intHour = (int)Math.pow(2, hourOfDay);
    // verifica se o bit que representa a hora neste dia está marcado
    return (intDay & intHour) == intHour;
  }

  /**
   * Exclui o(a) Papel informado(a) por 'papelInfo'.
   * @param papelInfo PapelInfo referente a(o) Papel
   *        que se deseja excluir.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   * @author Kleber Maia, 4/5/2005
   */
  public void delete(PapelInfo papelInfo) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // exclui os registros de PapelAcao relacionados
      PapelAcao papelAcao = (PapelAcao)getFacade().getEntity(PapelAcao.CLASS_NAME);
      papelAcao.deleteByPapelId(papelInfo.getPapelId());
      // exclui os registros de PapelRelacaoMestre relacionados
      PapelRelacaoMestre papelRelacaoMestre = (PapelRelacaoMestre)getFacade().getEntity(PapelRelacaoMestre.CLASS_NAME);
      papelRelacaoMestre.deleteByPapelId(papelInfo.getPapelId());
      // exclui os registros de PapelUsuario relacionados
      PapelUsuario papelUsuario = (PapelUsuario)getFacade().getEntity(PapelUsuario.CLASS_NAME);
      papelUsuario.deleteByPapelId(papelInfo.getPapelId());
      // exclui o registro
      super.delete(papelInfo);
      // grava tudo
      getFacade().commitTransaction();
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // lança exceção
      throw e;
    } // try-catch
  }

  /**
   * Retorna um boolean[7][24], onde a primeira dimensão representa os dias
   * da semana (domgingo a segunda) e a segunda dimensão as horas de cada dia
   * (0h a 23h) da 'tabelaHorario'.
   * @param tabelaHorario String Representação hexadecimal da Tabela Horário.
   * @return boolean[][]
   */
  public boolean[][] decodeTabelaHorario(String tabelaHorario) {
    // nosso resultado
    boolean[][] result = new boolean[7][24];
    // obtém os horários permitidos por dia
    for (int i=0; i<7; i++) {
      // representação numérica (hexadecimal) do dia
      int intDay = Integer.decode("0x" + tabelaHorario.substring(i*6, (i*6)+6)).intValue();
      // loop nas horas do dia
      for (int w=0; w<24; w++) {
        // bit que representa a hora no dia
        int bitHour = (int)Math.pow(2, w);
        // marca o dia e a hora no array
        result[i][w] = (intDay & bitHour) == bitHour;
      } // for w
    } // for i
    // retorna
    return result;
  }

  /**
   * Retorna uma String contendo a representação hexadecimal de 'tabelaHorario'.
   * @param tabelaHorario boolean[][] contendo os horários permitidos por
   *                      dia da semana.
   * @return String Retorna uma String contendo a representação hexadecimal de
   *                'tabelaHorario'.
   * @see decodeTabelaHorario()
   */
  public String encodeTabelaHorario(boolean[][] tabelaHorario) {
    // tamanho mínimo da representação de cada dia
    String zeros = "0000000";
    // nosso resultado
    StringBuffer result = new StringBuffer();
    // loop no nosso array
    for (int i=0; i<7; i++) {
      // representação numérica do dia
      int intDay = 0;
      // marca os bits referentes aos horários marcados no dia
      for (int w=0; w<24; w++) {
        if (tabelaHorario[i][w])
          intDay += (int)Math.pow(2, w);
      } // if
      // adiciona a representação hexadecimal do dia à tabela horário
      // adicionando os zeros necessários na frente
      String strDay = Integer.toHexString(intDay);
      result.append(zeros.substring(0, 6-strDay.length()) + strDay);
    } // for
    // retorna
    return result.toString();
  }

  /**
   * Insere o(a) Papel identificado(a) por 'papelInfo'.
   * @param papelInfo PapelInfo contendo as informações do(a) Papel que se
   *                    deseja incluir.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public void insert(PapelInfo papelInfo) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // valida
      validate(papelInfo);
      // define o Id
      if (papelInfo.getPapelId() == 0)
        papelInfo.setPapelId(getNextSequence(FIELD_PAPEL_ID));
      // insere o registro
      super.insert(papelInfo);
      // salva tudo
      getFacade().commitTransaction();
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // mostra exceção
      throw e;
    } // try-catch
  }

  /**
   * Retorna um PapelInfo referente a(o) Papel
   * indicado(a) pelos parâmetros que representam sua chave primária.
   * @param papelId Papel Id.
   * @return Retorna um PapelInfo referente a(o) Papel
   * indicado pelos parâmetros que representam sua chave primária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public PapelInfo selectByPapelId(
              int papelId
         ) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // prepara a consulta
      PreparedStatement statement = prepareSelect(
                                                  "(" + FIELD_PAPEL_ID.getFieldName(getTableName()) + "=?)"
                                          );
      statement.setInt(1, papelId);
      PapelInfo[] result = (PapelInfo[])select(statement);
      // retorna
      if (result.length == 0)
        throw new RecordNotFoundException("Nenhum Papel encontrado.");
      else if (result.length > 1)
        throw new ManyRecordsFoundException("Mais de um Papel encontrado.");
      else {
        // salva tudo
        getFacade().commitTransaction();
        return result[0];
      }
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // mostra exceção
      throw e;
    } // try-catch
  }

  /**
   * Retorna um PapelInfo referente a(o) Papel
   * indicado(a) pelos parâmetros que representam sua chave primária.
   * @param nome Nome.
   * @return Retorna um PapelInfo referente a(o) Papel
   * indicado pelos parâmetros que representam sua chave primária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public PapelInfo selectByNome(
              String nome
         ) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // prepara a consulta
      PreparedStatement statement = prepareSelect(
                                                  "(" + FIELD_NOME.getFieldName(getTableName()) + "=?)"
                                          );
      statement.setString(1, nome);
      PapelInfo[] result = (PapelInfo[])select(statement);
      // retorna
      if (result.length == 0)
        throw new RecordNotFoundException("Nenhum Papel encontrado.");
      else if (result.length > 1)
        throw new ManyRecordsFoundException("Mais de um Papel encontrado.");
      else {
        // salva tudo
        getFacade().commitTransaction();
        return result[0];
      }
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // mostra exceção
      throw e;
    } // try-catch
  }

  /**
   * Retorna um PapelInfo[] contendo a lista de Papel
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * <b>Se o usuário que efetuou logon na aplicação não exercer o Papel de Super
   * Usuários os Papéis ocultos não serão retornados.</b>
   * @param descricao Descrição.
   * @return Retorna um PapelInfo[] contendo a lista de Papel
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * <b>Se o usuário que efetuou logon na aplicação não exercer o Papel de Super
   * Usuários esse papel não será retornado.</b>
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public PapelInfo[] selectByDescricao(
              String descricao
         ) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // prepara a consulta
      String[] orderFieldNames = {FIELD_NOME.getFieldName(getTableName())};
      PreparedStatement statement = prepareSelect(
                                                  orderFieldNames,
                                                  "(" + FIELD_DESCRICAO.getFieldName(getTableName()) + " LIKE ?) AND " +
                                                  // se quem efetuou logon não é Super Usuário...não exibe os Papéis ocultos
                                                  "((? = 1) OR (" + FIELD_NOME.getFieldName(getTableName()) + " NOT LIKE ?))"
                                                );
      statement.setString(1, descricao + "%");
      statement.setInt(2, ((getFacade().getLoggedUser() != null) && getFacade().getLoggedUser().getSuperUser() ? 1 : 0));
      statement.setString(3, "@%");
      // nosso resultado
      EntityInfo[] result = select(statement);
      // salva tudo
      getFacade().commitTransaction();
      // retorna
      return (PapelInfo[])result;
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // mostra exceção
      throw e;
    } // try-catch
  }

  /**
   * Retorna um PapelInfo[] contendo a lista de Papel
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * @param papelUsuarioInfoList PapelUsuarioInfo[] contendo os PapelUsuarioInfo
   *                             referentes aos Papéis que se deseja retornar.
   * @return Retorna um PapelInfo[] contendo a lista de Papel
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public PapelInfo[] selectByPapelUsuarioInfoList(
              PapelUsuarioInfo[] papelUsuarioInfoList
         ) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // se não recebemos nenhum item...retorna
      if (papelUsuarioInfoList.length == 0)
        return new PapelInfo[0];
      // prepara o filtro selecionando os papéis que serão retornados
      // pelos existentes na lista de Papel Usuário recebida
      StringBuffer where = new StringBuffer("(0 = 1)");
      for (int i=0; i<papelUsuarioInfoList.length; i++) {
        if (where.length() > 0)
          where.append(" OR ");
        where.append("(" + FIELD_PAPEL_ID.getFieldName(getTableName()) + "=" + papelUsuarioInfoList[i].getPapelId() + ")");
      } // for
      // prepara a consulta
      String[] orderFieldNames = {FIELD_NOME.getFieldName(getTableName())};
      PreparedStatement statement = prepareSelect(
                                                  orderFieldNames,
                                                  where.toString()
                                                 );
      // nosso resultado
      EntityInfo[] result = select(statement);
      // salva tudo
      getFacade().commitTransaction();
      // retorna
      return (PapelInfo[])result;
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // mostra exceção
      throw e;
    } // try-catch
  }

  /**
   * Atualiza o(a) Papel identificado(a) por 'papelInfo'.
   * @param papelInfo PapelInfo contendo as informações do(a) Papel que se
   *                    deseja atualizar.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public void update(PapelInfo papelInfo) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // valida
      validate(papelInfo);
      // atualiza o registro
      super.update(papelInfo);
      // salva tudo
      getFacade().commitTransaction();
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // mostra exceção
      throw e;
    } // try-catch
  }

  /**
   * Valida o Papel que será inserido ou atualizado.
   * @param papelInfo PapelInfo
   * @throws Exception
   */
  public void validate(PapelInfo papelInfo) throws Exception {
    // se não informou a Tabela Horário...
    if (papelInfo.getTabelaHorario().equals("")) {
      // se é privilegiado...
      if (papelInfo.getPrivilegiado() == SIM)
        papelInfo.setTabelaHorario(TABELA_HORARIO_VAZIA);
      // se não é...
      else
        papelInfo.setTabelaHorario(TABELA_HORARIO_COMERCIAL);
    }
    // se não tem o tamanho correto...exceção
    else if (papelInfo.getTabelaHorario().length() == FIELD_TABELA_HORARIO.getFieldSize()) {
      throw new ExtendedException(getClass().getName(), "validate", "A Tabela Horário informada é inválida.");
    } // if
  }

}
