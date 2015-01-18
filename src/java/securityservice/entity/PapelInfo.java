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
