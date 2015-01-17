    
package securityservice.entity;

import java.sql.*;

import iobjects.entity.*;

/**
 * Representa as informações contidas pela entidade Papel Usuário.
 */
public class PapelUsuarioInfo extends EntityInfo {
  
  private int papelId = 0;  
  private int usuarioId = 0;  
  private int hash = 0;  

  /**
   * Construtor padrão.
   */
   public PapelUsuarioInfo() {
   }

  /**
   * Construtor estendido.
   * @param papelId Papel Id.
   * @param usuarioId Usuário Id.
   * @param hash Hash.
   */
  public PapelUsuarioInfo(
           int papelId,
           int usuarioId,
           int hash
         ) {
    // guarda nossos dados
    this.papelId = papelId;
    this.usuarioId = usuarioId;
    this.hash = hash;
  }
  
  public int getPapelId() {
    return papelId;
  }
  
  public int getUsuarioId() {
    return usuarioId;
  }
  
  public int getHash() {
    return hash;
  }
     
  public void setPapelId(int papelId) {
    this.papelId = papelId;
  }
  
  public void setUsuarioId(int usuarioId) {
    this.usuarioId = usuarioId;
  }
  
  public void setHash(int hash) {
    this.hash = hash;
  }
  
}
