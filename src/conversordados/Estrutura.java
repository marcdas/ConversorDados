package conversordados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Estrutura {

	private DadosConexao dadosConexao;
        private int[] bancoPossuiSchema = {1, 2};
        
        public int[] getBancoComSchema()
        {
            return bancoPossuiSchema;
        }
        
        private String palavraReservada(String texto)
        {
            String strRetorno = texto;
            if (texto.equals("window") || texto.equals("data") || texto.equals("datatime") || texto.equals("timestamp"))
            {
                strRetorno = (char)34 + texto + (char)34;
            }
            return strRetorno;
        }
        
        protected String montaCampo(ArrayList campos, int intTipoCampoDestino, int tipoRetorno)
        {
            //1 - Para criação ex.: campo1 numeric(15,9)
            //2 - Para select e insert Ex.: campo1, campo2, campo3
            ArrayList strInformacoes = new ArrayList();
            String retorno = "";
            String strTipoCampo = "";
            for (int i = 0; i < campos.size(); i++) 
            {
                strInformacoes.add(campos.get(i).toString().split("¨"));
                String nomeCampo      = palavraReservada(campos.get(i).toString().split("¨")[0]);
                String tamanhoCampo   = campos.get(i).toString().split("¨")[1];
                String escalaCampo    = campos.get(i).toString().split("¨")[2];
                String indexTipoCampo = campos.get(i).toString().split("¨")[3];
                
                String[] tipoCampoDestino = obtemTipoCampoDestino(intTipoCampoDestino, Integer.parseInt(indexTipoCampo));    
                
                if (tipoCampoDestino[1].equals("CT"))
                {
                    strTipoCampo = tipoCampoDestino[0] + " " + (tipoRetorno == 1 ? "(" + tamanhoCampo + ")":"");
                }
                else if (tipoCampoDestino[1].equals("CE"))
                {
                    if (Integer.parseInt(tamanhoCampo) > 0)
                        strTipoCampo = tipoCampoDestino[0] + (tipoRetorno == 1 ? "(" + tamanhoCampo + ", " + escalaCampo + ")" :"");
                    else
                        strTipoCampo = tipoCampoDestino[0];    
                }
                else
                    strTipoCampo = tipoCampoDestino[0];
                
                if ((i+1) == campos.size())
                    retorno += nomeCampo + (tipoRetorno == 1 ? " " + strTipoCampo : "");
                else if (!retorno.equals(null))
                    retorno += nomeCampo + (tipoRetorno == 1 ? " " + strTipoCampo : "") + " , " ;

                 nomeCampo = "";
            }
            return retorno;
        }
        
        private String[][] obtemTipoColunas(int intTipoBanco)
        {
            String[][] strTipoColunas = new String[9][2];
            if (intTipoBanco == 0)
            { 
                //sqlConsulta = new BancoSybase().getConsultaOwner(); 
                BancoSybase banco = new BancoSybase();
                //Obtem a Lista de campos da Origem
                strTipoColunas = banco.getTiposCampo();
            }
            else if (intTipoBanco == 1)
            { 
                BancoPostgres banco = new BancoPostgres();
                //Obtem a Lista de campos da Origem
                strTipoColunas = banco.getTiposCampo();
            }
            else if (intTipoBanco == 2)
            { 
                //listaOwner.add(dadosConexao.getBancoServico());
                //con.desconectar();
                //return listaOwner;
            }	
            return strTipoColunas;
        }
	protected ArrayList listaColuna(DadosConexao dadosConexao, int tipo) 
        {
            String sqlConsulta = "";
            String retorno = "";
            String[][] strTipoColunas = new String[8][2];
	    Conexao con = new Conexao();
            Connection conexao = con.retConexao(dadosConexao);
            

            //Verifica qual banco selecionado 
            if (dadosConexao.getTipoBanco() == 0)
            { 
                //sqlConsulta = new BancoSybase().getConsultaOwner(); 
                BancoSybase banco = new BancoSybase();
                //Obtem a Consutla de Colunas do banco de Origem
                sqlConsulta    = banco.getConsultaColuna(); 
                //Obtem a Lista de campos da Origem
                strTipoColunas = banco.getTiposCampo();
            }
            else if (dadosConexao.getTipoBanco() == 1)
            { 
                BancoPostgres banco = new BancoPostgres();
                //Obtem a Consutla de Colunas do banco de Origem
                sqlConsulta    = banco.getConsultaColuna(); 
                //Obtem a Lista de campos da Origem
                strTipoColunas = banco.getTiposCampo();
            }
            else if (dadosConexao.getTipoBanco() == 2)
            { 
                //listaOwner.add(dadosConexao.getBancoServico());
                //con.desconectar();
                //return listaOwner;
            }	
            else if (dadosConexao.getTipoBanco() == 3)
            { 
                BancoFirebird banco = new BancoFirebird();
                //Obtem a Consutla de Colunas do banco de Origem
                sqlConsulta    = banco.getConsultaColuna(); 
                //Obtem a Lista de campos da Origem
                strTipoColunas = banco.getTiposCampo();
            }            
            else if (dadosConexao.getTipoBanco() == 4)
            { 
                BancoSqlServer banco = new BancoSqlServer();
                //Obtem a Consutla de Colunas do banco de Origem
                sqlConsulta    = banco.getConsultaColuna(); 
                //Obtem a Lista de campos da Origem
                strTipoColunas = banco.getTiposCampo();
            }            
            
            ResultSet rs;
          
            ArrayList colunas = new ArrayList();
            try 
            {
                //Obtem consulta para listar as colunas
                PreparedStatement ps = conexao.prepareStatement(sqlConsulta.replace("[TABELA]", dadosConexao.getTabela()));
                //if (dadosConexao.getTipoBanco() != 3)
                //{
                    //Informa os parâmetros d consulta
                    ps.setString(1, dadosConexao.getTabela());
                    ps.setString(2, dadosConexao.getSchema());
                    if (dadosConexao.getTipoBanco() == 1)
                    { 
                        ps.setString(3, dadosConexao.getBancoServico());    
                    }
                //}
                rs = ps.executeQuery();    

                String strColuna, strTipo, strTamanho, strEscala;
                String strNovoTipo[] = null;
    
                int contador = 1;
                //Obtem dados os campos do Banco Server
                ResultSetMetaData rsmd = rs.getMetaData();
                long ltotalColunas = rsmd.getColumnCount();
                while (rs.next())
                {
                //for (int i = 1; i <= ltotalColunas; i++) 
                //{
                    strColuna  = rs.getString(1);  //rs.getMetaData().getColumnName(0);
                    strTipo    = rs.getString(2);
                    strTamanho = String.valueOf(rs.getInt(3));// String.valueOf(rs.getMetaData().getPrecision(i));
                    strEscala  = String.valueOf(rs.getInt(4));  //String.valueOf(rs.getMetaData().getScale(i));
                    
                    //Obtem o index do tipo da coluna do banco Origem
                    int intIndexOrigem = obtemIndexCampo(strTipoColunas, strTipo);
                    //Ainda não tem forma de conversão para o tipo BLOB, 
                    //Nesse caso terá o tamanho máximo (255).
                    //Não esquecer de implementar esse tipo de dado
                    colunas.add(strColuna + '¨' + 
                               (strTipo.trim().equals("BLOB") ? "255" : strTamanho) + '¨' + 
                               strEscala + '¨' + 
                               String.valueOf(intIndexOrigem));
                    
                    
                }
/*                
                while (rs.next()) 
                {
                    //1 coluna - 2 tipo - 3 tamanho - 4 escala
                    strColuna  = rs.getString(1).trim();
                    strTipo    = rs.getString(2).trim();
                    strTamanho = String.valueOf(rs.getInt(3));
                    strEscala  = String.valueOf(rs.getInt(4));
                 
                    //Obtem o index do tipo da coluna do banco Origem
                    int intIndexOrigem = obtemIndexCampo(strTipoColunas, strTipo);
                    //Ainda não tem forma de conversão para o tipo BLOB, 
                    //Nesse caso terá o tamanho máximo (255).
                    //Não esquecer de implementar esse tipo de dado
                    colunas.add(strColuna + '¨' + (strTipo.trim().equals("BLOB") ? "255" : strTamanho) + '¨' + 
                                strEscala + '¨' + String.valueOf(intIndexOrigem));
                    contador++;
                }
  */              
                
                //retorno = montaCampo(colunas, strTipoColunas, dadosConexao.getTipoBancoClient(), tipo);
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(Estrutura.class.getName()).log(Level.SEVERE, null, ex);
            }

            con.desconectar();

            return colunas;
	}

        protected String[] obtemTipoCampoDestino(int tipo_banco, int index)
        {
            String[] retorno = null;
            if (tipo_banco == 0)
            { 
                //sqlConsulta = new BancoSybase().getConsultaOwner(); 
                BancoSybase banco = new BancoSybase();
                retorno = banco.getIndexTipoColuna(index);
            }
            else if (tipo_banco == 1)
            { 
                BancoPostgres banco = new BancoPostgres();
                retorno = banco.getIndexTipoColuna(index);
            }
            else if (tipo_banco == 2)
            { 
                //listaOwner.add(dadosConexao.getBancoServico());
                //con.desconectar();
                //return listaOwner;
            }
            return retorno;
        }
        
        private int obtemIndexCampo(String[][] strTipoOrigem, String tipo)
        {
            int retorno = 0;
            for (int i = 0; i < strTipoOrigem.length; i++) 
            {
                String tipoColuna = strTipoOrigem[i][0];
                if (tipoColuna.equals(tipo.trim().toLowerCase()))
                {
                    retorno = i;
                    i = strTipoOrigem.length + 1;
                }
            }
            
            return retorno;
        }
        

	public String montaCreate(DadosConexao dadosConexao, DadosConexao dadosConDestino) 
        {
            
            ArrayList listaCampo = new ArrayList();
            String colunas = "";
            String strCreate = "CREATE TABLE [schema].[tabela] ([campo])";
            String retorno = "";
            ArrayList listaOwner = new ArrayList();
            
            listaCampo = listaColuna(dadosConexao, 1);
           
            int tipoBanco = dadosConexao.getTipoBancoClient();
            colunas  = montaCampo(listaCampo, tipoBanco, 1); 
            
            retorno = strCreate.replace("[schema]", dadosConDestino.getSchema());
            retorno = retorno.replace("[tabela]", dadosConexao.getTabela());
            retorno = retorno.replace("[campo]" , colunas);
            
            return retorno;
	}

        private int verificaExisteSchema(Connection con, String nomeSchema, int intTipoBancoDestino) throws SQLException
        {
            String sqlConsulta = "";
            if (intTipoBancoDestino == 0)
            { 
                //sqlConsulta = new BancoSybase().getConsultaOwner(); 
                //BancoSybase banco = new BancoSybase();
                //retorno = banco.ge .gegetIndexTipoColuna(index);
            }
            else if (intTipoBancoDestino == 1)
            { 
                BancoPostgres banco = new BancoPostgres();
                sqlConsulta = banco.getConsOwnerNome();
            }
            else if (intTipoBancoDestino == 2)
            { 
                //listaOwner.add(dadosConexao.getBancoServico());
                //con.desconectar();
                //return listaOwner;
            }
            
                    
            int retorno = 0;       
            ResultSet rs;

            PreparedStatement ps = con.prepareStatement(sqlConsulta);
            ps.setString(1, nomeSchema);

            rs = ps.executeQuery(); 
            
            if (rs.next())
            {
                if (rs.getInt(1) > 0)
                {
                    retorno = 1;
                }
            }
            
            
            return retorno;
        }
        
        public String criaObjeto(DadosConexao dadosConOrigem, DadosConexao dadosConDestino)
        {
            String retorno = "";
            Conexao con = new Conexao();
                       
            ResultSet rs;
            Statement stmt;
            String sqlCreate = montaCreate(dadosConOrigem, dadosConDestino);
            try 
            {
               
                Connection conexao = con.retConexao(dadosConDestino);
                stmt = conexao.createStatement();
                //Cria Schema ou usuario no banco de destino
                if ((dadosConDestino.getTipoBanco() == 1) || (dadosConDestino.getTipoBanco() == 2))
                {
                    //Verifica se existe o schema. Se não existir cria
                    if (verificaExisteSchema(conexao, dadosConDestino.getSchema(), dadosConDestino.getTipoBanco()) == 0)
                        stmt.executeUpdate("create schema " + dadosConDestino.getSchema());
                }
                else
                {
                    //Verifica se existe o usuario. Se não existir cria
                    if (verificaExisteSchema(conexao, dadosConDestino.getSchema(), dadosConDestino.getTipoBanco()) == 0)
                        stmt.executeUpdate("create user " + dadosConDestino.getSchema());
                }   
    
                
                stmt.executeUpdate("drop table if exists " + dadosConDestino.getSchema() + "." + dadosConOrigem.getTabela());
                stmt.executeUpdate(sqlCreate);
            } 
            catch (SQLException ex) 
            {
                retorno = sqlCreate + "\n Erro:" + ex.getMessage();
            }

            con.desconectar();
            
            return retorno;
            
        }

	public ArrayList listaUsrSchema(DadosConexao dadosConexao) throws SQLException
        {
            ArrayList listaOwner = new ArrayList();
            
            Conexao con = new Conexao();
            Connection conexao = con.retConexao(dadosConexao);
            String sqlConsulta = "";
            if (dadosConexao.getTipoBanco() == 0)
            { sqlConsulta = new BancoSybase().getConsultaOwner();   }
            else if (dadosConexao.getTipoBanco() == 1)
            { sqlConsulta = new BancoPostgres().getConsultaOwner(); }
            else if (dadosConexao.getTipoBanco() == 2)
            { 
                listaOwner.add(dadosConexao.getBancoServico());
                con.desconectar();
                return listaOwner;
            }
            else if (dadosConexao.getTipoBanco() == 3)
            { sqlConsulta = new BancoFirebird().getConsultaOwner(); }
            else if (dadosConexao.getTipoBanco() == 4)
            { sqlConsulta = new BancoSqlServer().getConsultaOwner(); }
            
            ResultSet rs;
            //Statement stmt = conexao.createStatement();
            //rs = stmt.executeQuery(sqlConsulta);
            PreparedStatement ps = conexao.prepareStatement(sqlConsulta);
            if (dadosConexao.getTipoBanco() == 1)
                ps.setString(1, dadosConexao.getBancoServico());

            rs = ps.executeQuery();                
            
            String texto; //retirar depois
            
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) 
            {
                 texto = rs.getString(1); //esquema
                 listaOwner.add(texto);
            }
            con.desconectar();
            
            return listaOwner;
	}

	public ArrayList listaObjetoBanco(DadosConexao dadosConexao) 
        {
            Conexao con = new Conexao();
            ArrayList listaTabela = new ArrayList();
            try 
            {
                Connection conexao = con.retConexao(dadosConexao);
                String sqlConsulta = "";
                if (dadosConexao.getTipoBanco() == 0) //Sybase
                { sqlConsulta = new BancoSybase().getConsultaTabela();   }
                else if (dadosConexao.getTipoBanco() == 1) //Postgres
                { sqlConsulta = new BancoPostgres().getConsultaTabela(); }
                else if (dadosConexao.getTipoBanco() == 2) //Mysql
                {  }
                else if (dadosConexao.getTipoBanco() == 3) //FireBird
                { sqlConsulta = new BancoFirebird().getConsultaTabela(); }
                else if (dadosConexao.getTipoBanco() == 4) //SqlServer
                { 
                    sqlConsulta = new BancoSqlServer().getConsultaTabela(); 
                    sqlConsulta = sqlConsulta.replace("[servico]", dadosConexao.getBancoServico());
                }

                ResultSet rs;
                Statement stmt = conexao.createStatement();
                //rs = stmt.executeQuery(sqlConsulta);
                PreparedStatement ps = conexao.prepareStatement(sqlConsulta);
                ps.setString(1, dadosConexao.getSchema().trim());
                if (dadosConexao.getTipoBanco() == 1) 
                    ps.setString(2, dadosConexao.getBancoServico());

                rs = ps.executeQuery();            


                String texto; //retirar depois
                
                 ResultSetMetaData rsmd = rs.getMetaData();
                while (rs.next()) 
                {
                    //1 tabela - 2 coluna
                     texto = rs.getString(1) + '¨' + String.valueOf(rs.getLong(2));
                     listaTabela.add(texto);
                }
                con.desconectar();
                //conexao.close();
            } 
            catch (SQLException e) 
            {
                JOptionPane.showMessageDialog(null, e.getMessage(),"Atenção",JOptionPane.ERROR_MESSAGE); 
                con.desconectar();
            }
                        
            return listaTabela;
	}
        
}
