package conversordados;
public class BancoPostgres {

	private final String strConexaoPost    = "jdbc:postgresql://[servidor]:[porta]/[servico]";
	private final String strDriverJdbcPost = "org.postgresql.Driver";
        private final String strConsultaOwner = "select schema_name as esquema             " +
                                                "  from information_schema.schemata        " +
                                                " where catalog_name = ?                   " +
                                                "   and schema_name not like 'pg_%'        " +
                                                "   and schema_name <> 'information_schema'" +
                                                " order by 1";
        private final String strConsOwnerNome = "select count(*) from information_schema.schemata where schema_name = ?";
        private final String strConsultaTabela = "select table_name as tabela,                 " +
                                                 "       (select sum(reltuples)                     " +
                                                 "          from pg_class as pg      " +
                                                 "         where pg.relname = table_name) as quantidade " +
                                                 "  from information_schema.tables " +
                                                 " where table_schema  = ?         " +
                                                 "   and table_catalog = ?         " +
                                                 "   and table_type = 'BASE TABLE' " +
                                                 " order by 1";
        private final String strConsultaColuna = "select column_name as coluna, " +
                                                 "       data_type as tipo, " +
                                                 "       (case when character_maximum_length is null then numeric_precision else character_maximum_length end) as tamanho, " +
                                                 "       numeric_scale as escala    " +
                                                 "  from information_schema.columns " +
                                                 " where table_name    = ?          " +
                                                 "   and table_schema  = ?          " +
                                                 "   and table_catalog = ?          " +
                                                 " order by ordinal_position        ";
       //ST - SEM TAMANHO, CE - COM ESCALA, CT - COM TAMANHO
	private String[][] tiposCampoPost = {{"integer"                    , "ST", ""     }, 
                                             {"double precision"           , "ST", ""     }, 
                                             {"char"                       , "CT", "texto"},
                                             {"varchar"                    , "CT", "texto"}, 
                                             {"text"                       , "ST", "texto"}, 
                                             {"date"                       , "ST", "texto"}, 
                                             {"time"                       , "ST", "texto"}, 
                                             {"timestamp"                  , "ST", "texto"},
                                             {"double precision"           , "ST", ""     },
                                             {"character varying"          , "CT", "texto"},
                                             {"character"                  , "CT", "texto"},
                                             {"bigint"                     , "ST", ""     },
                                             {"text"                       , "ST", "texto"},
                                             {"numeric"                    , "CE", ""     },
                                             {"timestamp without time zone", "ST", "texto"},
                                             {"text"                       , "ST", "texto"},
                                             {"text"                       , "ST", "texto"},
                                             {"double precision"           , "ST", ""},
                                             {"integer"                    , "ST", ""},
                                             {"text"                       , "ST", "texto"},
                                             {"text"                       , "ST", "texto"},
                                             {"text"                       , "ST", "texto"},
                                             {"boolean"                    , "ST", ""},
                                             {"double precision"           , "ST", ""},
                                             {"text"                       , "ST", "texto"},
                                             {"integer"                    , "ST", ""}};

        public String[] getIndexTipoColuna(int index)
        {
            String[] texto = tiposCampoPost[index];
            return texto;
        }
	public String getStrConexao() {
		return strConexaoPost;
	}

	public String getStrDriverJdbc() {
		return strDriverJdbcPost;
	}

	public String[][] getTiposCampo() {
		return tiposCampoPost;
	}

         public String getConsultaOwner() {
		return strConsultaOwner;
	}
        
         public String getConsOwnerNome() {
		return strConsOwnerNome;
	}
         
        public String getConsultaTabela() {
		return strConsultaTabela;
	}
         
        public String getConsultaColuna() {
		return strConsultaColuna;
	}         
        
        public String[][] getTiposColunas() {
		return tiposCampoPost;
	}     
}
