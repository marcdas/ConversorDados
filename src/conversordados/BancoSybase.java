package conversordados;

public class BancoSybase 
{
    //public BancoSybase() { }
    
    private final String strConexaoSyb     = "jdbc:sybase:Tds:[servidor]:[porta]/[servico]";
    private final String strDriverJdbcSyb  = "com.sybase.jdbc.SybDriver";
    //private final String strConsultaOwner  = "select user_name as esquema from sysuserperm";
    private final String strConsultaOwner  = "select name as esquema from dbo.syslogins";
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
    private final String strConsultaTabela = "select table_name as tabela,                                             " +
                                            "       count as quantidade                                                " +
                                            "  from SYSTABLE as t                                                      " +
                                            " inner join dbo.syslogins as p on(p.suid = t.creator)                     " +
                                            " where (table_name not like 'SYS%'    and  table_name not like 'ul_%'     " +
                                            "   and  table_name not like 'ml_%'    and  table_name not like 'migrate%' " +
                                            "   and  table_name not like 'satmp_%' and  table_name not like 'rs_%'     " +
                                            "   and  table_name not like 'dummy%'  and  table_name not like 'dummy%' ) " +
                                            "   and name = ? " +
                                            " order by 1 ";

    
//    private final String strConsultaColuna = "select col.column_name as coluna, dom.domain_name as tipo, width as tamanho, scale as escala " +
//                                             "  from SYSCOLUMN as col " +
//                                             " inner join SYSTABLE AS tab on(col.table_id = tab.table_id) " +
//                                             " inner join SYSDOMAIN  AS dom on(dom.domain_id = col.domain_id) " +
//                                             " inner join sysuserperm as per on(per.user_id = tab.creator) " +
//                                             " where table_name = ? " +
//                                             "   and user_name = ? " +
//                                             " order by col.column_id";
    
    private final String strConsultaColuna = "select col.column_name as colunaa, dom.domain_name as tipoo, width as tamanhoo, scale as escalaa " +
                                             "  from SYSCOLUMN as col " +
                                             " inner join SYSTABLE AS tab on(col.table_id = tab.table_id) " +
                                             " inner join SYSDOMAIN  AS dom on(dom.domain_id = col.domain_id) " +
                                             " inner join dbo.syslogins as per on(per.suid = tab.creator) " +
                                             " where table_name = ? " +
                                             "   and per.name = ? " +
                                             " order by col.column_id";    
    

    private final String[][] tiposCampoSyb = {
                                             {"integer"         , "ST", ""}, 
                                             {"numeric"         , "CE", ""}, 
                                             {"char"            , "CT", "texto"},
                                             {"varchar"         , "CT", "texto"}, 
                                             {"long varchar"    , "ST", "texto"}, 
                                             {"date"            , "ST", "texto"}, 
                                             {"time"            , "ST", "texto"}, 
                                             {"timestamp"       , "ST", "texto"},
                                             {""                , "", ""},
                                             {""                , "", ""},
                                             {""                , "", ""},
                                             {"long varchar"    , "ST", "texto"},
                                             {""                , "", ""},
                                             {"decimal"         , "CE", ""},
                                             {"timestamp"       , "ST", "texto"},
                                             {"long varchar"    , "ST", "texto"},
                                             {"long varchar"    , "ST", "texto"},
                                             {"long varchar"    , "ST", "texto"},
                                             {"long varchar"    , "ST", "texto"},
                                             {"long varchar"    , "ST", "texto"},
                                             {"long varchar"    , "ST", "texto"},
                                             {"long binary"     , "ST", "texto"} };
    
    public String[] getIndexTipoColuna(int index)
    {
        String[] texto = tiposCampoSyb[index];
        return texto;
    }
    public  String getStrConexao() 
    {
        return strConexaoSyb;
    }

    public String getStrDriverJdbc() {
            return strDriverJdbcSyb;
    }

    public String[][] getTiposCampo() {
            return tiposCampoSyb;
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
        return tiposCampoSyb;
    }
}
