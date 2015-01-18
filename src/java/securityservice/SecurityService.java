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
 * servi�os de seguran�a da aplica��o. A l�gica e a pol�tica de seguran�a da
 * aplica��o tamb�m s�o implementadas e definidas aqui.
 */
public class SecurityService extends BusinessObject implements iobjects.security.SecurityService {

  // identifica��o da classe
  static public final String CLASS_NAME = "securityservice.SecurityService";

  /**
   * Tamanho m�nimo da senha.
   */
  static public final int PASSWORD_MINIMUM_LENGTH = 6;
  /**
   * Tamanho m�ximo da senha.
   */
  static public final int PASSWORD_MAXIMUM_LENGTH = 24;
  /**
   * Quantidade m�nima de n�meros na senha.
   */
  static public final int PASSWORD_MINIMUM_NUMBERS = 2;
  /**
   * Quantidade m�nima de letras na senha.
   */
  static public final int PASSWORD_MINIMUM_ALPHA = 2;

  // identifica��o do super pap�l e do super usu�rio
  public final String ROLE_SUPER_USUARIOS_NAME         = "@Super Usu�rios";
  public final String ROLE_SUPER_USUARIOS_DESCRIPTION  = "Os super usu�rios t�m acesso completo ao sistema e aos seus objetos e n�o s�o vis�veis pelos administradores.";
  public final String USER_SUPER_USUARIO_NAME          = "@Super Usu�rio";
  public final String USER_SUPER_USUARIO_DESCRIPTION   = "Conta especial para administra��o do sistema.";
  public final String USER_SUPER_USUARIO_PASSWORD      = "superusuario";
  // identifica��o do pap�l e do usu�rio administradore
  public final String ROLE_ADMINISTRADORES_NAME        = "Administradores";
  public final String ROLE_ADMINISTRADORES_DESCRIPTION = "Os administradores t�m acesso completo ao sistema e aos seus objetos.";
  public final String USER_ADMINISTRADOR_NAME          = "Administrador";
  public final String USER_ADMINISTRADOR_DESCRIPTION   = "Conta padr�o para administra��o do sistema.";
  public final String USER_ADMINISTRADOR_PASSWORD      = "administrador";
  // identifica��o do pap�l e do usu�rio convidado
  public final String ROLE_CONVIDADOS_NAME             = "Convidados";
  public final String ROLE_CONVIDADOS_DESCRIPTION      = "Por padr�o os usu�rios convidados n�o possuem direitos de acesso aos objetos do sistema.";
  public final String USER_CONVIDADO_NAME              = "Convidado";
  public final String USER_CONVIDADO_DESCRIPTION       = "Conta padr�o para acesso como convidado ao sistema.";
  public final String USER_CONVIDADO_PASSWORD          = "convidado";

  private Papel              papel              = null;
  private PapelAcao          papelAcao          = null;
  private PapelRelacaoMestre papelRelacaoMestre = null;
  private PapelUsuario       papelUsuario       = null;
  private Usuario            usuario            = null;

  /**
   * A��o padr�o.
   */
  static public Action ACTION = new Action("securityservice",
                                           "Seguran�a",
                                           "Configura o servi�o de seguran�a da aplica��o.",
                                           "",
                                           "securityservice.jsp",
                                           "",
                                           "",
                                           Action.CATEGORY_NONE,
                                           false,
                                           false);

  /**
   * Construtor padr�o.
   */
  public SecurityService() {
    // nossas a��es
    actionList().add(ACTION);
  }

  /**
   * Altera a senha do usu�rio identificado por 'userId'.
   * @param userId int Identifaca��o do usu�rio.
   * @param oldPassword String Senha antiga.
   * @param newPassword String Nova senha.
   * @throws SecurityException Em caso de a senha anterior n�o conferir, a nova
   *                           senha n�o combinar com a pol�tica de defini��es
   *                           de senhas ou na tentativa de acesso ao banco de
   *                           dados.
   */
  public void changePassword(int    userId,
                             String oldPassword,
                             String newPassword) throws iobjects.security.SecurityException {
    try {
      // inicia transa��o
      getFacade().beginTransaction();
      try {
        // localiza o usu�rio informado
        UsuarioInfo usuarioInfo = usuario.selectByUsuarioId(userId);
        // se a senha atual n�o confere...
        if (!usuarioInfo.getSenha().equals(oldPassword))
          throw new Exception("Usu�rio n�o encontrada ou senha inv�lida.");
        // altera a senha
        usuarioInfo.setSenha(newPassword);
        // retira a necessidade de alterar a senha no pr�ximo logon
        usuarioInfo.setAlterarSenha(Usuario.NAO);
        // data da �ltima altera��o de senha
        usuarioInfo.setAlteracaoSenha(new Timestamp(DateTools.getActualDate().getTime()));
        // atualiza o usu�rio
        usuario.update(usuarioInfo);
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
    catch (RecordNotFoundException e) {
      throw new iobjects.security.SecurityException("Usu�rio n�o encontrado.");
    } // try-catch
    catch (Exception e) {
      throw new iobjects.security.SecurityException(e.getMessage());
    } // try-catch
  }

  /**
   * Retorna o Action utilizado para acesso � interface do servi�o de seguran�a.
   * @return Action Retorna o Action utilizado para acesso � interface do servi�o
   *         de seguran�a.
   */
  public Action getSecurityServiceAction() {
    return ACTION;
  }

  /**
   * Retorna o usu�rio identificado por 'userId'.
   * @param userId int Identifica��o do usu�rio que se deseja retornar.
   * @return User Retorna o usu�rio referenciado por 'userId'.
   * @throws SecurityException Em caso de exce��o na tentativa de localizar o usu�rio.
   */
  public User getUser(int userId) throws iobjects.security.SecurityException {
    try {
      // inicia transa��o
      getFacade().beginTransaction();
      try {
        // localiza o usu�rio informado
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
        // mostra exce��o
        throw e;
      } // try-catch
    }
    catch (RecordNotFoundException e) {
      throw new iobjects.security.SecurityException("Usu�rio n�o encontrado.");
    } // try-catch
    catch (Exception e) {
      throw new iobjects.security.SecurityException(e.getMessage());
    } // try-catch
  }

  /**
   * Verifica a exist�ncia dos pap�is Administradores e Convidados e dos usu�rios
   * Administrador e Usu�rio.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *         dados.
   */
  public void initialize() throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // instancia nossos objetos de neg�cio
      papel              = (Papel)getFacade().getEntity(Papel.CLASS_NAME);
      papelAcao          = (PapelAcao)getFacade().getEntity(PapelAcao.CLASS_NAME);
      papelRelacaoMestre = (PapelRelacaoMestre)getFacade().getEntity(PapelRelacaoMestre.CLASS_NAME);
      papelUsuario       = (PapelUsuario)getFacade().getEntity(PapelUsuario.CLASS_NAME);
      usuario            = (Usuario)getFacade().getEntity(Usuario.CLASS_NAME);
      // verifica a exist�ncia dos grupos e usu�rios Administradores e Convidados
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
      // mostra exce��o
      throw e;
    } // try-catch
  }

  /**
   * Efetua o logon do usu�rio identificado por 'name' e 'password' e retorna
   * o User referente. Retorna null caso o 'nome' e 'password' n�o confiram.
   * @param name String Nome do usu�rio.
   * @param password String Senha do usu�rio.
   * @return User Retorna o User referente ao usu�rio que efetuou logon.
   * @throws SecurityException Em caso de exce��o na tentativa de localizar o
   *                           usu�rio ou efetuar logon.
   */
  public User logon(String name, String password) throws iobjects.security.SecurityException {
    try {
      // inicia transa��o
      getFacade().beginTransaction();
      try {
        // inst�ncia de Usu�rio
        Usuario usuario = (Usuario)getFacade().getEntity(Usuario.CLASS_NAME);
        // localiza o usu�rio informado
        UsuarioInfo usuarioInfo = usuario.selectByNomeOrEmail(name, name);
        // se est� inativo...exce��o
        if (usuarioInfo.getInativo() == Usuario.SIM)
          throw new Exception("Conta de usu�rio desativada.");
        // se a senha n�o confere e n�o � a senha salva...
        if (!usuarioInfo.getSenha().equals(password) &&
            (!password.equals(usuarioInfo.getNome().hashCode() + "$" + usuarioInfo.getSenha().hashCode())))
          throw new Exception("Usu�rio n�o encontrado ou senha inv�lida.");
        // direitos do usu�rio
        User result = userFromUsuarioInfo(usuarioInfo, true);
        // se usu�rio expirou e � diferente de ZERO_DATE e n�o � nem administrador, nem super usu�rio...
        if (!usuarioInfo.getDataExpiracao().equals(DateTools.ZERO_DATE) && DateTools.getActualDate().after(usuarioInfo.getDataExpiracao()) && (!result.getSuperUser() && !result.getPrivileged()))
          throw new Exception("Conta de usu�rio expirada.");
        // se n�o � apenas leitura...
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
        // mostra exce��o
        throw e;
      } // catch
    }
    catch (RecordNotFoundException e) {
      throw new iobjects.security.SecurityException("Usu�rio n�o encontrado ou senha inv�lida.");
    }
    catch (Exception e) {
      throw new iobjects.security.SecurityException(e.getMessage());
    } // try-catch
  }

  /**
   * Retorna um User a partir de 'usuarioInfo'.
   * @param usuarioInfo UsuarioInfo.
   * @param scanUserRights boolean True se os direitos do usu�rio devem ser
   *                       vasculhados atrav�s dos pap�is que ele exerce.
   * @return User Retorna um User a partir de 'usuarioInfo'.
   * @throws Exception Em caso de exce��o na tentativa de obter as informa��es
   *                   e direitos do usu�rio.
   */
  private User userFromUsuarioInfo(UsuarioInfo usuarioInfo,
                                   boolean     scanUserRights) throws Exception {
    // � super usu�rio?
    boolean superUser = false;
    // � privilegiado?
    boolean privileged = false;
    // lista de a��es
    ActionInfoList actionInfoList = new ActionInfoList();
    // rela��es mestre
    MasterRelationInfoList masterRelationInfoList = new MasterRelationInfoList();

    // se devemos vasculhar os direitos do usu�rio...
    if (scanUserRights) {
      // inicia transa��o
      getFacade().beginTransaction();
      try {
        // Papel Super Usu�rios
        PapelInfo papelSuperUsuariosInfo = papel.selectByNome(ROLE_SUPER_USUARIOS_NAME);
        // obt�m os pap�is que o usu�rio exerce
        PapelUsuarioInfo[] papelUsuarioInfoList = papelUsuario.selectByUsuarioId(usuarioInfo.getUsuarioId());
        PapelInfo[] papelInfoList = papel.selectByPapelUsuarioInfoList(papelUsuarioInfoList);
        // pode efetuar logon neste dia e hora?
        boolean canLogonAtDateTime = false;
        // data e hora atuais
        java.util.Date now = new java.util.Date();
        // loop nos pap�is a procura de um Papel privilegiado
        for (int i=0; i<papelInfoList.length; i++) {
          // se exerce o Papel de Super Usu�rio...
          if (papelInfoList[i].getPapelId() == papelSuperUsuariosInfo.getPapelId()) {
            superUser = true;
            privileged = true;
            canLogonAtDateTime = true;
            break;
          }
          // se � privilegiado...
          else if (papelInfoList[i].getPrivilegiado() == Papel.SIM) {
            privileged = true;
            canLogonAtDateTime = true;
          }
          // verifica se pode efetuar logon nesta data e hora...
          else if (!canLogonAtDateTime) {
            canLogonAtDateTime = Papel.canLogonAtDateTime(papelInfoList[i].getTabelaHorario(), now);
          } // if
        } // for
        // se o usu�rio n�o � privilegiado...vasculha seus direitos
        if (!privileged) {
          // se n�o pode efetuar logon nesta data e hora...exce��o
          if (!canLogonAtDateTime)
            throw new Exception("Acesso negado a partir deste computador, dia da semana ou hor�rio do dia.");
          // loop nos pap�is
          for (int i=0; i<papelInfoList.length; i++) {
            // Papel da vez
            PapelInfo papelInfo = papelInfoList[i];
            // A��es associadas ao Papel
            PapelAcaoInfo[] papelAcaoInfoList = papelAcao.selectByPapelId(papelInfo.getPapelId());
            // loop nas A��es
            for (int w=0; w<papelAcaoInfoList.length; w++) {
              // PapelA��o da vez
              PapelAcaoInfo papelAcaoInfo = papelAcaoInfoList[w];
              // verifica se j� temos o ActionInfo
              ActionInfo actionInfo = actionInfoList.get(papelAcaoInfo.getAcao());
              // se ainda n�o temos...cria e adiciona a lista
              if (actionInfo == null)
                actionInfoList.add(new ActionInfo(papelAcaoInfo.getAcao(),
                                                  papelAcaoInfo.getComandosAsArray()));
              // se j� temos...apenas adiciona os comandos
              else
                actionInfo.addCommands(papelAcaoInfo.getComandosAsArray());
            } // for w
            // Rela��es Mestre associadas ao Papel
            PapelRelacaoMestreInfo[] papelRelacaoMestreInfoList = papelRelacaoMestre.selectByPapelId(papelInfo.getPapelId());
            // loop nas Rela��es Mestre
            for (int w=0; w<papelRelacaoMestreInfoList.length; w++) {
              // Papel Rela��o Mestre da vez
              PapelRelacaoMestreInfo papelRelacaoMestreInfo = papelRelacaoMestreInfoList[w];
              // verifica se j� temos o MasterRelationInfo
              MasterRelationInfo masterRelationInfo = masterRelationInfoList.get(papelRelacaoMestreInfo.getRelacaoMestre());
              // se ainda n�o temos...cria e adciona a lista
              if (masterRelationInfo == null) {
                masterRelationInfo = new MasterRelationInfo(papelRelacaoMestreInfo.getRelacaoMestre(),
                                                            papelRelacaoMestreInfo.getPrivilegiado() == PapelRelacaoMestre.SIM);
                masterRelationInfoList.add(masterRelationInfo);
              }
              // se j� temos...verifica se � privilegiado
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
        // mostra exce��o
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
   * Verifica a exist�ncia e cria o Papel e Usu�rio informados.
   * @param roleName String Nome do Papel.
   * @param roleDescription String Descri��o do Papel.
   * @param privileged boolean Privilegiado.
   * @param roleTimeTable String Tabela de Hor�rio.
   * @param userName String Nome do Usu�rio.
   * @param userDescription String Descri��o do Usu�rio.
   * @param userEmail String Email do Usu�rio.
   * @param userPassword String Senha do Usu�rio.
   * @param cannotChangePassword boolean N�o Pode Alterar Senha.
   * @throws Exception Em caso de exce��o na tentativa de localiza��o e inclus�o
   *                   do Papel e Usu�rio informados.
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
    // suspende a transa��o
    getFacade().suspendTransaction();
    try {
      // usu�rio a ser verificado
      UsuarioInfo usuarioInfo = null;
      try {
        // tenta localizar o usu�rio informado
        usuarioInfo = usuario.selectByNomeOrEmail(userName, userEmail);
      }
      catch (RecordNotFoundException e) {
        // n�o faz nada
      } // try-catch
      // se o usu�rio n�o foi encontrado...insere
      if (usuarioInfo == null) {
        // novo Usu�rio
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
        // n�o faz nada
      } // try-catch
      // se o papel n�o foi encontrado...insere
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

      // papel usu�rio a ser verificado
      PapelUsuarioInfo papelUsuarioInfo = null;
      try {
        // tenta localizar o papel usu�rio informado
        papelUsuarioInfo = papelUsuario.selectByPapelIdUsuarioId(papelInfo.getPapelId(), usuarioInfo.getUsuarioId());
      }
      catch (RecordNotFoundException e) {
        // n�o faz nada
      } // try-catch
      // se o papel usu�rio n�o foi encontrado...insere
      if (papelUsuarioInfo == null) {
        // novo papel usu�rio
        papelUsuarioInfo = new PapelUsuarioInfo(papelInfo.getPapelId(),
                                                usuarioInfo.getUsuarioId(),
                                                0);
        // insere
        papelUsuario.insert(papelUsuarioInfo);
      } // if
    }
    finally {
      // libera a transa��o
      getFacade().releaseTransaction();
    } // try-catch
  }

}
