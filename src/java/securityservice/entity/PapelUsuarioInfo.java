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
