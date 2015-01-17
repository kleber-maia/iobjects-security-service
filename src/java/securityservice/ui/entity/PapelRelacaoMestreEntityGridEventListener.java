package securityservice.ui.entity;

import java.sql.*;

import iobjects.*;
import iobjects.entity.*;
import iobjects.ui.entity.*;
import iobjects.util.*;

import securityservice.entity.*;

/**
 * Utilitário para maniputalar eventos do Grid de PapelRelacaoMestre.
 */
public class PapelRelacaoMestreEntityGridEventListener implements EntityGrid.EventListener {

  private Facade             facade             = null;
  private PapelRelacaoMestre papelRelacaoMestre = null;

  /**
   * Construtor padrão.
   * @param facade Facade Fachada.
   */
  public PapelRelacaoMestreEntityGridEventListener(Facade facade) {
    try {
      // guarda nossos valores
      this.facade = facade;
      // instância de PapelRelacaoMestre
      papelRelacaoMestre = (PapelRelacaoMestre) facade.getEntity(
          PapelRelacaoMestre.CLASS_NAME);
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    } // try-catch
  }

  /**
   * Faz um lookup para a tabela de relação mestre e retorna o valor do primeiro
   * campo de exibição referente a 'masterRelationValue'.
   * @param masterRelationValue String Valor da relação mestre para se fazer o
   *                            lookup.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   * @return String Faz um lookup para a tabela de relação mestre e retorna o
   *         valor do primeiro campo de exibição referente a 'masterRelationValue'.
   */
  private String lookupMasterRelation(String masterRelationValue) throws Exception {
    // valores da relação mestre para pesquisar
    String[] values = masterRelationValue.split(";");
    // expressão de filtro
    String where = "";
    // loop nos campos chave da relação mestre
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
   * célula que estiver sendo adicionada for referente a FIELD_RELACAO_MESTRE
   * será feito um lookup para a tabela da relação mestre e retornado o
   * primeiro campo de exibição configuado.
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
      // se é o campo Relação Mestre...
      if ((entityField != null) && (entityField == PapelRelacaoMestre.FIELD_RELACAO_MESTRE)) {
        // pega o valor da relação mestre
        String masterRelationValue = entityField.getFormatedFieldValue(entityInfo);
        // retorna o valor apropriado para ser exibido
        return "<img src='" + papelRelacaoMestre.extension().imageList().getImageUrl("relacaomestre16x16.gif") + "' align='absmiddle' />&nbsp;"
             + lookupMasterRelation(masterRelationValue);
      }
      // se é outro campo...
      else
        return value;
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    } // try-catch
  }

}
