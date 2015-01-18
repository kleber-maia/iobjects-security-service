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
package securityservice.ui.entity;

import java.sql.*;

import iobjects.*;
import iobjects.entity.*;
import iobjects.ui.entity.*;
import iobjects.util.*;

import securityservice.entity.*;

/**
 * Utilit�rio para maniputalar eventos do Grid de PapelRelacaoMestre.
 */
public class PapelRelacaoMestreEntityGridEventListener implements EntityGrid.EventListener {

  private Facade             facade             = null;
  private PapelRelacaoMestre papelRelacaoMestre = null;

  /**
   * Construtor padr�o.
   * @param facade Facade Fachada.
   */
  public PapelRelacaoMestreEntityGridEventListener(Facade facade) {
    try {
      // guarda nossos valores
      this.facade = facade;
      // inst�ncia de PapelRelacaoMestre
      papelRelacaoMestre = (PapelRelacaoMestre) facade.getEntity(
          PapelRelacaoMestre.CLASS_NAME);
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    } // try-catch
  }

  /**
   * Faz um lookup para a tabela de rela��o mestre e retorna o valor do primeiro
   * campo de exibi��o referente a 'masterRelationValue'.
   * @param masterRelationValue String Valor da rela��o mestre para se fazer o
   *                            lookup.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   * @return String Faz um lookup para a tabela de rela��o mestre e retorna o
   *         valor do primeiro campo de exibi��o referente a 'masterRelationValue'.
   */
  private String lookupMasterRelation(String masterRelationValue) throws Exception {
    // valores da rela��o mestre para pesquisar
    String[] values = masterRelationValue.split(";");
    // express�o de filtro
    String where = "";
    // loop nos campos chave da rela��o mestre
    for (int i=0; i<facade.masterRelationInformation().getReturnFieldNames().length; i++) {
      if (!where.equals(""))
        where += " AND ";
      where += "(" + facade.masterRelationInformation().getReturnFieldNames()[i] + "=" + values[i] + ")";
    } // for
    // prepara o Select
    PreparedStatement preparedStatement =
        SqlTools.prepareSelect(papelRelacaoMestre.getConnection(),
                               facade.masterRelationInformation().getTableName(),
                               facade.masterRelationInformation().getDisplayFieldNames(),
                               new String[0],
                               where);
    // pega o ResultSet
    preparedStatement.execute();
    ResultSet resultSet = preparedStatement.getResultSet();
    try {
      // retorna o primeiro campo
      resultSet.next();
      return resultSet.getString(1);
    }
    finally {
      // libera recursos
      if (resultSet != null) {
        resultSet.getStatement().close();
        resultSet.close();
      } // if
    }
  }

  /**
   * Responde ao evento onAddCell do EntityGrid de PapelRelacaoMestre. Se a
   * c�lula que estiver sendo adicionada for referente a FIELD_RELACAO_MESTRE
   * ser� feito um lookup para a tabela da rela��o mestre e retornado o
   * primeiro campo de exibi��o configuado.
   * @param entityInfo EntityInfo
   * @param entityField EntityField
   * @param value String
   * @return String Responde ao evento onAddCell do EntityGrid de PapelRelacaoMestre.
   */
  public String onAddCell(EntityInfo   entityInfo,
                          EntityField  entityField,
                          EntityLookup entityLookup,
                          String       value) {
    try {
      // se � o campo Rela��o Mestre...
      if ((entityField != null) && (entityField == PapelRelacaoMestre.FIELD_RELACAO_MESTRE)) {
        // pega o valor da rela��o mestre
        String masterRelationValue = entityField.getFormatedFieldValue(entityInfo);
        // retorna o valor apropriado para ser exibido
        return "<img src='" + papelRelacaoMestre.extension().imageList().getImageUrl("relacaomestre16x16.gif") + "' align='absmiddle' />&nbsp;"
             + lookupMasterRelation(masterRelationValue);
      }
      // se � outro campo...
      else
        return value;
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    } // try-catch
  }

}
