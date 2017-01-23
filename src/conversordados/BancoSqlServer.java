package conversordados;

public class BancoSqlServer 
{
    //public BancoSybase() { }
    
    private final String strConexaoSqlServer     = "jdbc:sqlserver://[servidor]\\SQLEXPRESS:[porta];databaseName=[servico]";
    private final String strDriverJdbcSqlServer  = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    //private final String strConsultaOwner  = "select user_name as esquema from sysuserperm";
    private final String strConsultaOwner  = "select name as esquema from sys.schemas";
//    private final String strConsultaTabela = "select table_name as tabela,                                              " +
//                                             "       count as quantidade                                                " +
//                                             "  from SYSTABLE as t                                                      " +
//                                             " inner join sysuserperm as p on(p.user_id = t.creator)                    " +
//                                             " where (table_name not like 'SYS%'    and  table_name not like 'ul_%'     " +
//                                             "   and  table_name not like 'ml_%'    and  table_name not like 'migrate%' " +
//                                             "   and  table_name not like 'satmp_%' and  table_name not like 'rs_%'     " +
//                                             "   and  table_name not like 'dummy%'  and  table_name not like 'dummy%' ) " +
//                                             "   and user_name = ?  " +
//                                             " order by 1";
    /*private final String strConsultaTabela = "select distinct t.name as tabela, 0 as quantidade " +
                                             "  from nittrans.sys.tables as t  " +
                                             " inner join sys.columns as c on(c.object_id = t.object_id) " +
                                             " inner join sys.types as s on(c.user_type_id = s.user_type_id) " +
                                             " inner join sys.schemas as h on(h.schema_id = t.schema_id) " +
                                             " where h.name = ? " +
                                             " order by 1 ";*/
    private final String strConsultaTabela = "select distinct t.name as tabela, 0 as quantidade " +
                                             "  from [servico].sys.tables as t  " +
                                             " inner join sys.columns as c on(c.object_id = t.object_id) " +
                                             " inner join sys.types as s on(c.user_type_id = s.user_type_id) " +
                                             " inner join sys.schemas as h on(h.schema_id = t.schema_id) " +
                                             " where h.name = ? " +
                                             " order by 1 ";
    
    
//    private final String strConsultaColuna = "select col.column_name as coluna, dom.domain_name as tipo, width as tamanho, scale as escala " +
//                                             "  from SYSCOLUMN as col " +
//                                             " inner join SYSTABLE AS tab on(col.table_id = tab.table_id) " +
//                                             " inner join SYSDOMAIN  AS dom on(dom.domain_id = col.domain_id) " +
//                                             " inner join sysuserperm as per on(per.user_id = tab.creator) " +
//                                             " where table_name = ? " +
//                                             "   and user_name = ? " +
//                                             " order by col.column_id";
    
    private final String strConsultaColuna = "select c.name as coluna, s.name as tipo, case when c.precision = 0 then c.max_length else c.precision end as tamanho, c.scale as escala " +
                                             "  from sys.tables as t  " +
                                             " inner join sys.columns as c on(c.object_id = t.object_id) " +
                                             " inner join sys.types as s on(c.user_type_id = s.user_type_id) " +
                                             " inner join sys.schemas as h on(h.schema_id = t.schema_id) " +
                                             " where t.name = ? " +
                                             "   and h.name = ? " +
                                             " order by c.column_id";    
    

    private final String[][] tiposCampoSqlServer = {
                                             {"int"             , "ST", ""}, 
                                             {"numeric"         , "CE", ""}, 
                                             {"char"            , "CT", "texto"},
                                             {""                , "", ""}, 
                                             {"image"           , "ST", "texto"}, 
                                             {""                , ""  , ""}, 
                                             {""                , "", ""}, 
                                             {"datetime"        , "ST", "texto"},
                                             {"float"           , "CE", ""},
                                             {""                , "", ""},
                                             {""                , "", ""},
                                             {"long varchar"    , "ST", "texto"},
                                             {""                , "", ""},
                                             {"decimal"         , "CE", ""},
                                             {"timestamp"       , "ST", "texto"},
                                             {"ntext"           , "ST", "texto"},
                                             {"nvarchar"        , "CT", "texto"},
                                             {"real"            , "CE", ""},
                                             {"smallint"        , "ST", ""},
                                             {"text"            , "ST", "texto"},
                                             {"tinyint"         , "ST", ""},
                                             {"long varchar"    , "ST", "texto"},
                                             {"bit"             , "ST"  , ""},
                                             {"money"           , "CE", ""},
                                             {"varchar"         , "CE", "texto"} };
    
    public String[] getIndexTipoColuna(int index)
    {
        String[] texto = tiposCampoSqlServer[index];
        return texto;
        
    }
    public  String getStrConexao() 
    {
        return strConexaoSqlServer;
    }

    public String getStrDriverJdbc() {
            return strDriverJdbcSqlServer;
    }

    public String[][] getTiposCampo() {
            return tiposCampoSqlServer;
    }
        
    public String getConsultaOwner() {
            return strConsultaOwner;
    }

    public String getConsultaTabela() {
           return strConsultaTabela;
    }
         
    public String getConsultaColuna() {
            return strConsultaColuna;
    }      

    public String[][] getTiposColunas() {
        return tiposCampoSqlServer;
    }
}
