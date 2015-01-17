   
package securityservice.report;

import java.sql.*;
import java.util.*;

import iobjects.*;
import iobjects.help.*;
import iobjects.report.*;
import iobjects.util.*;

import securityservice.misc.*;

/**
 * Representa o relat�rio de LogSeguranca.
 */
public class LogSeguranca extends Report {

  // identifica��o da classe
  static public final String CLASS_NAME = "securityservice.report.LogSeguranca";
  // nossa ajuda extra
  static public final String HELP = "";
  // nossas a��es
  static public final Action ACTION               = new Action("logseguranca", "Log", "Emite o relat�rio do Log.", HELP, "report/logseguranca.jsp", "Security Service", "", Action.CATEGORY_REPORT, false, false);
  static public final Action ACTION_RELATORIO     = new Action("logsegurancaRelatorio", ACTION.getCaption(), ACTION.getDescription(), HELP, "report/logsegurancarelatorio.jsp", ACTION.getModule(), ACTION.getAccessPath(), Action.CATEGORY_REPORT, ACTION.getMobile(), false);
  // nossos comandos
  static public final Command COMMAND_MAKE_REPORT = ACTION.addCommand(new Command(Command.COMMAND_MAKE_REPORT, "Gerar", "Gera e exibe o relat�rio com os par�metros informados."));
  static public final Command COMMAND_PRINT       = ACTION.addCommand(new Command(Command.COMMAND_PRINT, "Imprimir", "Envia o relat�rio exibido."));
  static public final Command COMMAND_COPY        = ACTION.addCommand(new Command(Command.COMMAND_COPY, "Copiar", "Copia todo o conte�do do relat�rio exibido."));
  // nossos par�metros de usu�rio
  public final Param USER_PARAM_DATA     = new Param("userParamData", "Data", "Informe a Data.", "", 250, Param.ALIGN_LEFT, Param.FORMAT_DATE, "", "value != ''", "Obrigat�rio informar a Data.");
  public final Param USER_PARAM_USUARIO  = new Param("userParamUsuario", "Usu�rio", "Informe o Usu�rio.", "", 250, Param.ALIGN_LEFT, Param.FORMAT_INTEGER);
  public final Param USER_PARAM_OPERACAO = new Param("userParamOperacao", "Opera��o", "Selecione a Opera��o.", TipoOperacaoLog.TODOS + "", 250, Param.ALIGN_LEFT, Param.FORMAT_INTEGER);

  {
    // valores padr�o
    USER_PARAM_DATA.setValue(DateTools.formatDate(DateTools.getActualDate()));
    // lookup
    USER_PARAM_OPERACAO.setLookupList(TipoOperacaoLog.LOOKUP_LIST_FOR_PARAM);
  }

  /**
   * Construtor padr�o.
   */
  public LogSeguranca() {
    // nossas a��es
    actionList().add(ACTION);
    actionList().add(ACTION_RELATORIO);
    // nossos par�metros de usu�rio
    userParamList().add(USER_PARAM_DATA);
    userParamList().add(USER_PARAM_USUARIO);
    userParamList().add(USER_PARAM_OPERACAO);
  }

  /**
   * Retorna o ResultSet de LogSeguranca.
   * <b>Importante: a rotina que executar este m�todo deve ser respons�vel por
   * fechar o ResultSet retornardo e o seu Statement.</b>
   * @param data Data.
   * @param usuario Usu�rio ID ou 0 para todos.
   * @param operacao TipoOperacaoLog.TODOS para que seja exibido
   *                 todas as opera��es.
   * @return ResultSet Retorna o ResultSet de LogSeguranca.
   *                   <b>Importante: a rotina que executar este m�todo deve ser
   *                   respons�vel por fechar o ResultSet retornardo e o seu
   *                   Statement.</b>
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public ResultSet getResultSetLogSeguranca(
                       Timestamp data,
                       int usuario,
                       int operacao
                    ) throws Exception {
    // inicia transa��o
    getFacade().beginTransaction();
    try {
      // SQL
      String sql = "SELECT dt_operacao, securityservice_log.in_usuario_id, sm_operacao, va_objeto, va_log "
                 + "FROM securityservice_log "
                 + "INNER JOIN securityservice_usuario ON (securityservice_log.in_usuario_id = securityservice_usuario.in_usuario_id) "
                 + "WHERE dt_operacao = ? "
                 + "AND ((securityservice_log.in_usuario_id = ?)  OR (" + usuario + " = 0 )) "
                 + "AND ((sm_operacao = ?) OR (" + (operacao == TipoOperacaoLog.TODOS) + "))"
                 + "ORDER BY dt_operacao";
      // prepara a query
      PreparedStatement preparedStatement = prepare(sql);
      // preenche os par�metros
      preparedStatement.setTimestamp(1, data);
      preparedStatement.setInt(2, usuario);
      preparedStatement.setInt(3, operacao);
      // executa
      preparedStatement.execute();
      // salva tudo
      getFacade().commitTransaction();
      // retorna
      return preparedStatement.getResultSet();
    }
    catch (Exception e) {
      // desfaz tudo
      getFacade().rollbackTransaction();
      throw e;
    } // try-catch
  }

}
