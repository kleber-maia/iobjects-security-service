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

/**
 * Representa a entidade Papel Rela��o Mestre no banco de dados da aplica��o.
 */
public class PapelRelacaoMestre extends Entity {

  // identifica��o da classe
  static public final String CLASS_NAME = "securityservice.entity.PapelRelacaoMestre";
  // nossas a��es
  static public final Action ACTION          = new Action("papelRelacaoMestre", "Papel Rela��o Mestre", "Cadastro de Papel Rela��o Mestre", "", "entity/papelrelacaomestre.jsp", "Seuciry Service", "", Action.CATEGORY_ENTITY, false, false);
  static public final Action ACTION_CADASTRO = new Action("papelRelacaoMestreCadastro", "", "", "", "entity/papelrelacaomestrecadastro.jsp", "", "", Action.CATEGORY_ENTITY, false, false);
  // nossos comandos
  static public final Command COMMAND_DELETE = ACTION.addCommand(new Command(Command.COMMAND_DELETE, "Excluir", ""));
  static public final Command COMMAND_EDIT   = ACTION.addCommand(new Command(Command.COMMAND_EDIT,   "Editar",  ""));
  static public final Command COMMAND_INSERT = ACTION.addCommand(new Command(Command.COMMAND_INSERT, "Inserir", ""));
  static public final Command COMMAND_SAVE   = ACTION.addCommand(new Command(Command.COMMAND_SAVE,   "Salvar",  ""));
  // nossos campos
  static public final EntityField FIELD_PAPEL_ID = new EntityField("in_papel_id", "Papel Id", "Informe o(a) Papel Id", "papelId", Types.INTEGER, 4, 0, true, EntityField.ALIGN_RIGHT, false, true, EntityField.FORMAT_INTEGER, "", true);
  static public final EntityField FIELD_RELACAO_MESTRE = new EntityField("va_relacao_mestre", "Rela��o Mestre", "Informe o(a) Rela��o Mestre", "relacaoMestre", Types.VARCHAR, 255, 0, true, EntityField.ALIGN_LEFT, false, true, EntityField.FORMAT_NONE, "", true);
  static public final EntityField FIELD_PRIVILEGIADO = new EntityField("sm_privilegiado", "Privilegiado", "Informe o(a) Privilegiado", "privilegiado", Types.SMALLINT, 2, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_INTEGER, "", false);
  static public final EntityField FIELD_HASH = new EntityField("in_hash", "Hash", "Informe o(a) Hash", "hash", Types.INTEGER, 4, 0, false, EntityField.ALIGN_RIGHT, true, true, EntityField.FORMAT_INTEGER, "", false);
  // nossos par�metros de usu�rio
  public final Param USER_PARAM_PAPEL_ID = new Param("userParamPapelId", "Papel Id", "Informe o(a) Papel Id", "");

  static public final String[] NAO_SIM = {"N�o", "Sim"};
  static public final int NAO = 0;
  static public final int SIM = 1;

  {
    // define os valores de pesquisa dos campos
    FIELD_PRIVILEGIADO.setLookupList(NAO_SIM);
  }

  /**
   * Construtor padr�o.
   */
  public PapelRelacaoMestre() {
    // nossas a��es
    actionList().add(ACTION);
    actionList().add(ACTION_CADASTRO);
    // nossa tabela
    setTableName("securityservice_papel_relacao_mestre");
    // nossos campos
    fieldList().add(FIELD_PAPEL_ID);
    fieldList().add(FIELD_RELACAO_MESTRE);
    fieldList().add(FIELD_PRIVILEGIADO);
    fieldList().add(FIELD_HASH);
    // nossos par�metros de usu�rio
    userParamList().add(USER_PARAM_PAPEL_ID);
  }

  /**
   * Exclui o(a) Papel Rela��o Mestre informado(a) por 'papelRelacaoMestreInfo'.
   * @param papelRelacaoMestreInfo PapelRelacaoMestreInfo referente a(o) Papel Rela��o Mestre
   *        que se deseja excluir.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public void delete(PapelRelacaoMestreInfo papelRelacaoMestreInfo) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // exclui o registro
      super.delete(papelRelacaoMestreInfo);
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
   * Exclui os(as) Papel Rela��o Mestre informados(as) por 'papelId'.
   * @param papelId int Papel Id das rela��es mestre que se deseja excluir.
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
   * Insere o(a) Papel Rela��o Mestre identificado(a) por 'papelRelacaoMestreInfo'.
   * @param papelRelacaoMestreInfo PapelRelacaoMestreInfo contendo as informa��es do(a) Papel Rela��o Mestre que se
   *                    deseja incluir.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public void insert(PapelRelacaoMestreInfo papelRelacaoMestreInfo) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // insere o registro
      super.insert(papelRelacaoMestreInfo);
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

  public void initialize() {
    // se a rela��o mestre est� ativa
    if (getFacade().masterRelationInformation().getActive()) {
      // Titulo da rela��o mestre
      String masterRelationCaption = getFacade().masterRelationInformation().getCaption();
      // define as descri��es dos comandos
      COMMAND_DELETE.setDescription("Excluir " + masterRelationCaption);
      COMMAND_EDIT.setDescription("Editar " + masterRelationCaption);
      COMMAND_INSERT.setDescription("Inserir " + masterRelationCaption);
      COMMAND_SAVE.setDescription("Salvar " + masterRelationCaption);
      // define o caption do campo
      FIELD_RELACAO_MESTRE.setCaption(masterRelationCaption);
    } // if
  }

  /**
   * Retorna um PapelRelacaoMestreInfo referente a(o) Papel Rela��o Mestre
   * indicado(a) pelos par�metros que representam sua chave prim�ria.
   * @param papelId Papel Id.
   * @param relacaoMestre Rela��o Mestre.
   * @return Retorna um PapelRelacaoMestreInfo referente a(o) Papel Rela��o Mestre
   * indicado pelos par�metros que representam sua chave prim�ria.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PapelRelacaoMestreInfo selectByPapelIdRelacaoMestre(
              int papelId,
              String relacaoMestre
         ) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // prepara a consulta
      PreparedStatement statement = prepareSelect(
                                                  "(" + FIELD_PAPEL_ID.getFieldName(getTableName()) + "=?) AND " +
                                                  "(" + FIELD_RELACAO_MESTRE.getFieldName(getTableName()) + "=?)"
                                          );
      statement.setInt(1, papelId);
      statement.setString(2, relacaoMestre);
      PapelRelacaoMestreInfo[] result = (PapelRelacaoMestreInfo[])select(statement);
      // retorna
      if (result.length == 0)
        throw new RecordNotFoundException("Nenhum Papel Rela��o Mestre encontrado.");
      else if (result.length > 1)
        throw new ManyRecordsFoundException("Mais de um Papel Rela��o Mestre encontrado.");
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
   * Retorna um PapelRelacaoMestreInfo[] contendo a lista de Papel Rela��o Mestre
   * indicados(as) pelos par�metros que representam sua chave secund�ria.
   * @param papelId Papel Id.
   * @return Retorna um PapelRelacaoMestreInfo[] contendo a lista de Papel Rela��o Mestre
   * indicados(as) pelos par�metros que representam sua chave secund�ria.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PapelRelacaoMestreInfo[] selectByPapelId(
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
      return (PapelRelacaoMestreInfo[])result;
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // mostra exce��o
      throw e;
    } // try-catch
  }

  /**
   * Atualiza o(a) Papel Rela��o Mestre identificado(a) por 'papelRelacaoMestreInfo'.
   * @param papelRelacaoMestreInfo PapelRelacaoMestreInfo contendo as informa��es do(a) Papel Rela��o Mestre que se
   *                    deseja atualizar.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public void update(PapelRelacaoMestreInfo papelRelacaoMestreInfo) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // atualiza o registro
      super.update(papelRelacaoMestreInfo);
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
