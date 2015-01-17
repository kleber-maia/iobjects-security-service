
package securityservice.entity;

import java.sql.*;

import iobjects.entity.*;

/**
 * Representa as informações contidas pela entidade Papel Ação.
 */
public class PapelAcaoInfo extends EntityInfo {

  private int papelId = 0;
  private String acao = "";
  private String comandos = "";
  private int hash = 0;

  /**
   * Construtor padrão.
   */
   public PapelAcaoInfo() {
   }

  /**
   * Construtor estendido.
   * @param papelId Papel Id.
   * @param acao Ação.
   * @param comandos Comandos.
   * @param hash Hash.
   */
  public PapelAcaoInfo(
           int papelId,
           String acao,
           String comandos,
           int hash
         ) {
    // guarda nossos dados
    this.papelId = papelId;
    this.acao = acao;
    this.comandos = comandos;
    this.hash = hash;
  }

  public int getPapelId() {
    return papelId;
  }

  public String getAcao() {
    return acao;
  }

  public String getComandos() {
    return comandos;
  }

  public String[] getComandosAsArray() {
    return comandos.split(";");
  }

  public int getHash() {
    return hash;
  }

  public void setPapelId(int papelId) {
    this.papelId = papelId;
  }

  public void setAcao(String acao) {
    this.acao = acao;
  }

  public void setComandos(String comandos) {
    this.comandos = comandos;
  }

  public void setHash(int hash) {
    this.hash = hash;
  }

}
