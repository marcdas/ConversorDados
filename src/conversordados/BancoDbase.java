package conversordados;

public class BancoDbase 
{
    //public BancoSybase() { }
    
    private final String strConexaoDbf     = "jdbc:dbf:[servidor]";
                                              
    private final String strDriverJdbcSyb  = "com.hxtt.sql.dbf.DBFDriver";
    //private final String strConsultaOwner  = "select user_name as esquema from sysuserperm";
    private final String strConsultaOwner  = "select distinct RDB$USER as esquema from RDB$USER_PRIVILEGES";
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
    private final String strConsultaTabela = "select DISTINCT F.rdb$relation_name as tabela, 0 as quantidade                                            " +
                                            //"        (select TOTAL_REGISTROS from CONTAR_REGISTROS(F.rdb$relation_name)) AS quantidade " +
                                            "  from RDB$RELATION_FIELDS as F " +
                                             "INNER JOIN RDB$RELATIONS AS R ON(R.RDB$RELATION_NAME = F.RDB$RELATION_NAME) " +
                                            "  where F.RDB$SYSTEM_FLAG = 0           " +
                                            "   and R.RDB$OWNER_NAME = ? " +
                                            //"   and trim(F.rdb$relation_name) = 'GRH_MOVIMENTOS' " +
                                            " order by 1 ";

    
    private final String strConsultaColuna = "select A.RDB$FIELD_NAME as coluna, " +
                                             "       C.RDB$TYPE_NAME as tipo,   " +
                                             "       B.RDB$FIELD_LENGTH as tamanho,          " +
                                             "       B.RDB$FIELD_SCALE as escala            " +
                                             "  FROM " +
                                             "       RDB$RELATION_FIELDS A, " +
                                             "       RDB$FIELDS B, " +
                                             "       RDB$TYPES C, " +
                                             "       RDB$RELATIONS R " +
                                             " WHERE (B.RDB$FIELD_NAME = A.RDB$FIELD_SOURCE) " +
                                             "   AND (C.RDB$TYPE = B.RDB$FIELD_TYPE) " +
                                             "   AND (R.RDB$RELATION_NAME = A.RDB$RELATION_NAME) " +
                                             "   AND (TRIM(C.RDB$FIELD_NAME) = 'RDB$FIELD_TYPE') " +
                                             "   AND A.RDB$RELATION_NAME  = ? " +
                                             "   and R.RDB$OWNER_NAME  = ? " +
                                             " order by RDB$FIELD_POSITION  ";    

    //private final String strConsultaColuna = "select FIRST 0 * FROM [TABELA] ";    
    //private final String strConsultaColuna = "select * FROM [TABELA] ";    
   
    
    

    private final String[][] tiposCampoFb = {{"integer"         , "ST", ""}, 
                                             {"double"          , "CE", ""}, 
                                             {""                , ""  , ""},
                                             {""                , ""  , ""}, 
                                             {""                , ""  , ""}, 
                                             {"date"            , "ST", "texto"}, 
                                             {"time"            , "ST", "texto"}, 
                                             {"timestamp"       , "ST", "texto"},
                                             {"double precision", "CT", ""},
                                             {""                , ""  , ""},
                                             {""                , ""  , ""},
                                             {"long"            , "CE", ""},
                                             {"blob"            , "CT", "texto"},
                                             {""                , ""  , ""},
                                             {"timestamp"       , "ST", "texto"},
                                             {"text"            , "ST", "texto"},
                                             {"char"            , "ST", "texto"},
                                             {"varchar"         , "ST", "texto"},
                                             {"longvarchar"     , "ST", "texto"},
                                             {"blob sub_type 0" , "ST", ""},
                                             {"blob sub_type 1" , "ST", ""},
                                             {"text"            , "ST", "texto"},
                                             {"short"           , "CT", "texto"}};
    public String[] getIndexTipoColuna(int index)
    {
        String[] texto = tiposCampoFb[index];
        return texto;
    }
    public  String getStrConexao() 
    {
        return strConexaoDbf;
    }

    public String getStrDriverJdbc() {
            return strDriverJdbcSyb;
    }

    public String[][] getTiposCampo() {
            return tiposCampoFb;
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
        return tiposCampoFb;
    }
}
