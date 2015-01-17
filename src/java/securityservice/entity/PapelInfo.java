
package securityservice.entity;

import java.sql.*;

import iobjects.entity.*;

/**
 * Representa as informações contidas pela entidade Papel.
 */
public class PapelInfo extends EntityInfo {

  private int papelId = 0;
  private String nome = "";
  private String descricao = "";
  private String tabelaHorario = "";
  private int privilegiado = Papel.NAO;
  private int hash = 0;

  /**
   * Construtor padrão.
   */
   public PapelInfo() {
   }

  /**
   * Construtor estendido.
   * @param papelId Papel Id.
   * @param nome Nome.
   * @param descricao Descrição.
   * @param privilegiado Privilegiado.
   * @param tabelaHorario Tabela Horário.
   * @param hash Hash.
   */
  public PapelInfo(
           int papelId,
           String nome,
           String descricao,
           int privilegiado,
           String tabelaHorario,
           int hash
         ) {
    // guarda nossos dados
    this.papelId = papelId;
    this.nome = nome;
    this.descricao = descricao;
    this.privilegiado = privilegiado;
    this.tabelaHorario = tabelaHorario;
    this.hash = hash;
  }

  public int getPapelId() {
    return papelId;
  }

  public String getNome() {
    return nome;
  }

  public String getDescricao() {
    return descricao;
  }

  public int getPrivilegiado() {
    return privilegiado;
  }

  public String getTabelaHorario() {
    return tabelaHorario;
  }

  public int getHash() {
    return hash;
  }

  public void setPapelId(int papelId) {
    this.papelId = papelId;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public void setPrivilegiado(int privilegiado) {
    this.privilegiado = privilegiado;
  }

  public void setTabelaHorario(String tabelaHorario) {
    this.tabelaHorario = tabelaHorario;
  }

  public void setHash(int hash) {
    this.hash = hash;
  }

}
