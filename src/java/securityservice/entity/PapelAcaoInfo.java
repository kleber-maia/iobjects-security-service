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
