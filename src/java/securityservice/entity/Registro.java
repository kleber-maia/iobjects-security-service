
package securityservice.entity;

import java.sql.*;
import java.util.*;

import iobjects.*;
import iobjects.entity.*;
import iobjects.help.*;
import iobjects.security.*;
import iobjects.util.*;

/**
 * Representa a entidade Registro no banco de dados da aplicação.
 */
public class Registro extends Entity implements Registry {

  // identificação da classe
  static public final String CLASS_NAME = "securityservice.entity.Registro";
  // nossa ajuda extra
  static public final String HELP = "";
  // nossas ações
  static public final Action ACTION          = new Action("registro", "Registro", "Mantém o cadastro de Registro.", HELP, "entity/registro.jsp", "iManager", "", Action.CATEGORY_ENTITY, false, false);
  static public final Action ACTION_CADASTRO = new Action("registroCadastro", ACTION.getCaption(), ACTION.getDescription(), HELP, "entity/registrocadastro.jsp", ACTION.getModule(), ACTION.getAccessPath(), Action.CATEGORY_ENTITY, ACTION.getMobile(), false);
  // nossos comandos
  static public final Command COMMAND_EDIT   = ACTION.addCommand(new Command(Command.COMMAND_EDIT,   "Editar",    "Edita o " + ACTION.getCaption() + " exibido, clicando na lista."));
  static public final Command COMMAND_INSERT = ACTION.addCommand(new Command(Command.COMMAND_INSERT, "Inserir",   "Insere um novo " + ACTION.getCaption() + "."));
  static public final Command COMMAND_DELETE = ACTION.addCommand(new Command(Command.COMMAND_DELETE, "Excluir",   "Exclui o " + ACTION.getCaption() + " selecionado."));
  static public final Command COMMAND_SAVE   = ACTION.addCommand(new Command(Command.COMMAND_SAVE,   "Salvar",    "Salva o " + ACTION.getCaption() + " que está sendo editado ou inserido."));
  static public final Command COMMAND_SEARCH = ACTION.addCommand(new Command(Command.COMMAND_SEARCH, "Pesquisar", "Pesquisa por " + ACTION.getCaption() + " com os parâmetros informados."));
  // nossos campos
  static public final EntityField FIELD_REGISTRO_ID = new EntityField("in_registro_id", "Registro ID", "", "registroId", Types.INTEGER, 10, 0, true, EntityField.ALIGN_RIGHT, false, true, EntityField.FORMAT_INTEGER, "", true);
  static public final EntityField FIELD_CHAVE = new EntityField("va_chave", "Chave", "Informe a Chave.", "chave", Types.VARCHAR, 500, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_UPPER_CASE, "", true);
  static public final EntityField FIELD_VALOR_STRING = new EntityField("va_valor", "Valor como String", "Informe o Valor como String.", "valorString", Types.VARCHAR, 2147483647, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_NONE, "", true);
  static public final EntityField FIELD_VALOR_INTEGER = new EntityField("in_valor", "Valor como Integer", "Informe o Valor como Integer.", "valorInteger", Types.INTEGER, 10, 0, false, EntityField.ALIGN_RIGHT, true, true, EntityField.FORMAT_INTEGER, "", true);
  static public final EntityField FIELD_VALOR_DOUBLE = new EntityField("do_valor", "Valor como Double", "Informe o Valor como Double.", "valorDouble", Types.DOUBLE, 34, 17, false, EntityField.ALIGN_RIGHT, true, true, EntityField.FORMAT_DOUBLE, "", true);
  // nossos parâmetros de usuário
  public final Param USER_PARAM_CHAVE = new Param("userParamChave", "Chave", "Informe a Chave.", "", 500, Param.ALIGN_LEFT, Param.FORMAT_NONE, "", "value != ''", "Obrigatório informar a Chave.");

  /**
   * Constante para ser usada como delimitador de nível quando uma chave possuir
   * chaves filhas.
   */
  static public final String LEVEL_DELIMITER = "/";
  /**
   * Constante para identificar a raiz dos usuários.
   */
  static public final String USER = "USER";
  /**
   * Constante para identificar a raiz das relações mestres.
   */
  static public final String MASTER_RELATION = "MASTER_RELATION";
  /**
   * Constante para identificar a raiz do sistema.
   */
  static public final String SYSTEM = "SYSTEM";

  /**
   * Construtor padrão.
   */
  public Registro() {
    // nossas ações
    actionList().add(ACTION);
    actionList().add(ACTION_CADASTRO);
    // nossa tabela
    setTableName("securityservice_registro");
    // nossos campos
    fieldList().add(FIELD_REGISTRO_ID);
    fieldList().add(FIELD_CHAVE);
    fieldList().add(FIELD_VALOR_STRING);
    fieldList().add(FIELD_VALOR_INTEGER);
    fieldList().add(FIELD_VALOR_DOUBLE);
    // nossos parâmetros de usuário
    userParamList().add(USER_PARAM_CHAVE);
  }

  /**
   * Exclui o(a) Registro informado(a) por 'registroInfo'.
   * @param registroInfo RegistroInfo referente a(o) Registro
   *        que se deseja excluir.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  private void delete(RegistroInfo registroInfo) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // obtém os registros filhos
      RegistroInfo[] registrosFilhos = selectByChavePai(registroInfo.getChave());
      // apaga os registros filhos
      for (int i=0; i<registrosFilhos.length; i++)
        super.delete(registrosFilhos[i]);
      // exclui o registro
      super.delete(registroInfo);
      // salva tudo
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
   * Retorna o valor atual da raiz que será utilizada como parte da chave
   * do Registro.
   * @param raiz int Raiz desejada.
   * @return String
   * @throws Exception
   */
  private String getRaiz(int raiz) throws Exception {
    // raiz que iremos utilizar
    switch (raiz) {
      case Registry.ROOT_USER:
        return USER;
      case Registry.ROOT_MASTER_RELATION:
        return MASTER_RELATION;
    default:
      return SYSTEM;
    } // switch
  }

  /**
   * Insere o(a) Registro identificado(a) por 'registroInfo'.
   * @param registroInfo RegistroInfo contendo as informações do(a) Registro que se
   *                    deseja incluir.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  private void insert(RegistroInfo registroInfo) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // valida os dados
      validate(registroInfo);
      // obtém o valor da seqüência
      registroInfo.setRegistroId(getNextSequence(FIELD_REGISTRO_ID));
      // insere o registro
      super.insert(registroInfo);
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

  private RegistroInfo read(int root, String key, String name) throws Exception {
    // tenta obter o registro
    return selectByChave(getRaiz(root) + LEVEL_DELIMITER + key + LEVEL_DELIMITER + name);
  }

  public double readDouble(int root, String key, String name, double defaultValue) throws Exception {
    RegistroInfo result = read(root, key, name);
    return (result == null ? defaultValue : result.getValorDouble());
  }

  public int readInteger(int root, String key, String name, int defaultValue) throws Exception {
    RegistroInfo result = read(root, key, name);
    return (result == null ? defaultValue : result.getValorInteger());
  }

  public String readString(int root, String key, String name, String defaultValue) throws Exception {
    RegistroInfo result = read(root, key, name);
    return (result == null ? defaultValue : result.getValorString());
  }

  /**
   * Retorna um RegistroInfo referente a(o) Registro
   * indicado(a) pelos parâmetros que representam sua chave primária.
   * @param registroId Registro ID.
   * @return Retorna um RegistroInfo referente a(o) Registro
   * indicado pelos parâmetros que representam sua chave primária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  private RegistroInfo selectByRegistroId(
              int registroId
         ) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // prepara a consulta
      PreparedStatement statement = prepareSelect(
                                                  "(" + FIELD_REGISTRO_ID.getFieldName(getTableName()) + "=?)"
                                          );
      statement.setInt(1, registroId);
      RegistroInfo[] result = (RegistroInfo[])select(statement);
      // salva tudo
      getFacade().commitTransaction();
      // retorna
      if (result.length == 0)
        return null;
      else if (result.length > 1)
        throw new ManyRecordsFoundException(getClass().getName(), "selectByRegistroId", "Mais de um Registro encontrado.");
      else
        return result[0];
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // mostra exceção
      throw e;
    } // try-catch
  }

  /**
   * Retorna um RegistroInfo referente a(o) Registro
   * indicado(a) pelos parâmetros que representam sua chave única.
   * @param chave Chave.
   * @return Retorna um RegistroInfo[] contendo a lista de Registro
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  private RegistroInfo selectByChave(
              String chave
         ) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // prepara a consulta
      String[] orderFieldNames = {FIELD_CHAVE.getFieldName(getTableName())};
      PreparedStatement statement = prepareSelect(
                                                  orderFieldNames,
                                                  "(" + FIELD_CHAVE.getFieldName(getTableName()) + " = ?)"
                                                );
      statement.setString(1, chave.toUpperCase());
      RegistroInfo[] result = (RegistroInfo[])select(statement);
      // salva tudo
      getFacade().commitTransaction();
      // retorna
      if (result.length == 0)
        return null;
      else if (result.length > 1)
        throw new ManyRecordsFoundException(getClass().getName(), "selectByChave", "Mais de um Registro encontrado.");
      else
        return result[0];
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // mostra exceção
      throw e;
    } // try-catch
  }

  /**
   * Retorna um RegistroInfo[] contendo a lista de Registro filhos da Chave
   * informada.
   * <p><b>As Chaves dos Registros filhos devem estar no seguinte formato:
   * Chave do Registro Pai + LEVEL_DELIMITER + Chave do Registro Filho.
   * Exemplo: Chave Pai/Chave Filha 1, Chave Pai/Chave Filha 2</b></p>
   * @param chave Chave.
   * @return Retorna um RegistroInfo[] contendo a lista de Registro
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  private RegistroInfo[] selectByChavePai(
              String chave
         ) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // prepara a consulta
      String[] orderFieldNames = {FIELD_CHAVE.getFieldName(getTableName())};
      PreparedStatement statement = prepareSelect(
                                                  orderFieldNames,
                                                  "(" + FIELD_CHAVE.getFieldName(getTableName()) + " LIKE ?)"
                                                );
      statement.setString(1, chave.toUpperCase() + (chave.endsWith(LEVEL_DELIMITER) ? "" : LEVEL_DELIMITER) + "%");
      // nosso resultado
      EntityInfo[] result = select(statement);
      // salva tudo
      getFacade().commitTransaction();
      // retorna
      return (RegistroInfo[])result;
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // mostra exceção
      throw e;
    } // try-catch
  }

  /**
   * Atualiza o(a) Registro identificado(a) por 'registroInfo'.
   * @param registroInfo RegistroInfo contendo as informações do(a) Registro que se
   *                    deseja atualizar.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  private void update(RegistroInfo registroInfo) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // valida os dados
      validate(registroInfo);
      // atualiza o registro
      super.update(registroInfo);
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
   * Valida o(a) Registro identificado(a) por 'registroInfo'.
   * @param registroInfo RegistroInfo contendo as informações do(a) Registro que se
   *                    deseja validar.
   * @throws Exception Em caso de exceção no preenchimento dos dados informados.
   */
  private void validate(RegistroInfo registroInfo) throws Exception {
    // põe a chave em maiúsculo
    registroInfo.setChave(registroInfo.getChave().toUpperCase());
  }

  private void write(int root, String key, String name, Object valor) throws Exception {
    // tenta obter o registro existente
    RegistroInfo registroInfo = read(root, key, name);
    // se não encontramos...cria um novo
    if (registroInfo == null) {
      registroInfo = new RegistroInfo();
      registroInfo.setChave(getRaiz(root) + LEVEL_DELIMITER + key + LEVEL_DELIMITER + name);
    } // if
    // define o novo valor
    if (valor instanceof String)
      registroInfo.setValorString((String)valor);
    else if (valor instanceof Integer)
      registroInfo.setValorInteger(((Integer)valor).intValue());
    else if (valor instanceof Double)
      registroInfo.setValorDouble(((Double)valor).doubleValue());
    else
      throw new ExtendedException(CLASS_NAME, "write", "Tipo desconhecido: " + valor.getClass().getName() + ".");
    // salva
    if (registroInfo.getRegistroId() == 0)
      insert(registroInfo);
    else
      update(registroInfo);
  }

  public void writeDouble(int root, String key, String name, double value) throws Exception {
    write(root, key, name, new Double(value));
  }

  public void writeInteger(int root, String key, String name, int value) throws Exception {
    write(root, key, name, new Integer(value));
  }

  public void writeString(int root, String key, String name, String value) throws Exception {
    write(root, key, name, value);
  }

}
