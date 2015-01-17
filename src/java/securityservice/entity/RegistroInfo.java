    
package securityservice.entity;

import java.sql.*;

import iobjects.entity.*;

/**
 * Representa as informações contidas pela entidade Registro.
 */
public class RegistroInfo extends EntityInfo {
  
  private int registroId = 0;  
  private String chave = "";  
  private String valorString = "";  
  private int valorInteger = 0;  
  private double valorDouble = 0.0D;  

  /**
   * Construtor padrão.
   */
  public RegistroInfo() {
  }

  /**
   * Construtor estendido.
   * @param registroId int Registro ID.
   * @param chave String Chave.
   * @param valorString String Valor String.
   * @param valorInteger int Valor Integer.
   * @param valorDouble double Valor Double.
   */
  public RegistroInfo(
           int registroId,
           String chave,
           String valorString,
           int valorInteger,
           double valorDouble
         ) {
    // guarda nossos dados
    this.registroId = registroId;
    this.chave = chave;
    this.valorString = valorString;
    this.valorInteger = valorInteger;
    this.valorDouble = valorDouble;
  }
  
  public int getRegistroId() {
    return registroId;
  }
  
  public String getChave() {
    return chave;
  }
  
  public String getValorString() {
    return valorString;
  }
  
  public int getValorInteger() {
    return valorInteger;
  }
  
  public double getValorDouble() {
    return valorDouble;
  }
     
  public void setRegistroId(int registroId) {
    this.registroId = registroId;
  }
  
  public void setChave(String chave) {
    this.chave = chave;
  }
  
  public void setValorString(String valorString) {
    this.valorString = valorString;
  }
  
  public void setValorInteger(int valorInteger) {
    this.valorInteger = valorInteger;
  }
  
  public void setValorDouble(double valorDouble) {
    this.valorDouble = valorDouble;
  }
  
}
