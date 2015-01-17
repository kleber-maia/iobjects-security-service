
package securityservice.entity;

import java.sql.*;
import java.util.*;

import iobjects.*;
import iobjects.entity.*;
import iobjects.util.*;

/**
 * Representa a entidade Papel A��o no banco de dados da aplica��o.
 */
public class PapelAcao extends Entity {

  // identifica��o da classe
  static public final String CLASS_NAME = "securityservice.entity.PapelAcao";
  // nossas a��es
  static public final Action ACTION_CADASTRO = new Action("papelAcaoCadastro", "Papel A��o", "Cadastro de Papel A��o", "", "entity/papelacaocadastro.jsp", "Security Service", "", Action.CATEGORY_ENTITY, false, false);
  // nossos comandos
  static public final Command COMMAND_SAVE   = ACTION_CADASTRO.addCommand(new Command(Command.COMMAND_SAVE, "Salvar", "Salva os Objetos e Comandos selecionados para o Papel."));
  // nossos campos
  static public final EntityField FIELD_PAPEL_ID = new EntityField("in_papel_id", "Papel Id", "Informe o(a) Papel Id", "papelId", Types.INTEGER, 4, 0, true, EntityField.ALIGN_RIGHT, false, true, EntityField.FORMAT_INTEGER, "", true);
  static public final EntityField FIELD_ACAO = new EntityField("va_acao", "A��o", "Informe o(a) A��o", "acao", Types.VARCHAR, 255, 0, true, EntityField.ALIGN_LEFT, false, true, EntityField.FORMAT_NONE, "", true);
  static public final EntityField FIELD_COMANDOS = new EntityField("va_comandos", "Comandos", "Informe o(a) Comandos", "comandos", Types.VARCHAR, 2000, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_NONE, "", true);
  static public final EntityField FIELD_HASH = new EntityField("in_hash", "Hash", "Informe o(a) Hash", "hash", Types.INTEGER, 4, 0, false, EntityField.ALIGN_RIGHT, true, true, EntityField.FORMAT_INTEGER, "", true);
  // nossos par�metros de usu�rio
  public final Param USER_PARAM_PAPEL_ID = new Param("userParamPapelId", "Papel Id", "Informe o(a) Papel Id", "");

  /**
   * Construtor padr�o.
   */
  public PapelAcao() {
    // nossas a��es
    actionList().add(ACTION_CADASTRO);
    // nossa tabela
    setTableName("securityservice_papel_acao");
    // nossos campos
    fieldList().add(FIELD_PAPEL_ID);
    fieldList().add(FIELD_ACAO);
    fieldList().add(FIELD_COMANDOS);
    fieldList().add(FIELD_HASH);
    // nossos par�metros de usu�rio
    userParamList().add(USER_PARAM_PAPEL_ID);
  }

  /**
   * Exclui o(a) Papel A��o informado(a) por 'papelAcaoInfo'.
   * @param papelAcaoInfo PapelAcaoInfo referente a(o) Papel A��o
   *        que se deseja excluir.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public void delete(PapelAcaoInfo papelAcaoInfo) throws Exception {
    // inicia transa��o
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
      // mostra exce��o
      throw e;
    } // try-catch
  }

  /**
   * Exclui o(a) Papel A��o informado(a) por 'papelId'.
   * @param papelId int Papel Id das a��es que se deseja excluir.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public void deleteByPapelId(int papelId) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // prepara exclus�o
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
      // mostra exce��o
      throw e;
    } // try-catch
  }

  /**
   * Insere o(a) Papel A��o identificado(a) por 'papelAcaoInfo'.
   * @param papelAcaoInfo PapelAcaoInfo contendo as informa��es do(a) Papel A��o que se
   *                    deseja incluir.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public void insert(PapelAcaoInfo papelAcaoInfo) throws Exception {
    // inicia transa��o
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
      // mostra exce��o
      throw e;
    } // try-catch
  }

  /**
   * Insere a lista de A��o com o Papel identificado por 'papelId'.
   * @param papelId int Papel Id.
   * @param actionList String[] Lista de a��es para inserir.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public void insert(int    papelId,
                     String actionList[]) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // apaga as a��es do Papel informado
      deleteByPapelId(papelId);
      // loop nas a��es recebidas
      for (int i=0; i<actionList.length; i++) {
        // nome da a��o da vez
        String action = actionList[i];
        // se � um comando...continua
        if (action.indexOf(':') >= 0)
          continue;
        // verifica os pr�ximos itens da lista a procura dos seus comandos
        Vector commandList = new Vector();
        if (i<actionList.length) {
          for (int w=i+1; w<actionList.length; w++) {
            // item da vez
            String command = actionList[w];
            // se � uma a��o...sai do loop
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
      // lan�a exce��o
      throw e;
    }
  }

  /**
   * Retorna um PapelAcaoInfo referente a(o) Papel A��o
   * indicado(a) pelos par�metros que representam sua chave prim�ria.
   * @param papelId Papel Id.
   * @param acao A��o.
   * @return Retorna um PapelAcaoInfo referente a(o) Papel A��o
   * indicado pelos par�metros que representam sua chave prim�ria.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PapelAcaoInfo selectByPapelIdAcao(
              int papelId,
              String acao
         ) throws Exception {
    // inicia transa��o
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
        throw new RecordNotFoundException("Nenhum Papel A��o encontrado.");
      else if (result.length > 1)
        throw new ManyRecordsFoundException("Mais de um Papel A��o encontrado.");
      else {
        // salva tudo
        getFacade().commitTransaction();
        return result[0];
      }
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // mostra exce��o
      throw e;
    } // try-catch
  }

  /**
   * Retorna um PapelAcaoInfo[] contendo a lista de Papel A��o
   * indicados(as) pelos par�metros que representam sua chave secund�ria.
   * @param papelId Papel Id.
   * @return Retorna um PapelAcaoInfo[] contendo a lista de Papel A��o
   * indicados(as) pelos par�metros que representam sua chave secund�ria.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PapelAcaoInfo[] selectByPapelId(
              int papelId
         ) throws Exception {
    // inicia transa��o
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
      // mostra exce��o
      throw e;
    } // try-catch
  }

  /**
   * Atualiza o(a) Papel A��o identificado(a) por 'papelAcaoInfo'.
   * @param papelAcaoInfo PapelAcaoInfo contendo as informa��es do(a) Papel A��o que se
   *                    deseja atualizar.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public void update(PapelAcaoInfo papelAcaoInfo) throws Exception {
    // inicia transa��o
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
      // mostra exce��o
      throw e;
    } // try-catch
  }

}
