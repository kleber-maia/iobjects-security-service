
package securityservice.entity;

import java.sql.*;
import java.util.*;

import iobjects.*;
import iobjects.entity.*;
import iobjects.util.*;

/**
 * Representa a entidade Papel Ação no banco de dados da aplicação.
 */
public class PapelAcao extends Entity {

  // identificação da classe
  static public final String CLASS_NAME = "securityservice.entity.PapelAcao";
  // nossas ações
  static public final Action ACTION_CADASTRO = new Action("papelAcaoCadastro", "Papel Ação", "Cadastro de Papel Ação", "", "entity/papelacaocadastro.jsp", "Security Service", "", Action.CATEGORY_ENTITY, false, false);
  // nossos comandos
  static public final Command COMMAND_SAVE   = ACTION_CADASTRO.addCommand(new Command(Command.COMMAND_SAVE, "Salvar", "Salva os Objetos e Comandos selecionados para o Papel."));
  // nossos campos
  static public final EntityField FIELD_PAPEL_ID = new EntityField("in_papel_id", "Papel Id", "Informe o(a) Papel Id", "papelId", Types.INTEGER, 4, 0, true, EntityField.ALIGN_RIGHT, false, true, EntityField.FORMAT_INTEGER, "", true);
  static public final EntityField FIELD_ACAO = new EntityField("va_acao", "Ação", "Informe o(a) Ação", "acao", Types.VARCHAR, 255, 0, true, EntityField.ALIGN_LEFT, false, true, EntityField.FORMAT_NONE, "", true);
  static public final EntityField FIELD_COMANDOS = new EntityField("va_comandos", "Comandos", "Informe o(a) Comandos", "comandos", Types.VARCHAR, 2000, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_NONE, "", true);
  static public final EntityField FIELD_HASH = new EntityField("in_hash", "Hash", "Informe o(a) Hash", "hash", Types.INTEGER, 4, 0, false, EntityField.ALIGN_RIGHT, true, true, EntityField.FORMAT_INTEGER, "", true);
  // nossos parâmetros de usuário
  public final Param USER_PARAM_PAPEL_ID = new Param("userParamPapelId", "Papel Id", "Informe o(a) Papel Id", "");

  /**
   * Construtor padrão.
   */
  public PapelAcao() {
    // nossas ações
    actionList().add(ACTION_CADASTRO);
    // nossa tabela
    setTableName("securityservice_papel_acao");
    // nossos campos
    fieldList().add(FIELD_PAPEL_ID);
    fieldList().add(FIELD_ACAO);
    fieldList().add(FIELD_COMANDOS);
    fieldList().add(FIELD_HASH);
    // nossos parâmetros de usuário
    userParamList().add(USER_PARAM_PAPEL_ID);
  }

  /**
   * Exclui o(a) Papel Ação informado(a) por 'papelAcaoInfo'.
   * @param papelAcaoInfo PapelAcaoInfo referente a(o) Papel Ação
   *        que se deseja excluir.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public void delete(PapelAcaoInfo papelAcaoInfo) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // exclui o registro
      super.delete(papelAcaoInfo);
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
   * Exclui o(a) Papel Ação informado(a) por 'papelId'.
   * @param papelId int Papel Id das ações que se deseja excluir.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public void deleteByPapelId(int papelId) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // prepara exclusão
      PreparedStatement preparedStatement = SqlTools.prepareDelete(getConnection(),
                                                                   getTableName(),
                                                                   FIELD_PAPEL_ID.getFieldName(getTableName()) + "=" + papelId);
      // exclui
      preparedStatement.execute();
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
   * Insere o(a) Papel Ação identificado(a) por 'papelAcaoInfo'.
   * @param papelAcaoInfo PapelAcaoInfo contendo as informações do(a) Papel Ação que se
   *                    deseja incluir.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public void insert(PapelAcaoInfo papelAcaoInfo) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // insere o registro
      super.insert(papelAcaoInfo);
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
   * Insere a lista de Ação com o Papel identificado por 'papelId'.
   * @param papelId int Papel Id.
   * @param actionList String[] Lista de ações para inserir.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public void insert(int    papelId,
                     String actionList[]) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // apaga as ações do Papel informado
      deleteByPapelId(papelId);
      // loop nas ações recebidas
      for (int i=0; i<actionList.length; i++) {
        // nome da ação da vez
        String action = actionList[i];
        // se é um comando...continua
        if (action.indexOf(':') >= 0)
          continue;
        // verifica os próximos itens da lista a procura dos seus comandos
        Vector commandList = new Vector();
        if (i<actionList.length) {
          for (int w=i+1; w<actionList.length; w++) {
            // item da vez
            String command = actionList[w];
            // se é uma ação...sai do loop
            if (command.indexOf(':') < 0)
              break;
            // adiciona a lista de comandos
            commandList.add(command.substring(command.indexOf(':')+1));
          } // for w
        } // if
        // info para ser inserido com seus comandos
        String[] commands = new String[commandList.size()];
        commandList.copyInto(commands);
        PapelAcaoInfo papelAcaoInfo = new PapelAcaoInfo(papelId,
                                                        action,
                                                        StringTools.arrayStringToString(commands, ";"),
                                                        0);
        // insere
        insert(papelAcaoInfo);
      } // for i
      // salva tudo
      getFacade().commitTransaction();
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // lança exceção
      throw e;
    }
  }

  /**
   * Retorna um PapelAcaoInfo referente a(o) Papel Ação
   * indicado(a) pelos parâmetros que representam sua chave primária.
   * @param papelId Papel Id.
   * @param acao Ação.
   * @return Retorna um PapelAcaoInfo referente a(o) Papel Ação
   * indicado pelos parâmetros que representam sua chave primária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public PapelAcaoInfo selectByPapelIdAcao(
              int papelId,
              String acao
         ) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // prepara a consulta
      PreparedStatement statement = prepareSelect(
                                                  "(" + FIELD_PAPEL_ID.getFieldName(getTableName()) + "=?) AND " +
                                                  "(" + FIELD_ACAO.getFieldName(getTableName()) + "=?)"
                                          );
      statement.setInt(1, papelId);
      statement.setString(2, acao);
      PapelAcaoInfo[] result = (PapelAcaoInfo[])select(statement);
      // retorna
      if (result.length == 0)
        throw new RecordNotFoundException("Nenhum Papel Ação encontrado.");
      else if (result.length > 1)
        throw new ManyRecordsFoundException("Mais de um Papel Ação encontrado.");
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
   * Retorna um PapelAcaoInfo[] contendo a lista de Papel Ação
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * @param papelId Papel Id.
   * @return Retorna um PapelAcaoInfo[] contendo a lista de Papel Ação
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public PapelAcaoInfo[] selectByPapelId(
              int papelId
         ) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // prepara a consulta
      String[] orderFieldNames = {FIELD_PAPEL_ID.getFieldName(getTableName())};
      PreparedStatement statement = prepareSelect(
                                                  orderFieldNames,
                                                  "(" + FIELD_PAPEL_ID.getFieldName(getTableName()) + " = ?)"
                                                );
      statement.setInt(1, papelId);
      // nosso resultado
      EntityInfo[] result = select(statement);
      // salva tudo
      getFacade().commitTransaction();
      // retorna
      return (PapelAcaoInfo[])result;
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // mostra exceção
      throw e;
    } // try-catch
  }

  /**
   * Atualiza o(a) Papel Ação identificado(a) por 'papelAcaoInfo'.
   * @param papelAcaoInfo PapelAcaoInfo contendo as informações do(a) Papel Ação que se
   *                    deseja atualizar.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public void update(PapelAcaoInfo papelAcaoInfo) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // atualiza o registro
      super.update(papelAcaoInfo);
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

}
