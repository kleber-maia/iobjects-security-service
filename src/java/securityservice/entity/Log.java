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
import java.util.*;

import iobjects.*;
import iobjects.entity.*;
import iobjects.help.*;

import securityservice.misc.*;

import iobjects.util.*;


import securityservice.misc.TipoOperacaoLog;

/**
 * Representa a entidade Log no banco de dados da aplicação.
 */
public class Log extends Entity {

  // identificação da classe
  static public final String CLASS_NAME = "securityservice.entity.Log";
  // nossa ajuda extra
  static public final String HELP = "";
  // nossas ações
  static public final Action ACTION          = new Action("log", "Log", "Mantém o cadastro de Log.", HELP, "entity/log.jsp", "Security Service", "", Action.CATEGORY_ENTITY, false, false);
  static public final Action ACTION_CADASTRO = new Action("logCadastro", ACTION.getCaption(), ACTION.getDescription(), HELP, "entity/logcadastro.jsp", ACTION.getModule(), ACTION.getAccessPath(), Action.CATEGORY_ENTITY, ACTION.getMobile(), false);
  // nossos comandos
  static public final Command COMMAND_EDIT   = ACTION.addCommand(new Command(Command.COMMAND_EDIT,   "Editar",    "Edita o registro exibido, clicando na lista."));
  static public final Command COMMAND_INSERT = ACTION.addCommand(new Command(Command.COMMAND_INSERT, "Inserir",   "Insere um novo registro."));
  static public final Command COMMAND_DELETE = ACTION.addCommand(new Command(Command.COMMAND_DELETE, "Excluir",   "Exclui o(s) registro(s) selecionado(s)."));
  static public final Command COMMAND_SAVE   = ACTION.addCommand(new Command(Command.COMMAND_SAVE,   "Salvar",    "Salva o registro que está sendo editado ou inserido."));
  static public final Command COMMAND_SEARCH = ACTION.addCommand(new Command(Command.COMMAND_SEARCH, "Pesquisar", "Pesquisa por registros com os parâmetros informados."));
  // nossos campos
  static public final EntityField FIELD_LOG_ID     = new EntityField("in_log_id", "Log", "Informa o Log.", "logId", Types.INTEGER, 10, 0, true, EntityField.ALIGN_RIGHT, false, true, EntityField.FORMAT_INTEGER, "", true);
  static public final EntityField FIELD_DATA       = new EntityField("dt_operacao", "Data", "Informa a Data.", "data", Types.TIMESTAMP, 10, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_DATE, "", true);
  static public final EntityField FIELD_USUARIO_ID = new EntityField("in_usuario_id", "Usuário", "Informa o Usuário.", "usuarioId", Types.INTEGER, 10, 0, false, EntityField.ALIGN_RIGHT, true, true, EntityField.FORMAT_INTEGER, "", true);
  static public final EntityField FIELD_OPERACAO   = new EntityField("sm_operacao", "Operação", "Informa a Operação.", "operacao", Types.SMALLINT, 5, 0, false, EntityField.ALIGN_RIGHT, true, true, EntityField.FORMAT_INTEGER, "", true);
  static public final EntityField FIELD_OBJETO     = new EntityField("va_objeto", "Objeto", "Informa o Objeto.", "objeto", Types.VARCHAR, 80, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_NONE, "", true);
  static public final EntityField FIELD_DESCRICAO  = new EntityField("va_log", "Descrição", "Informa a Descrição.", "descricao", Types.VARCHAR, 2147483647, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_NONE, "", true);
  // nossos parâmetros de usuário
  public final Param USER_PARAM_DATA       = new Param("userParamData", "Data", "Informe a Data.", "", 10, Param.ALIGN_LEFT, Param.FORMAT_DATE, "", "value != ''", "Obrigatório informar a Data.");
  public final Param USER_PARAM_USUARIO_ID = new Param("userParamUsuarioId", "Usuário ID", "Informe o Usuário.", "", 10, Param.ALIGN_RIGHT, Param.FORMAT_INTEGER, "", "value != ''", "Obrigatório informar o Usuário ID.");
  public final Param USER_PARAM_OPERACAO   = new Param("userParamOperacao", "Operação", "Selecione a Operação.", "", 10, Param.ALIGN_RIGHT, Param.FORMAT_INTEGER, "", "value != ''", "Obrigatório informar a Operação.");
  public final Param USER_PARAM_OBJETO     = new Param("userParamObjeto", "Objeto", "Informe o Objeto.", "", 80, Param.ALIGN_LEFT, Param.FORMAT_NONE, "", "value != ''", "Obrigatório informar o Objeto.");
  public final Param USER_PARAM_DESCRICAO  = new Param("userParamDescricao", "Descricao", "Informe a Descrição.", "", 2147483647, Param.ALIGN_LEFT, Param.FORMAT_NONE, "", "value != ''", "Obrigatório informar a Descrição.");

  {
    // configura os campos
    FIELD_OPERACAO.setLookupList(TipoOperacaoLog.LOOKUP_LIST_FOR_FIELD);
  }

  /**
   * Construtor padrão.
   */
  public Log() {
    // nossas ações
    actionList().add(ACTION);
    actionList().add(ACTION_CADASTRO);
    // nossa tabela
    setTableName("securityservice_log");
    // nossos campos  
    fieldList().add(FIELD_LOG_ID);  
    fieldList().add(FIELD_DATA);  
    fieldList().add(FIELD_USUARIO_ID);  
    fieldList().add(FIELD_OPERACAO);  
    fieldList().add(FIELD_OBJETO);  
    fieldList().add(FIELD_DESCRICAO);  
    // nossos parâmetros de usuário
    userParamList().add(USER_PARAM_DATA);
    userParamList().add(USER_PARAM_USUARIO_ID);
    userParamList().add(USER_PARAM_OPERACAO);
    userParamList().add(USER_PARAM_OBJETO);
  }

  /**
   * Exclui o(a) Log informado(a) por 'logInfo'.
   * @param logInfo LogInfo referente a(o) Log
   *        que se deseja excluir.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public void delete(LogInfo logInfo) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // exclui o registro
      super.delete(logInfo);
      // salva tudo
      getFacade().commitTransaction();
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      throw e;
    } // try-catch
  }

   /**
   * Exclui os Logs por uma data.
   * @param data é referente à datas
   *        que se deseja excluir.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public void delete(Timestamp data) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // prepara a query
      PreparedStatement preparedStatement = SqlTools.prepareDelete(getConnection(), getTableName(), "dt_operacao < ?");
      // preenche os parâmetros
      preparedStatement.setTimestamp(1, new Timestamp(DateTools.getCalculatedDays(data, -90).getTime()));
      // executa
      preparedStatement.execute();
      // salva tudo
      getFacade().commitTransaction();
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      throw e;
    } // try-catch
  }

  /**
   * Insere o(a) Log identificado(a) por 'logInfo'.
   * @param logInfo LogInfo contendo as informações do(a) Log que se
   *                    deseja incluir.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public void insert(LogInfo logInfo) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // valida os dados
      validate(logInfo);
      // valor da sequência
      logInfo.setLogId(getNextSequence(FIELD_LOG_ID));
      // insere o registro
      super.insert(logInfo);
      // salva tudo
      getFacade().commitTransaction();
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      throw e;
    } // try-catch
  }

  /**
   * Retorna um LogInfo referente a(o) Log
   * indicado(a) pelos parâmetros que representam sua chave primária.
   * @param logId Log ID.
   * @return Retorna um LogInfo referente a(o) Log
   * indicado pelos parâmetros que representam sua chave primária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public LogInfo selectByPrimaryKey(
                int logId
           ) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // prepara a consulta
      PreparedStatement statement = prepareSelect(
                                                  "(" + FIELD_LOG_ID.getFieldName(getTableName()) + "=?)"
                                          );
      statement.setInt(1, logId);
      LogInfo[] result = (LogInfo[])select(statement);
      // se não encontramos...
      if (result.length == 0)
        throw new RecordNotFoundException(getClass().getName(), "selectByPrimaryKey", "Nenhum registro encontrado.");
      // se encontramos mais...
      else if (result.length > 1)
        throw new ManyRecordsFoundException(getClass().getName(), "selectByPrimaryKey", "Mais de um registro encontrado.");
      else {
        // salva tudo
        getFacade().commitTransaction();
        // retorna
        return result[0];
      } // if
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      throw e;
    } // try-catch
  }

  /**
   * Retorna um LogInfo[] contendo a lista de Log
   * indicados(as) pelos parâmetros de pesquisa.
   * @param data Data ou Zero Date para todos.
   * @param usuarioId Usuário ID ou 0 para todos.
   * @param operacao Operação ou TODOS para todos.
   * @param objeto Objeto.
   * @param paginate Informação de paginação dos resultados ou null.
   * @return Retorna um LogInfo[] contendo a lista de Log
   * indicados(as) pelos parâmetros de pesquisa.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public LogInfo[] selectByFilter(
                Timestamp data,
                int usuarioId,
                int operacao,
                String objeto,
                Paginate paginate) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // prepara a consulta
      String[] orderFieldNames = {FIELD_DATA.getFieldName(getTableName())};
      PreparedStatement statement = prepareSelect(
                                                  orderFieldNames,
                                                  "(" + FIELD_DATA.getFieldName(getTableName()) + " = ?) OR " +
                                                  "(" + FIELD_DATA.getFieldName(getTableName()) + " = " + DateTools.ZERO_DATE + ") AND " +
                                                  "((" + FIELD_USUARIO_ID.getFieldName(getTableName()) + " = ?) OR " + FIELD_USUARIO_ID.getFieldName(getTableName()) + " = 0 " +
                                                  "(" + FIELD_OPERACAO.getFieldName(getTableName()) + " = ?) AND " +
                                                  "(" + FIELD_OBJETO.getFieldName(getTableName()) + " LIKE ?)",
                                                  new String[]{},
                                                  paginate
                                                 );
      statement.setTimestamp(1, data);
      statement.setInt(2, usuarioId);
      statement.setInt(3, operacao);
      statement.setString(4, objeto + "%");
      // nosso resultado
      LogInfo[] result = (LogInfo[])select(statement);
      // salva tudo
      getFacade().commitTransaction();
      // retorna
      return result;
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      throw e;
    } // try-catch
  }

  /**
   * Atualiza o(a) Log identificado(a) por 'logInfo'.
   * @param logInfo LogInfo contendo as informações do(a) Log que se
   *                    deseja atualizar.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public void update(LogInfo logInfo) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // valida os dados
      validate(logInfo);
      // atualiza o registro
      super.update(logInfo);
      // salva tudo
      getFacade().commitTransaction();
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      throw e;
    } // try-catch
  }

  /**
   * Valida o(a) Log identificado(a) por 'logInfo'.
   * @param logInfo LogInfo contendo as informações do(a) Log que se
   *                    deseja validar.
   * @throws Exception Em caso de exceção no preenchimento dos dados informados.
   */
  public void validate(LogInfo logInfo) throws Exception {
  }

}
