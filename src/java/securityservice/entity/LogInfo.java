    
package securityservice.entity;

import java.sql.*;

import iobjects.entity.*;
import iobjects.util.*;

/**
 * Representa as informações contidas pela entidade Log.
 */
public class LogInfo extends EntityInfo {
  
  private int logId = 0;  
  private Timestamp data = DateTools.ZERO_DATE;  
  private int usuarioId = 0;  
  private int operacao = 0;  
  private String objeto = "";  
  private String descricao = "";  

  /**
   * Construtor padrão.
   */
  public LogInfo() {
  }

  /**
   * Construtor estendido.
   * @param logId int Log ID.
   * @param data Timestamp Data.
   * @param usuarioId int Usuário ID.
   * @param operacao int Operação.
   * @param objeto String Objeto.
   * @param descricao String Descrição.
   */
  public LogInfo(
           int logId,
           Timestamp data,
           int usuarioId,
           int operacao,
           String objeto,
           String descricao
         ) {
    // guarda nossos dados
    this.logId = logId;
    this.data = data;
    this.usuarioId = usuarioId;
    this.operacao = operacao;
    this.objeto = objeto;
    this.descricao = descricao;
  }
  
  public int getLogId() {
    return logId;
  }
  
  public Timestamp getData() {
    return data;
  }
  
  public int getUsuarioId() {
    return usuarioId;
  }
  
  public int getOperacao() {
    return operacao;
  }
  
  public String getObjeto() {
    return objeto;
  }
  
  public String getDescricao() {
    return descricao;
  }
     
  public void setLogId(int logId) {
    this.logId = logId;
  }
  
  public void setData(Timestamp data) {
    this.data = data;
  }
  
  public void setUsuarioId(int usuarioId) {
    this.usuarioId = usuarioId;
  }
  
  public void setOperacao(int operacao) {
    this.operacao = operacao;
  }
  
  public void setObjeto(String objeto) {
    this.objeto = objeto;
  }
  
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }
  
}
