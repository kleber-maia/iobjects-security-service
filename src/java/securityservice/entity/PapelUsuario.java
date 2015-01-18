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
import iobjects.util.*;
import securityservice.SecurityService;

/**
 * Representa a entidade Papel Usu�rio no banco de dados da aplica��o.
 */
public class PapelUsuario extends Entity {

  // identifica��o da classe
  static public final String CLASS_NAME = "securityservice.entity.PapelUsuario";
  // nossas a��es
  static public final Action ACTION          = new Action("papelUsuario", "Papel Usu�rio", "Cadastro de Papel Usu�rio", "", "entity/papelusuario.jsp", "Security Service", "", Action.CATEGORY_ENTITY, false, false);
  static public final Action ACTION_CADASTRO = new Action("papelUsuarioCadastro", "", "", "", "entity/papelusuariocadastro.jsp", "", "", Action.CATEGORY_ENTITY, false, false);
  // nossos comandos
  static public final Command COMMAND_DELETE = ACTION.addCommand(new Command(Command.COMMAND_DELETE, "Excluir", ""));
  static public final Command COMMAND_EDIT   = ACTION.addCommand(new Command(Command.COMMAND_EDIT,   "Editar",  ""));
  static public final Command COMMAND_INSERT = ACTION.addCommand(new Command(Command.COMMAND_INSERT, "Inserir", ""));
  static public final Command COMMAND_SAVE   = ACTION.addCommand(new Command(Command.COMMAND_SAVE,   "Salvar",  ""));
  // nossos campos
  static public final EntityField FIELD_PAPEL_ID = new EntityField("in_papel_id", "Papel Id", "Informe o(a) Papel Id", "papelId", Types.INTEGER, 4, 0, true, EntityField.ALIGN_RIGHT, false, true, EntityField.FORMAT_INTEGER, "", true);
  static public final EntityField FIELD_USUARIO_ID = new EntityField("in_usuario_id", "Usu�rio Id", "Informe o(a) Usu�rio Id", "usuarioId", Types.INTEGER, 4, 0, true, EntityField.ALIGN_RIGHT, false, true, EntityField.FORMAT_INTEGER, "", true);
  static public final EntityField FIELD_HASH = new EntityField("in_hash", "Hash", "Informe o(a) Hash", "hash", Types.INTEGER, 4, 0, false, EntityField.ALIGN_RIGHT, true, true, EntityField.FORMAT_INTEGER, "", true);
  // nossos par�metros de usu�rio
  public final Param USER_PARAM_MASTER     = new Param("userParamMaster", "Master", "", "");
  public final Param USER_PARAM_PAPEL_ID   = new Param("userParamPapelId", "Papel Id", "Informe o(a) Papel Id", "");
  public final Param USER_PARAM_USUARIO_ID = new Param("userParamUsuarioId", "Usu�rio Id", "Informe o(a) Usu�rio Id", "");

  // Lookup para Papel
  static public final EntityLookup LOOKUP_PAPEL = new EntityLookup("lookupPapel", "Papel", "", Papel.CLASS_NAME, FIELD_PAPEL_ID, Papel.FIELD_NOME, Papel.FIELD_NOME);
  // Lookup para Usu�rio
  static private EntityField[] usuarioKeyFields     = {FIELD_USUARIO_ID};
  static private EntityField[] usuarioDisplayFields = {Usuario.FIELD_NOME};
  static private EntityField[] usuarioOrderFields   = {Usuario.FIELD_NOME};
  static private String        usuarioFilter        = Usuario.FIELD_INATIVO.getFieldName() + "<>" + Usuario.SIM;
  static public final EntityLookup LOOKUP_USUARIO = new EntityLookup("lookupUsuario", "Usu�rio", "", Usuario.CLASS_NAME, usuarioKeyFields, usuarioDisplayFields, usuarioOrderFields, usuarioFilter, false, true);

  // identifica��es possiveis sobre o objeto que exerce o papel de mestre
  // na rela��o mestre-detalhe
  static public final String MASTER_IS_PAPEL   = "PAPEL";
  static public final String MASTER_IS_USUARIO = "USUARIO";

  /**
   * Construtor padr�o.
   */
  public PapelUsuario() {
    // nossas a��es
    actionList().add(ACTION);
    actionList().add(ACTION_CADASTRO);
    // nossa tabela
    setTableName("securityservice_papel_usuario");
    // nossos campos
    fieldList().add(FIELD_PAPEL_ID);
    fieldList().add(FIELD_USUARIO_ID);
    fieldList().add(FIELD_HASH);
    // nossos lookups
    lookupList().add(LOOKUP_PAPEL);
    lookupList().add(LOOKUP_USUARIO);
    // nossos par�metros de usu�rio
    userParamList().add(USER_PARAM_MASTER);
    userParamList().add(USER_PARAM_PAPEL_ID);
    userParamList().add(USER_PARAM_USUARIO_ID);
  }

  /**
   * Exclui o(a) Papel Usu�rio informado(a) por 'papelUsuarioInfo'.
   * @param papelUsuarioInfo PapelUsuarioInfo referente a(o) Papel Usu�rio
   *        que se deseja excluir.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public void delete(PapelUsuarioInfo papelUsuarioInfo) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // exclui o registro
      super.delete(papelUsuarioInfo);
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
   * Exclui os Papel Usu�rio referenciados pelo Papel informado.
   * @param papelId Papel Id.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public void deleteByPapelId(int papelId) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // exclui o registro
      PreparedStatement preparedStatement = SqlTools.prepareDelete(getConnection(),
                                                                   getTableName(),
                                                                   FIELD_PAPEL_ID.getFieldName(getTableName()) + "=" + papelId);
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
   * Exclui os Papel Usu�rio referenciados pelo Usu�rio informado.
   * @param usuarioId Usu�rio Id.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public void deleteByUsuarioId(int usuarioId) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // exclui o registro
      PreparedStatement preparedStatement = SqlTools.prepareDelete(getConnection(),
                                                                   getTableName(),
                                                                   FIELD_USUARIO_ID.getFieldName(getTableName()) + "=" + usuarioId);
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
   * Insere o(a) Papel Usu�rio identificado(a) por 'papelUsuarioInfo'.
   * @param papelUsuarioInfo PapelUsuarioInfo contendo as informa��es do(a) Papel Usu�rio que se
   *                    deseja incluir.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public void insert(PapelUsuarioInfo papelUsuarioInfo) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // insere o registro
      super.insert(papelUsuarioInfo);
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
   * Retorna um PapelUsuarioInfo referente a(o) Papel Usu�rio
   * indicado(a) pelos par�metros que representam sua chave prim�ria.
   * @param papelId Papel Id.
   * @param usuarioId Usu�rio Id.
   * @return Retorna um PapelUsuarioInfo referente a(o) Papel Usu�rio
   * indicado pelos par�metros que representam sua chave prim�ria.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PapelUsuarioInfo selectByPapelIdUsuarioId(
              int papelId,
              int usuarioId
         ) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // prepara a consulta
      PreparedStatement statement = prepareSelect(
                                                  "(" + FIELD_PAPEL_ID.getFieldName(getTableName()) + "=?) AND " +
                                                  "(" + FIELD_USUARIO_ID.getFieldName(getTableName()) + "=?)"
                                          );
      statement.setInt(1, papelId);
      statement.setInt(2, usuarioId);
      PapelUsuarioInfo[] result = (PapelUsuarioInfo[])select(statement);
      // retorna
      if (result.length == 0)
        throw new RecordNotFoundException("Nenhum Papel Usu�rio encontrado.");
      else if (result.length > 1)
        throw new ManyRecordsFoundException("Mais de um Papel Usu�rio encontrado.");
      else {
        // salva tudo
        getFacade().commitTransaction();
        return result[0];
      } // if
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // mostra exce��o
      throw e;
    } // try-catch
  }

  /**
   * Retorna um PapelUsuarioInfo[] contendo a lista de Papel Usu�rio
   * indicados(as) pelos par�metros que representam sua chave secund�ria.
   * @param papelId Papel Id.
   * @return Retorna um PapelUsuarioInfo[] contendo a lista de Papel Usu�rio
   * indicados(as) pelos par�metros que representam sua chave secund�ria.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PapelUsuarioInfo[] selectByPapelId(int papelId) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // prepara a consulta
      String[] orderFieldNames = {Usuario.FIELD_NOME.getFieldName(LOOKUP_USUARIO.getName())};
      PreparedStatement statement = prepareSelect(
                                                  orderFieldNames,
                                                  "(" + FIELD_PAPEL_ID.getFieldName(getTableName()) + " = ?) AND " +
                                                  // se que efetuou logon n�o � Super Usu�rio...n�o exibe os Pap�is ocultos
                                                  "((? = 1) OR (" + Usuario.FIELD_NOME.getFieldName(LOOKUP_USUARIO.getName()) + " NOT LIKE ?))"
                                                );
      statement.setInt(1, papelId);
      statement.setInt(2, ((getFacade().getLoggedUser() == null) || getFacade().getLoggedUser().getSuperUser() ? 1 : 0));
      statement.setString(3, "@%");
      // nosso resultado
      EntityInfo[] result = select(statement);
      // salva tudo
      getFacade().commitTransaction();
      // retorna
      return (PapelUsuarioInfo[])result;
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // mostra exce��o
      throw e;
    } // try-catch
  }

  /**
   * Retorna um PapelUsuarioInfo[] contendo a lista de Papel Usu�rio
   * indicados(as) pelos par�metros que representam sua chave secund�ria.
   * @param usuarioId Usu�rio Id.
   * @return Retorna um PapelUsuarioInfo[] contendo a lista de Papel Usu�rio
   * indicados(as) pelos par�metros que representam sua chave secund�ria.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PapelUsuarioInfo[] selectByUsuarioId(
              int usuarioId
         ) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // prepara a consulta
      String[] orderFieldNames = {Papel.FIELD_NOME.getFieldName(LOOKUP_PAPEL.getName())};
      PreparedStatement statement = prepareSelect(
                                                  orderFieldNames,
                                                  "(" + FIELD_USUARIO_ID.getFieldName(getTableName()) + " = ?) AND " +
                                                  // se quem efetuou logon n�o � Super Usu�rio...n�o exibe os Pap�is ocultos
                                                  "((? = 1) OR (" + Papel.FIELD_NOME.getFieldName(LOOKUP_PAPEL.getName()) + " NOT LIKE ?))"
                                                );
      statement.setInt(1, usuarioId);
      statement.setInt(2, ((getFacade().getLoggedUser() == null) || getFacade().getLoggedUser().getSuperUser() ? 1 : 0));
      statement.setString(3, "@%");
      // nosso resultado
      EntityInfo[] result = select(statement);
      // salva tudo
      getFacade().commitTransaction();
      // retorna
      return (PapelUsuarioInfo[])result;
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // mostra exce��o
      throw e;
    } // try-catch
  }

  /**
   * Atualiza o(a) Papel Usu�rio identificado(a) por 'papelUsuarioInfo'.
   * @param papelUsuarioInfo PapelUsuarioInfo contendo as informa��es do(a) Papel Usu�rio que se
   *                    deseja atualizar.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public void update(PapelUsuarioInfo papelUsuarioInfo) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // atualiza o registro
      super.update(papelUsuarioInfo);
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
