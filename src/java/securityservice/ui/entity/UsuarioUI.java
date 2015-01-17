package securityservice.ui.entity;

import iobjects.*;
import iobjects.entity.*;
import iobjects.ui.*;
import iobjects.ui.entity.*;

import securityservice.entity.*;

/**
 * Utilit�rio para cria��o de controles para a classe Usuario.
 */
public class UsuarioUI {

  /**
   * Retorna a representa��o JavaScript de um LookupSearch contendo valores da
   * entidade Usuario.
   * @param facade Fachada.
   * @param param Param cujas propriedades ir�o gerar o controle de sele��o.
   * @param width int Largura do controle de sele��o na p�gina ou 0 (zero) para
   *              que ele se ajuste automaticamente ao seu conteiner.
   * @param style String Estilo de formata��o do ExternalLookup.
   * @param onChangeScript C�digo JavaScript para ser executado quando o usu�rio
   *                       alterar o valor do elemento HTML.
   * @param readOnly True para apenas leitura.
   * @return Retorna a representa��o JavaScript de um LookupSearch contendo valores da
   * entidade Usuario.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de dados.
   */
  static public String lookupSearchForParam(Facade  facade,
                                            Param   param,
                                            int     width,
                                            String  style,
                                            String  onChangeScript,
                                            boolean readOnly) throws Exception {
    // retorna
    return LookupSearch.script(facade,
                               param.getName(),
                               Usuario.CLASS_NAME,
                               new EntityField[]{Usuario.FIELD_NOME},
                               Usuario.FIELD_NOME,
                               "",
                               "(" + Usuario.FIELD_INATIVO.getFieldName() + " = " + Usuario.NAO + ")",
                               Usuario.FIELD_USUARIO_ID,
                               param.getValue(),
                               param.getScriptConstraint(),
                               param.getScriptConstraintErrorMessage(),
                               width,
                               style,
                               onChangeScript,
                               readOnly);
  }

  /**
   * Retorna a representa��o JavaScript de um LookupSearch contendo valores da
   * entidade Usuario.
   * @param facade Fachada.
   * @param entityLookup EntityLookup cujas propriedades ir�o gerar o controle de sele��o.
   * @param entityInfo EntityInfo contendo os valores do registro que est� sendo
   *                   editado/inserido.
   * @param width int Largura do controle de sele��o na p�gina ou 0 (zero) para
   *              que ele se ajuste automaticamente ao seu conteiner.
   * @param style String Estilo de formata��o do ExternalLookup.
   * @param onChangeScript C�digo JavaScript para ser executado quando o usu�rio
   *                       alterar o valor do elemento HTML.
   * @param readOnly True para apenas leitura.
   * @return Retorna a representa��o JavaScript de um LookupSearch contendo valores da
   * entidade Usuario.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de dados.
   */
  static public String lookupSearchForLookup(Facade       facade,
                                             EntityLookup entityLookup,
                                             EntityInfo   entityInfo,
                                             int          width,
                                             String       style,
                                             String       onChangeScript,
                                             boolean      readOnly) throws Exception {
    // obt�m Usuario ID
    Integer usuarioId = (Integer)entityInfo.getPropertyValue(entityLookup.getKeyFields()[0].getFieldAlias());
    // retorna
    return LookupSearch.script(facade,
                               entityLookup.getKeyFields()[0].getFieldAlias(),
                               Usuario.CLASS_NAME,
                               new EntityField[]{Usuario.FIELD_NOME},
                               Usuario.FIELD_NOME,
                               "",
                               "(" + Usuario.FIELD_INATIVO.getFieldName() + " = " + Usuario.NAO + ")",
                               Usuario.FIELD_USUARIO_ID,
                               (usuarioId > 0 ? usuarioId + "" : ""),
                               entityLookup.getKeyFields()[0].getScriptConstraint(),
                               entityLookup.getKeyFields()[0].getScriptConstraintErrorMessage(),
                               width,
                               style,
                               onChangeScript,
                               readOnly);
  }

  private UsuarioUI() {
  }

}
