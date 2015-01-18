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

import securityservice.*;
import securityservice.misc.TipoExpiracao;

/**
 * Representa a entidade Usu�rio no banco de dados da aplica��o.
 */
public class Usuario extends Entity {

  // identifica��o da classe
  static public final String CLASS_NAME = "securityservice.entity.Usuario";
  // nossa ajuda
  static public final String HELP = "Cadastre os Usu�rios e os associe aos Pap�is que "
                                  + "eles exercem na aplica��o. Se um Usu�rio for associado "
                                  + "a mais de um Papel, seus direitos ser�o iguais � soma "
                                  + "dos direitos dos seus Pap�is.";
  // nossas a��es
  static public final Action ACTION          = new Action("usuario", "Usu�rio", "Mant�m o cadastro dos Usu�rios e os Pap�is que eles exercem na aplica��o.", HELP, "entity/usuario.jsp", "Security Service", "", Action.CATEGORY_ENTITY, false, false);
  static public final Action ACTION_CADASTRO = new Action("usuarioCadastro", "", "", "", "entity/usuariocadastro.jsp", "", "", Action.CATEGORY_ENTITY, false, false);
  // nossos comandos
  static public final Command COMMAND_DELETE = ACTION.addCommand(new Command(Command.COMMAND_DELETE, "Excluir", "Exclui o Usu�rio selecionado."));
  static public final Command COMMAND_EDIT   = ACTION.addCommand(new Command(Command.COMMAND_EDIT,   "Editar",  "Edita o Usu�rio selecionado."));
  static public final Command COMMAND_INSERT = ACTION.addCommand(new Command(Command.COMMAND_INSERT, "Inserir", "Insere um novo Usu�rio."));
  static public final Command COMMAND_SAVE   = ACTION.addCommand(new Command(Command.COMMAND_SAVE,   "Salvar",  "Salva o Usu�rio que est� sendo editado ou inserido."));
  // nossos campos
  static public final EntityField FIELD_USUARIO_ID = new EntityField("in_usuario_id", "Usu�rio Id", "", "usuarioId", Types.INTEGER, 4, 0, true, EntityField.ALIGN_RIGHT, false, true, EntityField.FORMAT_INTEGER, "", true);
  static public final EntityField FIELD_NOME = new EntityField("va_nome", "Nome", "Informe o Nome.", "nome", Types.VARCHAR, 255, 0, false, EntityField.ALIGN_LEFT, false, true, EntityField.FORMAT_NONE, "", true, "", "value != ''", "Informe o(a) Nome");
  static public final EntityField FIELD_DESCRICAO = new EntityField("va_descricao", "Descri��o", "Informe a Descri��o.", "descricao", Types.VARCHAR, 255, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_NONE, "", false);
  static public final EntityField FIELD_EMAIL = new EntityField("va_email", "E-mail", "Informe o E-mail.", "email", Types.VARCHAR, 255, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_NONE, "", true, "", "value != ''", "Informe o(a) Email");
  static public final EntityField FIELD_SENHA = new EntityField("va_senha", "Senha", "Informe a Senha. � reconmend�vel utilizar letras, n�meros e ter o tamanho de 6 caracteres.", "senha", Types.VARCHAR, 255, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_NONE, "", true, "", "value != ''", "Informe o(a) Senha");
  static public final EntityField FIELD_NIVEL = new EntityField("va_nivel", "N�vel", "Informe o N�vel hier�rquico. Ex.: <b>1</b> ou <b>1.1</b> ou <b>1.1.2</b>. Esta informa��o � utilizada apenas por funcionalidades espec�ficas da aplica��o.", "nivel", Types.VARCHAR, 50, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_NONE, "", false);
  static public final EntityField FIELD_INATIVO = new EntityField("sm_inativo", "Inativo", "Selecione se o usu�rio est� Inativo. Se sim, ele n�o poder� efetuar logon na aplica��o.", "inativo", Types.SMALLINT, 2, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_INTEGER, "", false);
  static public final EntityField FIELD_ALTERAR_SENHA = new EntityField("sm_alterar_senha", "Alterar Senha no pr�ximo logon", "Selecione se o Usu�rio deve alterar sua senha no pr�ximo logon.", "alterarSenha", Types.SMALLINT, 2, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_INTEGER, "", false);
  static public final EntityField FIELD_NAO_PODE_ALTERAR_SENHA = new EntityField("sm_nao_pode_alterar_senha", "N�o pode alterar Senha", "Selecione se o Usu�rio n�o pode alterar sua senha.", "naoPodeAlterarSenha", Types.SMALLINT, 2, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_INTEGER, "", false);
  static public final EntityField FIELD_ALTERACAO_SENHA = new EntityField("da_alteracao_senha", "�ltima altera��o de Senha", "", "alteracaoSenha", Types.TIMESTAMP, 8, 0, false, EntityField.ALIGN_LEFT, false, false, EntityField.FORMAT_DATE_TIME, "", false);
  static public final EntityField FIELD_HASH = new EntityField("in_hash", "Hash", "", "hash", Types.INTEGER, 4, 0, false, EntityField.ALIGN_RIGHT, true, true, EntityField.FORMAT_INTEGER, "", true);
  static public final EntityField FIELD_DATA_EXPIRACAO = new EntityField("da_expiracao", "Data Expira��o", "", "dataExpiracao", Types.TIMESTAMP, 10, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_DATE, "", false);
  static public final EntityField FIELD_TIPO_EXPIRACAO = new EntityField("sm_tipo_expiracao", "Tipo Expira��o", "", "tipoExpiracao", Types.SMALLINT, 2, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_INTEGER, "", false);
  static public final EntityField FIELD_DATA_ULTIMO_LOGON = new EntityField("dt_ultimo_logon", "Data �ltimo Logon", "", "dataUltimoLogon", Types.TIMESTAMP, 10, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_DATE_TIME, "", false);

  static public final String[] NAO_SIM = {"N�o", "Sim"};
  static public final int NAO = 0;
  static public final int SIM = 1;

  {
    // define os valores de pesquisa dos campos
    FIELD_ALTERAR_SENHA.setLookupList(NAO_SIM);
    FIELD_INATIVO.setLookupList(NAO_SIM);
    FIELD_NAO_PODE_ALTERAR_SENHA.setLookupList(NAO_SIM);
    FIELD_TIPO_EXPIRACAO.setLookupList(TipoExpiracao.LOOKUP_LIST_FOR_FIELD);
  }

  /**
   * Construtor padr�o.
   */
  public Usuario() {
    // nossas a��es
    actionList().add(ACTION);
    actionList().add(ACTION_CADASTRO);
    // nossa tabela
    setTableName("securityservice_usuario");
    // nossos campos
    fieldList().add(FIELD_USUARIO_ID);
    fieldList().add(FIELD_NOME);
    fieldList().add(FIELD_DESCRICAO);
    fieldList().add(FIELD_EMAIL);
    fieldList().add(FIELD_SENHA);
    fieldList().add(FIELD_NIVEL);
    fieldList().add(FIELD_INATIVO);
    fieldList().add(FIELD_ALTERAR_SENHA);
    fieldList().add(FIELD_NAO_PODE_ALTERAR_SENHA);
    fieldList().add(FIELD_ALTERACAO_SENHA);
    fieldList().add(FIELD_HASH);
    fieldList().add(FIELD_DATA_EXPIRACAO);
    fieldList().add(FIELD_TIPO_EXPIRACAO);
    fieldList().add(FIELD_DATA_ULTIMO_LOGON);
  }

  /**
   * Exclui o(a) Usu�rio informado(a) por 'usuarioInfo'.
   * @param usuarioInfo UsuarioInfo referente a(o) Usu�rio
   *        que se deseja excluir.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public void delete(UsuarioInfo usuarioInfo) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // exclui os registros de PapelUsuario relacionados
      PapelUsuario papelUsuario = (PapelUsuario)getFacade().getEntity(PapelUsuario.CLASS_NAME);
      papelUsuario.deleteByUsuarioId(usuarioInfo.getUsuarioId());
      // exclui o registro
      super.delete(usuarioInfo);
      // grava tudo
      getFacade().commitTransaction();
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // lan�a exce��o
      throw e;
    } // try-catch
  }

  /**
   * Descriptografa as senhas de 'usuarioInfoList'.
   * @param usuarioInfoList UsuarioInfo[] cujas senhas ser�o descriptografadas.
   */
  private void decryptSenhas(UsuarioInfo[] usuarioInfoList) {
    // loop nos usu�rios
    for (int i=0; i<usuarioInfoList.length; i++) {
      // usu�rio da vez
      UsuarioInfo usuarioInfo = usuarioInfoList[i];
      // se temos uma senha v�lida...descriptografa
      if (usuarioInfo.getSenha().endsWith("="))
        usuarioInfo.setSenha(encrypter(usuarioInfo).decrypt(usuarioInfo.getSenha()));
    } // for
  }

  /**
   * Retorna um Encrypter capaz de criptografar ou descriptografar corretamente
   * dados de UsuarioInfo.
   * @param usuarioInfo UsuarioInfo cujos dados ser�o criptografados ou
   *                    descriptografados.
   * @return Encrypter Retorna um Encrypter capaz de criptografar ou descriptografar
   *                   corretamente dados de UsuarioInfo.
   */
  private Encrypter encrypter(UsuarioInfo usuarioInfo) {
    return new Encrypter(Encrypter.SCHEME_DESEDE,
                         StringTools.reverseCombination(usuarioInfo.getUsuarioId() + usuarioInfo.getNome(),
                                                        Encrypter.KEY_LENGTH));
  }

  /**
   * Criptografa a senha de 'usuarioInfo'.
   * @param usuarioInfo UsuarioInfo cuja senha ser� descriptografada.
   */
  private void encryptSenha(UsuarioInfo usuarioInfo) {
    // se a senha n�o est� criptografada...criptografa
    if (!usuarioInfo.getSenha().isEmpty() && !usuarioInfo.getSenha().endsWith("="))
      usuarioInfo.setSenha(encrypter(usuarioInfo).encrypt(usuarioInfo.getSenha()));
  }

  /**
   * Insere o(a) Usu�rio identificado(a) por 'usuarioInfo'.
   * @param usuarioInfo UsuarioInfo contendo as informa��es do(a) Usu�rio que se
   *                    deseja incluir.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public void insert(UsuarioInfo usuarioInfo) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // define o Id
      if (usuarioInfo.getUsuarioId() == 0)
        usuarioInfo.setUsuarioId(getNextSequence(FIELD_USUARIO_ID));
      // senha atual
      String senha = usuarioInfo.getSenha();
      // criptografa a senha
      encryptSenha(usuarioInfo);
      // insere o registro
      super.insert(usuarioInfo);
      // retorna a senha
      usuarioInfo.setSenha(senha);
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
   * Retorna um UsuarioInfo referente a(o) Usu�rio
   * indicado(a) pelos par�metros que representam sua chave prim�ria.
   * @param usuarioId Usu�rio Id.
   * @return Retorna um UsuarioInfo referente a(o) Usu�rio
   * indicado pelos par�metros que representam sua chave prim�ria.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public UsuarioInfo selectByUsuarioId(
              int usuarioId
         ) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // prepara a consulta
      PreparedStatement statement = prepareSelect(
                                                  "(" + FIELD_USUARIO_ID.getFieldName(getTableName()) + "=?)"
                                          );
      statement.setInt(1, usuarioId);
      UsuarioInfo[] result = (UsuarioInfo[])select(statement);
      // descriptografa as senhas
      decryptSenhas(result);
      // retorna
      if (result.length == 0)
        throw new RecordNotFoundException("Nenhum Usu�rio encontrado.");
      else if (result.length > 1)
        throw new ManyRecordsFoundException("Mais de um Usu�rio encontrado.");
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
   * Retorna um UsuarioInfo referente a(o) Usu�rio
   * indicado(a) pelos par�metros que representam sua chave prim�ria.
   * @param nome Nome.
   * @param email Email.
   * @return Retorna um UsuarioInfo referente a(o) Usu�rio
   * indicado pelos par�metros que representam sua chave prim�ria.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public UsuarioInfo selectByNomeOrEmail(
              String nome,
              String email
         ) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // prepara a consulta
      PreparedStatement statement = prepareSelect(
                                                  "(" + FIELD_NOME.getFieldName(getTableName()) + "=?) OR " +
                                                  "(" + FIELD_EMAIL.getFieldName(getTableName()) + "=?)"
                                          );
      statement.setString(1, nome);
      statement.setString(2, email);
      UsuarioInfo[] result = (UsuarioInfo[])select(statement);
      // descriptografa as senhas
      decryptSenhas(result);
      // retorna
      if (result.length == 0)
        throw new RecordNotFoundException("Nenhum Usu�rio encontrado.");
      else if (result.length > 1)
        throw new ManyRecordsFoundException("Mais de um Usu�rio encontrado.");
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
   * Retorna um UsuarioInfo[] contendo a lista de Usu�rio
   * indicados(as) pelos par�metros que representam sua chave secund�ria.
   * @param nome Nome.
   * @return Retorna um UsuarioInfo[] contendo a lista de Usu�rio
   * indicados(as) pelos par�metros que representam sua chave secund�ria.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public UsuarioInfo[] selectByNome(
              String nome
         ) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // prepara a consulta
      String[] orderFieldNames = {FIELD_NOME.getFieldName(getTableName())};
      PreparedStatement statement = prepareSelect(
                                                  orderFieldNames,
                                                  "(" + FIELD_NOME.getFieldName(getTableName()) + " LIKE ?) AND " +
                                                  // se quem efetuou logon n�o � Super Usu�rio...n�o exibe os Pap�is ocultos
                                                  "((? = 1) OR (" + FIELD_NOME.getFieldName(getTableName()) + " NOT LIKE ?))"
                                                );
      statement.setString(1, nome + "%");
      statement.setInt(2, ((getFacade().getLoggedUser() != null) && getFacade().getLoggedUser().getSuperUser() ? 1 : 0));
      statement.setString(3, "@%");
      // nosso resultado
      UsuarioInfo[] result = (UsuarioInfo[])select(statement);
      // descriptografa as senhas
      decryptSenhas(result);
      // salva tudo
      getFacade().commitTransaction();
      // retorna
      return result;
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // mostra exce��o
      throw e;
    } // try-catch
  }

  /**
   * Atualiza o(a) Usu�rio identificado(a) por 'usuarioInfo'.
   * @param usuarioInfo UsuarioInfo contendo as informa��es do(a) Usu�rio que se
   *                    deseja atualizar.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public void update(UsuarioInfo usuarioInfo) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // senha atual
      String senha = usuarioInfo.getSenha();
      // criptografa a senha
      encryptSenha(usuarioInfo);
      // atualiza o registro
      super.update(usuarioInfo);
      // retorna a senha
      usuarioInfo.setSenha(senha);
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
