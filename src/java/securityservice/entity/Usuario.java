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
 * Representa a entidade Usuário no banco de dados da aplicação.
 */
public class Usuario extends Entity {

  // identificação da classe
  static public final String CLASS_NAME = "securityservice.entity.Usuario";
  // nossa ajuda
  static public final String HELP = "Cadastre os Usuários e os associe aos Papéis que "
                                  + "eles exercem na aplicação. Se um Usuário for associado "
                                  + "a mais de um Papel, seus direitos serão iguais à soma "
                                  + "dos direitos dos seus Papéis.";
  // nossas ações
  static public final Action ACTION          = new Action("usuario", "Usuário", "Mantém o cadastro dos Usuários e os Papéis que eles exercem na aplicação.", HELP, "entity/usuario.jsp", "Security Service", "", Action.CATEGORY_ENTITY, false, false);
  static public final Action ACTION_CADASTRO = new Action("usuarioCadastro", "", "", "", "entity/usuariocadastro.jsp", "", "", Action.CATEGORY_ENTITY, false, false);
  // nossos comandos
  static public final Command COMMAND_DELETE = ACTION.addCommand(new Command(Command.COMMAND_DELETE, "Excluir", "Exclui o Usuário selecionado."));
  static public final Command COMMAND_EDIT   = ACTION.addCommand(new Command(Command.COMMAND_EDIT,   "Editar",  "Edita o Usuário selecionado."));
  static public final Command COMMAND_INSERT = ACTION.addCommand(new Command(Command.COMMAND_INSERT, "Inserir", "Insere um novo Usuário."));
  static public final Command COMMAND_SAVE   = ACTION.addCommand(new Command(Command.COMMAND_SAVE,   "Salvar",  "Salva o Usuário que está sendo editado ou inserido."));
  // nossos campos
  static public final EntityField FIELD_USUARIO_ID = new EntityField("in_usuario_id", "Usuário Id", "", "usuarioId", Types.INTEGER, 4, 0, true, EntityField.ALIGN_RIGHT, false, true, EntityField.FORMAT_INTEGER, "", true);
  static public final EntityField FIELD_NOME = new EntityField("va_nome", "Nome", "Informe o Nome.", "nome", Types.VARCHAR, 255, 0, false, EntityField.ALIGN_LEFT, false, true, EntityField.FORMAT_NONE, "", true, "", "value != ''", "Informe o(a) Nome");
  static public final EntityField FIELD_DESCRICAO = new EntityField("va_descricao", "Descrição", "Informe a Descrição.", "descricao", Types.VARCHAR, 255, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_NONE, "", false);
  static public final EntityField FIELD_EMAIL = new EntityField("va_email", "E-mail", "Informe o E-mail.", "email", Types.VARCHAR, 255, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_NONE, "", true, "", "value != ''", "Informe o(a) Email");
  static public final EntityField FIELD_SENHA = new EntityField("va_senha", "Senha", "Informe a Senha. É reconmendável utilizar letras, números e ter o tamanho de 6 caracteres.", "senha", Types.VARCHAR, 255, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_NONE, "", true, "", "value != ''", "Informe o(a) Senha");
  static public final EntityField FIELD_NIVEL = new EntityField("va_nivel", "Nível", "Informe o Nível hierárquico. Ex.: <b>1</b> ou <b>1.1</b> ou <b>1.1.2</b>. Esta informação é utilizada apenas por funcionalidades específicas da aplicação.", "nivel", Types.VARCHAR, 50, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_NONE, "", false);
  static public final EntityField FIELD_INATIVO = new EntityField("sm_inativo", "Inativo", "Selecione se o usuário está Inativo. Se sim, ele não poderá efetuar logon na aplicação.", "inativo", Types.SMALLINT, 2, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_INTEGER, "", false);
  static public final EntityField FIELD_ALTERAR_SENHA = new EntityField("sm_alterar_senha", "Alterar Senha no próximo logon", "Selecione se o Usuário deve alterar sua senha no próximo logon.", "alterarSenha", Types.SMALLINT, 2, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_INTEGER, "", false);
  static public final EntityField FIELD_NAO_PODE_ALTERAR_SENHA = new EntityField("sm_nao_pode_alterar_senha", "Não pode alterar Senha", "Selecione se o Usuário não pode alterar sua senha.", "naoPodeAlterarSenha", Types.SMALLINT, 2, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_INTEGER, "", false);
  static public final EntityField FIELD_ALTERACAO_SENHA = new EntityField("da_alteracao_senha", "Última alteração de Senha", "", "alteracaoSenha", Types.TIMESTAMP, 8, 0, false, EntityField.ALIGN_LEFT, false, false, EntityField.FORMAT_DATE_TIME, "", false);
  static public final EntityField FIELD_HASH = new EntityField("in_hash", "Hash", "", "hash", Types.INTEGER, 4, 0, false, EntityField.ALIGN_RIGHT, true, true, EntityField.FORMAT_INTEGER, "", true);
  static public final EntityField FIELD_DATA_EXPIRACAO = new EntityField("da_expiracao", "Data Expiração", "", "dataExpiracao", Types.TIMESTAMP, 10, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_DATE, "", false);
  static public final EntityField FIELD_TIPO_EXPIRACAO = new EntityField("sm_tipo_expiracao", "Tipo Expiração", "", "tipoExpiracao", Types.SMALLINT, 2, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_INTEGER, "", false);
  static public final EntityField FIELD_DATA_ULTIMO_LOGON = new EntityField("dt_ultimo_logon", "Data Último Logon", "", "dataUltimoLogon", Types.TIMESTAMP, 10, 0, false, EntityField.ALIGN_LEFT, true, true, EntityField.FORMAT_DATE_TIME, "", false);

  static public final String[] NAO_SIM = {"Não", "Sim"};
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
   * Construtor padrão.
   */
  public Usuario() {
    // nossas ações
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
   * Exclui o(a) Usuário informado(a) por 'usuarioInfo'.
   * @param usuarioInfo UsuarioInfo referente a(o) Usuário
   *        que se deseja excluir.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public void delete(UsuarioInfo usuarioInfo) throws Exception {
    // inicia transação
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
      // lança exceção
      throw e;
    } // try-catch
  }

  /**
   * Descriptografa as senhas de 'usuarioInfoList'.
   * @param usuarioInfoList UsuarioInfo[] cujas senhas serão descriptografadas.
   */
  private void decryptSenhas(UsuarioInfo[] usuarioInfoList) {
    // loop nos usuários
    for (int i=0; i<usuarioInfoList.length; i++) {
      // usuário da vez
      UsuarioInfo usuarioInfo = usuarioInfoList[i];
      // se temos uma senha válida...descriptografa
      if (usuarioInfo.getSenha().endsWith("="))
        usuarioInfo.setSenha(encrypter(usuarioInfo).decrypt(usuarioInfo.getSenha()));
    } // for
  }

  /**
   * Retorna um Encrypter capaz de criptografar ou descriptografar corretamente
   * dados de UsuarioInfo.
   * @param usuarioInfo UsuarioInfo cujos dados serão criptografados ou
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
   * @param usuarioInfo UsuarioInfo cuja senha será descriptografada.
   */
  private void encryptSenha(UsuarioInfo usuarioInfo) {
    // se a senha não está criptografada...criptografa
    if (!usuarioInfo.getSenha().isEmpty() && !usuarioInfo.getSenha().endsWith("="))
      usuarioInfo.setSenha(encrypter(usuarioInfo).encrypt(usuarioInfo.getSenha()));
  }

  /**
   * Insere o(a) Usuário identificado(a) por 'usuarioInfo'.
   * @param usuarioInfo UsuarioInfo contendo as informações do(a) Usuário que se
   *                    deseja incluir.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public void insert(UsuarioInfo usuarioInfo) throws Exception {
    // inicia transação
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
      // mostra exceção
      throw e;
    } // try-catch
  }

  /**
   * Retorna um UsuarioInfo referente a(o) Usuário
   * indicado(a) pelos parâmetros que representam sua chave primária.
   * @param usuarioId Usuário Id.
   * @return Retorna um UsuarioInfo referente a(o) Usuário
   * indicado pelos parâmetros que representam sua chave primária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public UsuarioInfo selectByUsuarioId(
              int usuarioId
         ) throws Exception {
    // inicia transação
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
        throw new RecordNotFoundException("Nenhum Usuário encontrado.");
      else if (result.length > 1)
        throw new ManyRecordsFoundException("Mais de um Usuário encontrado.");
      else {
        // salva tudo
        getFacade().commitTransaction();
        return result[0];
      } // if
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // mostra exceção
      throw e;
    } // try-catch
  }

  /**
   * Retorna um UsuarioInfo referente a(o) Usuário
   * indicado(a) pelos parâmetros que representam sua chave primária.
   * @param nome Nome.
   * @param email Email.
   * @return Retorna um UsuarioInfo referente a(o) Usuário
   * indicado pelos parâmetros que representam sua chave primária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public UsuarioInfo selectByNomeOrEmail(
              String nome,
              String email
         ) throws Exception {
    // inicia transação
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
        throw new RecordNotFoundException("Nenhum Usuário encontrado.");
      else if (result.length > 1)
        throw new ManyRecordsFoundException("Mais de um Usuário encontrado.");
      else {
        // salva tudo
        getFacade().commitTransaction();
        return result[0];
      } // if
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      // mostra exceção
      throw e;
    } // try-catch
  }

  /**
   * Retorna um UsuarioInfo[] contendo a lista de Usuário
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * @param nome Nome.
   * @return Retorna um UsuarioInfo[] contendo a lista de Usuário
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public UsuarioInfo[] selectByNome(
              String nome
         ) throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // prepara a consulta
      String[] orderFieldNames = {FIELD_NOME.getFieldName(getTableName())};
      PreparedStatement statement = prepareSelect(
                                                  orderFieldNames,
                                                  "(" + FIELD_NOME.getFieldName(getTableName()) + " LIKE ?) AND " +
                                                  // se quem efetuou logon não é Super Usuário...não exibe os Papéis ocultos
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
      // mostra exceção
      throw e;
    } // try-catch
  }

  /**
   * Atualiza o(a) Usuário identificado(a) por 'usuarioInfo'.
   * @param usuarioInfo UsuarioInfo contendo as informações do(a) Usuário que se
   *                    deseja atualizar.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public void update(UsuarioInfo usuarioInfo) throws Exception {
    // inicia transação
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
      // mostra exceção
      throw e;
    } // try-catch
  }

}
