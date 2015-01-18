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

package securityservice;

import java.sql.*;
import java.util.*;

import iobjects.*;
import iobjects.entity.*;
import iobjects.security.*;
import iobjects.util.*;

import securityservice.entity.*;
import securityservice.misc.TipoExpiracao;

/**
 * Implementa a interface iobjects.security.SecurityService provendo os
 * serviços de segurança da aplicação. A lógica e a política de segurança da
 * aplicação também são implementadas e definidas aqui.
 */
public class SecurityService extends BusinessObject implements iobjects.security.SecurityService {

  // identificação da classe
  static public final String CLASS_NAME = "securityservice.SecurityService";

  /**
   * Tamanho mínimo da senha.
   */
  static public final int PASSWORD_MINIMUM_LENGTH = 6;
  /**
   * Tamanho máximo da senha.
   */
  static public final int PASSWORD_MAXIMUM_LENGTH = 24;
  /**
   * Quantidade mínima de números na senha.
   */
  static public final int PASSWORD_MINIMUM_NUMBERS = 2;
  /**
   * Quantidade mínima de letras na senha.
   */
  static public final int PASSWORD_MINIMUM_ALPHA = 2;

  // identificação do super papél e do super usuário
  public final String ROLE_SUPER_USUARIOS_NAME         = "@Super Usuários";
  public final String ROLE_SUPER_USUARIOS_DESCRIPTION  = "Os super usuários têm acesso completo ao sistema e aos seus objetos e não são visíveis pelos administradores.";
  public final String USER_SUPER_USUARIO_NAME          = "@Super Usuário";
  public final String USER_SUPER_USUARIO_DESCRIPTION   = "Conta especial para administração do sistema.";
  public final String USER_SUPER_USUARIO_PASSWORD      = "superusuario";
  // identificação do papél e do usuário administradore
  public final String ROLE_ADMINISTRADORES_NAME        = "Administradores";
  public final String ROLE_ADMINISTRADORES_DESCRIPTION = "Os administradores têm acesso completo ao sistema e aos seus objetos.";
  public final String USER_ADMINISTRADOR_NAME          = "Administrador";
  public final String USER_ADMINISTRADOR_DESCRIPTION   = "Conta padrão para administração do sistema.";
  public final String USER_ADMINISTRADOR_PASSWORD      = "administrador";
  // identificação do papél e do usuário convidado
  public final String ROLE_CONVIDADOS_NAME             = "Convidados";
  public final String ROLE_CONVIDADOS_DESCRIPTION      = "Por padrão os usuários convidados não possuem direitos de acesso aos objetos do sistema.";
  public final String USER_CONVIDADO_NAME              = "Convidado";
  public final String USER_CONVIDADO_DESCRIPTION       = "Conta padrão para acesso como convidado ao sistema.";
  public final String USER_CONVIDADO_PASSWORD          = "convidado";

  private Papel              papel              = null;
  private PapelAcao          papelAcao          = null;
  private PapelRelacaoMestre papelRelacaoMestre = null;
  private PapelUsuario       papelUsuario       = null;
  private Usuario            usuario            = null;

  /**
   * Ação padrão.
   */
  static public Action ACTION = new Action("securityservice",
                                           "Segurança",
                                           "Configura o serviço de segurança da aplicação.",
                                           "",
                                           "securityservice.jsp",
                                           "",
                                           "",
                                           Action.CATEGORY_NONE,
                                           false,
                                           false);

  /**
   * Construtor padrão.
   */
  public SecurityService() {
    // nossas ações
    actionList().add(ACTION);
  }

  /**
   * Altera a senha do usuário identificado por 'userId'.
   * @param userId int Identifacação do usuário.
   * @param oldPassword String Senha antiga.
   * @param newPassword String Nova senha.
   * @throws SecurityException Em caso de a senha anterior não conferir, a nova
   *                           senha não combinar com a política de definições
   *                           de senhas ou na tentativa de acesso ao banco de
   *                           dados.
   */
  public void changePassword(int    userId,
                             String oldPassword,
                             String newPassword) throws iobjects.security.SecurityException {
    try {
      // inicia transação
      getFacade().beginTransaction();
      try {
        // localiza o usuário informado
        UsuarioInfo usuarioInfo = usuario.selectByUsuarioId(userId);
        // se a senha atual não confere...
        if (!usuarioInfo.getSenha().equals(oldPassword))
          throw new Exception("Usuário não encontrada ou senha inválida.");
        // altera a senha
        usuarioInfo.setSenha(newPassword);
        // retira a necessidade de alterar a senha no próximo logon
        usuarioInfo.setAlterarSenha(Usuario.NAO);
        // data da última alteração de senha
        usuarioInfo.setAlteracaoSenha(new Timestamp(DateTools.getActualDate().getTime()));
        // atualiza o usuário
        usuario.update(usuarioInfo);
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
    catch (RecordNotFoundException e) {
      throw new iobjects.security.SecurityException("Usuário não encontrado.");
    } // try-catch
    catch (Exception e) {
      throw new iobjects.security.SecurityException(e.getMessage());
    } // try-catch
  }

  /**
   * Retorna o Action utilizado para acesso à interface do serviço de segurança.
   * @return Action Retorna o Action utilizado para acesso à interface do serviço
   *         de segurança.
   */
  public Action getSecurityServiceAction() {
    return ACTION;
  }

  /**
   * Retorna o usuário identificado por 'userId'.
   * @param userId int Identificação do usuário que se deseja retornar.
   * @return User Retorna o usuário referenciado por 'userId'.
   * @throws SecurityException Em caso de exceção na tentativa de localizar o usuário.
   */
  public User getUser(int userId) throws iobjects.security.SecurityException {
    try {
      // inicia transação
      getFacade().beginTransaction();
      try {
        // localiza o usuário informado
        UsuarioInfo usuarioInfo = usuario.selectByUsuarioId(userId);
        // retorna
        User result = userFromUsuarioInfo(usuarioInfo, false);
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
    catch (RecordNotFoundException e) {
      throw new iobjects.security.SecurityException("Usuário não encontrado.");
    } // try-catch
    catch (Exception e) {
      throw new iobjects.security.SecurityException(e.getMessage());
    } // try-catch
  }

  /**
   * Verifica a existência dos papéis Administradores e Convidados e dos usuários
   * Administrador e Usuário.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *         dados.
   */
  public void initialize() throws Exception {
    // inicia transação
    getFacade().beginTransaction();
    try {
      // instancia nossos objetos de negócio
      papel              = (Papel)getFacade().getEntity(Papel.CLASS_NAME);
      papelAcao          = (PapelAcao)getFacade().getEntity(PapelAcao.CLASS_NAME);
      papelRelacaoMestre = (PapelRelacaoMestre)getFacade().getEntity(PapelRelacaoMestre.CLASS_NAME);
      papelUsuario       = (PapelUsuario)getFacade().getEntity(PapelUsuario.CLASS_NAME);
      usuario            = (Usuario)getFacade().getEntity(Usuario.CLASS_NAME);
      // verifica a existência dos grupos e usuários Administradores e Convidados
      verifyDefaultRoleAndUser(ROLE_SUPER_USUARIOS_NAME,
                               ROLE_SUPER_USUARIOS_DESCRIPTION,
                               true,
                               Papel.TABELA_HORARIO_VAZIA,
                               USER_SUPER_USUARIO_NAME,
                               USER_SUPER_USUARIO_DESCRIPTION,
                               StringTools.format(USER_SUPER_USUARIO_NAME + "@" + getFacade().applicationInformation().getName(), false, false, true, true, false).toLowerCase(),
                               USER_SUPER_USUARIO_PASSWORD,
                               false);
      verifyDefaultRoleAndUser(ROLE_ADMINISTRADORES_NAME,
                               ROLE_ADMINISTRADORES_DESCRIPTION,
                               true,
                               Papel.TABELA_HORARIO_VAZIA,
                               USER_ADMINISTRADOR_NAME,
                               USER_ADMINISTRADOR_DESCRIPTION,
                               StringTools.format(USER_ADMINISTRADOR_NAME + "@" + getFacade().applicationInformation().getName(), false, false, true, true, false).toLowerCase(),
                               USER_ADMINISTRADOR_PASSWORD,
                               false);
      verifyDefaultRoleAndUser(ROLE_CONVIDADOS_NAME,
                               ROLE_CONVIDADOS_DESCRIPTION,
                               false,
                               Papel.TABELA_HORARIO_COMERCIAL,
                               USER_CONVIDADO_NAME,
                               USER_CONVIDADO_DESCRIPTION,
                               StringTools.format(USER_CONVIDADO_NAME + "@" + getFacade().applicationInformation().getName(), false, false, true, true, false).toLowerCase(),
                               USER_CONVIDADO_PASSWORD,
                               true);
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
   * Efetua o logon do usuário identificado por 'name' e 'password' e retorna
   * o User referente. Retorna null caso o 'nome' e 'password' não confiram.
   * @param name String Nome do usuário.
   * @param password String Senha do usuário.
   * @return User Retorna o User referente ao usuário que efetuou logon.
   * @throws SecurityException Em caso de exceção na tentativa de localizar o
   *                           usuário ou efetuar logon.
   */
  public User logon(String name, String password) throws iobjects.security.SecurityException {
    try {
      // inicia transação
      getFacade().beginTransaction();
      try {
        // instância de Usuário
        Usuario usuario = (Usuario)getFacade().getEntity(Usuario.CLASS_NAME);
        // localiza o usuário informado
        UsuarioInfo usuarioInfo = usuario.selectByNomeOrEmail(name, name);
        // se está inativo...exceção
        if (usuarioInfo.getInativo() == Usuario.SIM)
          throw new Exception("Conta de usuário desativada.");
        // se a senha não confere e não é a senha salva...
        if (!usuarioInfo.getSenha().equals(password) &&
            (!password.equals(usuarioInfo.getNome().hashCode() + "$" + usuarioInfo.getSenha().hashCode())))
          throw new Exception("Usuário não encontrado ou senha inválida.");
        // direitos do usuário
        User result = userFromUsuarioInfo(usuarioInfo, true);
        // se usuário expirou e é diferente de ZERO_DATE e não é nem administrador, nem super usuário...
        if (!usuarioInfo.getDataExpiracao().equals(DateTools.ZERO_DATE) && DateTools.getActualDate().after(usuarioInfo.getDataExpiracao()) && (!result.getSuperUser() && !result.getPrivileged()))
          throw new Exception("Conta de usuário expirada.");
        // se não é apenas leitura...
        if (!getFacade().connectionManager().connectionFiles().get(getFacade().getDefaultConnectionName()).readOnly()){
          // altera data do logon
          usuarioInfo.setDataUltimoLogon(new Timestamp(DateTools.getActualDateTime().getTime()));
          // salva
          usuario.update(usuarioInfo);
        } // if
        // salva tudo
        getFacade().commitTransaction();
        // retorna seus direitos
        return result;
      }
      catch (Exception e) {
        // desfaz tudo
        getFacade().rollbackTransaction();
        // mostra exceção
        throw e;
      } // catch
    }
    catch (RecordNotFoundException e) {
      throw new iobjects.security.SecurityException("Usuário não encontrado ou senha inválida.");
    }
    catch (Exception e) {
      throw new iobjects.security.SecurityException(e.getMessage());
    } // try-catch
  }

  /**
   * Retorna um User a partir de 'usuarioInfo'.
   * @param usuarioInfo UsuarioInfo.
   * @param scanUserRights boolean True se os direitos do usuário devem ser
   *                       vasculhados através dos papéis que ele exerce.
   * @return User Retorna um User a partir de 'usuarioInfo'.
   * @throws Exception Em caso de exceção na tentativa de obter as informações
   *                   e direitos do usuário.
   */
  private User userFromUsuarioInfo(UsuarioInfo usuarioInfo,
                                   boolean     scanUserRights) throws Exception {
    // é super usuário?
    boolean superUser = false;
    // é privilegiado?
    boolean privileged = false;
    // lista de ações
    ActionInfoList actionInfoList = new ActionInfoList();
    // relações mestre
    MasterRelationInfoList masterRelationInfoList = new MasterRelationInfoList();

    // se devemos vasculhar os direitos do usuário...
    if (scanUserRights) {
      // inicia transação
      getFacade().beginTransaction();
      try {
        // Papel Super Usuários
        PapelInfo papelSuperUsuariosInfo = papel.selectByNome(ROLE_SUPER_USUARIOS_NAME);
        // obtém os papéis que o usuário exerce
        PapelUsuarioInfo[] papelUsuarioInfoList = papelUsuario.selectByUsuarioId(usuarioInfo.getUsuarioId());
        PapelInfo[] papelInfoList = papel.selectByPapelUsuarioInfoList(papelUsuarioInfoList);
        // pode efetuar logon neste dia e hora?
        boolean canLogonAtDateTime = false;
        // data e hora atuais
        java.util.Date now = new java.util.Date();
        // loop nos papéis a procura de um Papel privilegiado
        for (int i=0; i<papelInfoList.length; i++) {
          // se exerce o Papel de Super Usuário...
          if (papelInfoList[i].getPapelId() == papelSuperUsuariosInfo.getPapelId()) {
            superUser = true;
            privileged = true;
            canLogonAtDateTime = true;
            break;
          }
          // se é privilegiado...
          else if (papelInfoList[i].getPrivilegiado() == Papel.SIM) {
            privileged = true;
            canLogonAtDateTime = true;
          }
          // verifica se pode efetuar logon nesta data e hora...
          else if (!canLogonAtDateTime) {
            canLogonAtDateTime = Papel.canLogonAtDateTime(papelInfoList[i].getTabelaHorario(), now);
          } // if
        } // for
        // se o usuário não é privilegiado...vasculha seus direitos
        if (!privileged) {
          // se não pode efetuar logon nesta data e hora...exceção
          if (!canLogonAtDateTime)
            throw new Exception("Acesso negado a partir deste computador, dia da semana ou horário do dia.");
          // loop nos papéis
          for (int i=0; i<papelInfoList.length; i++) {
            // Papel da vez
            PapelInfo papelInfo = papelInfoList[i];
            // Ações associadas ao Papel
            PapelAcaoInfo[] papelAcaoInfoList = papelAcao.selectByPapelId(papelInfo.getPapelId());
            // loop nas Ações
            for (int w=0; w<papelAcaoInfoList.length; w++) {
              // PapelAção da vez
              PapelAcaoInfo papelAcaoInfo = papelAcaoInfoList[w];
              // verifica se já temos o ActionInfo
              ActionInfo actionInfo = actionInfoList.get(papelAcaoInfo.getAcao());
              // se ainda não temos...cria e adiciona a lista
              if (actionInfo == null)
                actionInfoList.add(new ActionInfo(papelAcaoInfo.getAcao(),
                                                  papelAcaoInfo.getComandosAsArray()));
              // se já temos...apenas adiciona os comandos
              else
                actionInfo.addCommands(papelAcaoInfo.getComandosAsArray());
            } // for w
            // Relações Mestre associadas ao Papel
            PapelRelacaoMestreInfo[] papelRelacaoMestreInfoList = papelRelacaoMestre.selectByPapelId(papelInfo.getPapelId());
            // loop nas Relações Mestre
            for (int w=0; w<papelRelacaoMestreInfoList.length; w++) {
              // Papel Relação Mestre da vez
              PapelRelacaoMestreInfo papelRelacaoMestreInfo = papelRelacaoMestreInfoList[w];
              // verifica se já temos o MasterRelationInfo
              MasterRelationInfo masterRelationInfo = masterRelationInfoList.get(papelRelacaoMestreInfo.getRelacaoMestre());
              // se ainda não temos...cria e adciona a lista
              if (masterRelationInfo == null) {
                masterRelationInfo = new MasterRelationInfo(papelRelacaoMestreInfo.getRelacaoMestre(),
                                                            papelRelacaoMestreInfo.getPrivilegiado() == PapelRelacaoMestre.SIM);
                masterRelationInfoList.add(masterRelationInfo);
              }
              // se já temos...verifica se é privilegiado
              else if (papelRelacaoMestreInfo.getPrivilegiado() == PapelRelacaoMestre.SIM)
                masterRelationInfo.setPrivileged(true);
            } // for w
          } // for i
        } // if
        // salva tudo
        getFacade().commitTransaction();
      }
      catch (Exception e) {
        // desfaz tudo
        getFacade().rollbackTransaction();
        // mostra exceção
        throw e;
      } // try-catch
    } // if
    // retorna
    return new User(usuarioInfo.getUsuarioId(),
                    usuarioInfo.getNome(),
                    usuarioInfo.getDescricao(),
                    usuarioInfo.getEmail(),
                    usuarioInfo.getNivel(),
                    privileged,
                    superUser,
                    usuarioInfo.getAlterarSenha() == Usuario.SIM,
                    usuarioInfo.getNaoPodeAlterarSenha() == Usuario.SIM,
                    actionInfoList,
                    masterRelationInfoList);
  }

  /**
   * Verifica a existência e cria o Papel e Usuário informados.
   * @param roleName String Nome do Papel.
   * @param roleDescription String Descrição do Papel.
   * @param privileged boolean Privilegiado.
   * @param roleTimeTable String Tabela de Horário.
   * @param userName String Nome do Usuário.
   * @param userDescription String Descrição do Usuário.
   * @param userEmail String Email do Usuário.
   * @param userPassword String Senha do Usuário.
   * @param cannotChangePassword boolean Não Pode Alterar Senha.
   * @throws Exception Em caso de exceção na tentativa de localização e inclusão
   *                   do Papel e Usuário informados.
   */
  private void verifyDefaultRoleAndUser(String  roleName,
                                        String  roleDescription,
                                        boolean privileged,
                                        String  roleTimeTable,
                                        String  userName,
                                        String  userDescription,
                                        String  userEmail,
                                        String  userPassword,
                                        boolean cannotChangePassword) throws Exception {
    // suspende a transação
    getFacade().suspendTransaction();
    try {
      // usuário a ser verificado
      UsuarioInfo usuarioInfo = null;
      try {
        // tenta localizar o usuário informado
        usuarioInfo = usuario.selectByNomeOrEmail(userName, userEmail);
      }
      catch (RecordNotFoundException e) {
        // não faz nada
      } // try-catch
      // se o usuário não foi encontrado...insere
      if (usuarioInfo == null) {
        // novo Usuário
        usuarioInfo = new UsuarioInfo(0,
                                      userName,
                                      userDescription,
                                      userEmail,
                                      userPassword,
                                      "",
                                      Usuario.NAO,
                                      Usuario.NAO,
                                      cannotChangePassword ? Usuario.SIM : Usuario.NAO,
                                      DateTools.ZERO_DATE,
                                      0,
                                      DateTools.ZERO_DATE,
                                      TipoExpiracao.BLOQUEIO,
                                      DateTools.ZERO_DATE);
        // insere
        usuario.insert(usuarioInfo);
        // localiza novamente para pegar seu Id
        usuarioInfo = usuario.selectByNomeOrEmail(userName, userEmail);
      } // if

      // papel a ser verificado
      PapelInfo papelInfo = null;
      try {
        // tenta localizar o papel informado
        papelInfo = papel.selectByNome(roleName);
      }
      catch (RecordNotFoundException e) {
        // não faz nada
      } // try-catch
      // se o papel não foi encontrado...insere
      if (papelInfo == null) {
        // novo papel
        papelInfo = new PapelInfo(0,
                                  roleName,
                                  roleDescription,
                                  privileged ? Papel.SIM : Papel.NAO,
                                  roleTimeTable,
                                  0);
        // insere
        papel.insert(papelInfo);
        // localiza novamente para pegar seu Id
        papelInfo = papel.selectByNome(roleName);
      } // if

      // papel usuário a ser verificado
      PapelUsuarioInfo papelUsuarioInfo = null;
      try {
        // tenta localizar o papel usuário informado
        papelUsuarioInfo = papelUsuario.selectByPapelIdUsuarioId(papelInfo.getPapelId(), usuarioInfo.getUsuarioId());
      }
      catch (RecordNotFoundException e) {
        // não faz nada
      } // try-catch
      // se o papel usuário não foi encontrado...insere
      if (papelUsuarioInfo == null) {
        // novo papel usuário
        papelUsuarioInfo = new PapelUsuarioInfo(papelInfo.getPapelId(),
                                                usuarioInfo.getUsuarioId(),
                                                0);
        // insere
        papelUsuario.insert(papelUsuarioInfo);
      } // if
    }
    finally {
      // libera a transação
      getFacade().releaseTransaction();
    } // try-catch
  }

}
