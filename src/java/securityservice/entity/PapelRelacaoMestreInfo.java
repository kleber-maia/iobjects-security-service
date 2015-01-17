    
package securityservice.entity;

import java.sql.*;

import iobjects.entity.*;

/**
 * Representa as informações contidas pela entidade Papel Relação Mestre.
 */
public class PapelRelacaoMestreInfo extends EntityInfo {
  
  private int papelId = 0;  
  private String relacaoMestre = "";  
  private int privilegiado = 0;  
  private int hash = 0;  

  /**
   * Construtor padrão.
   */
   public PapelRelacaoMestreInfo() {
   }

  /**
   * Construtor estendido.
   * @param papelId Papel Id.
   * @param relacaoMestre Relação Mestre.
   * @param privilegiado Privilegiado.
   * @param hash Hash.
   */
  public PapelRelacaoMestreInfo(
           int papelId,
           String relacaoMestre,
           int privilegiado,
           int hash
         ) {
    // guarda nossos dados
    this.papelId = papelId;
    this.relacaoMestre = relacaoMestre;
    this.privilegiado = privilegiado;
    this.hash = hash;
  }
  
  public int getPapelId() {
    return papelId;
  }
  
  public String getRelacaoMestre() {
    return relacaoMestre;
  }
  
  public int getPrivilegiado() {
    return privilegiado;
  }
  
  public int getHash() {
    return hash;
  }
     
  public void setPapelId(int papelId) {
    this.papelId = papelId;
  }
  
  public void setRelacaoMestre(String relacaoMestre) {
    this.relacaoMestre = relacaoMestre;
  }
  
  public void setPrivilegiado(int privilegiado) {
    this.privilegiado = privilegiado;
  }
  
  public void setHash(int hash) {
    this.hash = hash;
  }
  
}
