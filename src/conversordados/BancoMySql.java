package conversordados;
//
public class BancoMySql {

	private final String strConexaoMySql = "jdbc:mysql://[servidor]:[porta]/information_schema?zeroDateTimeBehavior=convertToNull";
	private final String strDriverJdbcMySql = "com.mysql.jdbc.Driver";
        private final String strConsultaOwner = "select schema_name as esquema from SCHEMATA order by 1";
        private final String strConsOwnerNome = "select count(*) from information_schema.schemata where schema_name = ?";
        private final String strConsultaTabela = "select table_name as tabela,                 " +
                                                 "       table_rows as quantidade " +
                                                 "  from information_schema.tables " +
                                                 " where table_schema  = ?         " +
                                                 "   and table_catalog = ?         " +
                                                 "   and table_type = 'BASE TABLE' " +
                                                 " order by 1";
                
                private final String strConsultaColuna = "select column_name as coluna, " +
                                                 "       data_type as tipo, " +
                                                 "       (case when character_octet_length is null then numeric_precision else character_octet_length end) as tamanho, " +
                                                 "       numeric_scale as escala " +
                                                 "  from information_schema.columns " +
                                                 " where table_schema  = ? " +
                                                 "   and table_name    = ? " +
                                                 " order by ordinal_position ";
                
	private String[][] tiposCampoMySql = {{"integer"  , "ST", ""},
                                              {"numeric"  , "CE", ""},
                                              {"char"     , "CT", "texto"},
                                              {"varchar"  , "CT", "texto"},
                                              {"text"     , "ST", "texto"},
                                              {"date"     , "ST", "texto"},
                                              {"time"     , "ST", "texto"},
                                              {"timestamp", "ST", "texto"},
                                              {""         , ""  , ""},
                                              {""         , ""  , ""},
                                              {""         , ""  , ""},
                                              {""         , ""  , ""},
                                              {""         , ""  , ""},
                                              {""         , ""  , ""}};
        

	public String getStrConexao() {
		return strConexaoMySql;
	}

	public String getStrDriverJdbc() {
		return strDriverJdbcMySql;
	}

	public String[][] getTiposCampo() {
		return tiposCampoMySql;
	}

         public String getConsultaOwner() {
		return strConsultaOwner;
	}
}
