
package securityservice.entity;

import java.sql.*;

import iobjects.entity.*;
import iobjects.util.*;
import securityservice.misc.TipoExpiracao;

/**
 * Representa as informações contidas pela entidade Usuário.
 */
public class UsuarioInfo extends EntityInfo {

  private int usuarioId = 0;
  private String nome = "";
  private String descricao = "";
  private String email = "";
  private String senha = "";
  private String nivel = "";
  private int inativo = Usuario.NAO;
  private int alterarSenha = Usuario.SIM;
  private int naoPodeAlterarSenha = Usuario.NAO;
  private Timestamp alteracaoSenha = DateTools.ZERO_DATE;
  private int hash = 0;
  private Timestamp dataExpiracao = DateTools.ZERO_DATE;
  private int       tipoExpiracao = TipoExpiracao.BLOQUEIO;
  private Timestamp dataUltimoLogon = DateTools.ZERO_DATE;

  /**
   * Construtor padrão.
   */
   public UsuarioInfo() {
   }

  /**
   * Construtor estendido.
   * @param usuarioId Usuário Id.
   * @param nome Nome.
   * @param descricao Descrição.
   * @param email Email.
   * @param senha Senha.
   * @param nivel Nível.
   * @param inativo Inativo.
   * @param alterarSenha Alterar Senha.
   * @param naoPodeAlterarSenha Não Pode Alterar Senha.
   * @param alteracaoSenha Alteração Senha.
   * @param hash Hash.
   * @param dataExpiracao Data de Expiração do usuário.
   * @param tipoExpiracao Tipo de Expiração.
   */
  public UsuarioInfo(
           int usuarioId,
           String nome,
           String descricao,
           String email,
           String senha,
           String nivel,
           int inativo,
           int alterarSenha,
           int naoPodeAlterarSenha,
           Timestamp alteracaoSenha,
           int hash,
           Timestamp dataExpiracao,
           int       tipoExpiracao,
           Timestamp dataUltimoLogon
         ) {
    // guarda nossos dados
    this.usuarioId = usuarioId;
    this.nome = nome;
    this.descricao = descricao;
    this.email = email;
    this.senha = senha;
    this.nivel = nivel;
    this.inativo = inativo;
    this.alterarSenha = alterarSenha;
    this.naoPodeAlterarSenha = naoPodeAlterarSenha;
    this.alteracaoSenha = alteracaoSenha;
    this.hash = hash;
    this.dataExpiracao = dataExpiracao;
    this.tipoExpiracao = tipoExpiracao;
    this.dataUltimoLogon = dataUltimoLogon;
  }

  public int getUsuarioId() {
    return usuarioId;
  }

  public String getNome() {
    return nome;
  }

  public String getDescricao() {
    return descricao;
  }

  public String getEmail() {
    return email;
  }

  public String getSenha() {
    return senha;
  }

  public String getNivel() {
    return nivel;
  }

  public int getInativo() {
    return inativo;
  }

  public int getAlterarSenha() {
    return alterarSenha;
  }

  public int getNaoPodeAlterarSenha() {
    return naoPodeAlterarSenha;
  }

  public Timestamp getAlteracaoSenha() {
    return alteracaoSenha;
  }

  public int getHash() {
    return hash;
  }
  
  public Timestamp getDataExpiracao() {
    return dataExpiracao;
  }
  
  public int getTipoExpiracao() {
    return tipoExpiracao;
  }
  
  public Timestamp getDataUltimoLogon() {
    return dataUltimoLogon;
  }

  public void setUsuarioId(int usuarioId) {
    this.usuarioId = usuarioId;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public void setNivel(String nivel) {
    this.nivel = nivel;
  }

  public void setInativo(int inativo) {
    this.inativo = inativo;
  }

  public void setAlterarSenha(int alterarSenha) {
    this.alterarSenha = alterarSenha;
  }

  public void setNaoPodeAlterarSenha(int naoPodeAlterarSenha) {
    this.naoPodeAlterarSenha = naoPodeAlterarSenha;
  }

  public void setAlteracaoSenha(Timestamp alteracaoSenha) {
    this.alteracaoSenha = alteracaoSenha;
  }

  public void setHash(int hash) {
    this.hash = hash;
  }
  
  public void setDataExpiracao(Timestamp dataExpiracao) {
    this.dataExpiracao = dataExpiracao;
  }
  
  public void setTipoExpiracao(int tipoExpiracao) {
    this.tipoExpiracao = tipoExpiracao;
  }
  
  public void setDataUltimoLogon(Timestamp dataUltimoLogon) {
    this.dataUltimoLogon = dataUltimoLogon;
  }

}
