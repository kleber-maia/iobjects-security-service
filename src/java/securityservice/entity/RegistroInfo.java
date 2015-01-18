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
