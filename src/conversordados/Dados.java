
//
package conversordados;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;


public class Dados extends Estrutura
{
    ArrayList lstrColunas = new ArrayList();
    private String montaSelect(DadosConexao dadosConexaoOrigem)
    {
        
        String retorno = "";
        String strCampos  = "";
        String strConsulta = "SELECT [campo] FROM [schema].[tabela] [where]";
        String strNovaConsulta = "";
        lstrColunas =  listaColuna(dadosConexaoOrigem, 2);
        strCampos = montaCampo(lstrColunas, dadosConexaoOrigem.getTipoBancoClient(), 2);
        
        if (dadosConexaoOrigem.getTipoBanco() == 3)
            strConsulta = strConsulta.replace("[schema].", "");
        
        strNovaConsulta = strConsulta.replace("[campo]", strCampos);
        strNovaConsulta = strNovaConsulta.replace("[schema]", dadosConexaoOrigem.getSchema().trim());
        strNovaConsulta = strNovaConsulta.replace("[tabela]", dadosConexaoOrigem.getTabela());
        strNovaConsulta = strNovaConsulta.replace("[where]", (!dadosConexaoOrigem.getCondicao().equals("") ? "where " + dadosConexaoOrigem.getCondicao() : ""));
        
        return strNovaConsulta;
    }
    
    private String montaInsert( String schema, String tabela, String colunas, String informacoes)
    {
        String strNovoSql = "";
        String strSql = "INSERT INTO [schema].[tabela]([colunas]) values ([valores]);";
        strNovoSql = strSql.replace("[schema]", schema);
        strNovoSql = strNovoSql.replace("[tabela]", tabela);
        strNovoSql = strNovoSql.replace("[colunas]", colunas);
        strNovoSql = strNovoSql.replace("[valores]", informacoes);
    
        return strNovoSql;
    }
    
    private String gravaArquivo(String texto, String tabela, String strCaminho)
    {  
        String retorno = "";
        String conteudo = texto;  
        try
        {  
            // o true significa q o arquivo será constante  
            FileWriter x = new FileWriter(strCaminho + tabela + ".txt",true);   
            
            conteudo += "\n"; // criando nova linha e recuo no arquivo              
            x.write(conteudo); // armazena o texto no objeto x, que aponta para o arquivo             
            x.close(); // cria o arquivo              
         //   JOptionPane.showMessageDialog(null,"Arquivo gravado com sucesso","Concluído",JOptionPane.INFORMATION_MESSAGE);  
        }  // em caso de erro apreenta mensagem abaixo  
        catch(Exception e)
        {  
            retorno = e.getMessage();
           // JOptionPane.showMessageDialog(null,e.getMessage(),"Atenção",JOptionPane.WARNING_MESSAGE); 
        }
        
        return retorno;
    }  
    //Gera o arquivo
    private FileWriter gravaArquivo(String tabela, String strCaminho)
    {
        FileWriter retorno = null;
        try
        {  
            // o true significa q o arquivo será constante  
            retorno = new FileWriter(strCaminho + tabela + ".txt",true);   
            
        }  // em caso de erro apreenta mensagem abaixo  
        catch(Exception e)
        {  
            //retorno = e.getMessage();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Atenção",JOptionPane.WARNING_MESSAGE); 
        }
        
        return retorno;
        
    }
    
    
    public String insereDados(DadosConexao dadosDestino, String comando)
    {
        String retorno = "";
        Conexao con = new Conexao();
        Connection conexao = con.retConexao(dadosDestino);
        try 
        {        
            Statement stmt;
            stmt = conexao.createStatement();
            stmt.executeUpdate(comando);
        } 
        catch (Exception e) 
        {
            retorno = e.getMessage();
        }
        con.desconectar();
        
        return retorno;
    }
    public String insereDados(Connection con, String comando)
    {
        String retorno = "";
        
        try 
        {        
            Statement stmt;
            stmt = con.createStatement();
            stmt.executeUpdate(comando);
            
            stmt.close();
            con.close();
        } 
        catch (Exception e) 
        {
            retorno = e.getMessage();
        }
        
        return retorno;
    }
    public void insereDados(Connection con, Reader arquivo, DadosConexao dadosCliente) throws SQLException, IOException
    {
        if (dadosCliente.getTipoBanco() == 1)
        {
            CopyManager copyManager = new CopyManager((BaseConnection) con);  
            copyManager.copyIn("COPY " + dadosCliente.getSchema() + "." + dadosCliente.getTabela() + " FROM STDIN", arquivo ); 
        }
        con.close();
        
    }
    
    
    public BufferedReader lerArquivo(DadosConexao dadosDestino, String strCaminho) throws SQLException
    {  
        BufferedReader arquivo = null;
        try 
        {
            //File fg = new File(strCaminho + dadosDestino.getTabela() + ".txt");  
            //fg.delete();  
            arquivo = new BufferedReader(new FileReader(strCaminho + dadosDestino.getTabela() + ".txt"));
        }
        catch (IOException e) 
        {
           
        }
       
        return arquivo;
    }  
    
    public String importaDados(DadosConexao dadosConexao, String comando) throws SQLException
    {
        String retorno = "";
        retorno = insereDados(dadosConexao, comando);
        
        
        return retorno;
    }

    public String exportaDados(DadosConexao dadosConexaoOrigem, DadosConexao dadosConexaoDestino, String strCaminho, int tipoExportacao) 
    {
        //-----
        String strColunas = "";
        String retorno = "";
        Conexao con = new Conexao();
        String     strConsulta = montaSelect(dadosConexaoOrigem);
        Connection conexao     = con.retConexao(dadosConexaoOrigem);
        String strSqlInsert  = "";
        String strSeparador  = ", ";
        String strDelimitador = "";
        String strInformacao = "";
        String strValor = "";
        FileWriter fw = null;
        ResultSet rs;
        Statement stmt;
    
        File fg = new File(strCaminho + dadosConexaoOrigem.getTabela() + ".txt");  
        fg.delete();  
        
        dadosDTO dados_dto = new dadosDTO();
   
        try
        {
            stmt = conexao.createStatement();
            rs = stmt.executeQuery(strConsulta);
            ResultSetMetaData rsmd = rs.getMetaData();
            int contar = 1;
            
            
            fw = gravaArquivo(dadosConexaoOrigem.getTabela(), strCaminho);
            
            while (rs.next())
            {
                if (contar == 399)
                    retorno = "";

                strSqlInsert  = "";
                strColunas    = "";
                strInformacao = "";
                int contar2 = 1;
                for (int i = 0; i < lstrColunas.size(); i++) 
                {
                    if (contar2 == 279)
                         retorno = "";
                    String[] cols = lstrColunas.get(i).toString().split("¨");
                    String strTipoColuna = obtemTipoCampoDestino(dadosConexaoOrigem.getTipoBancoClient(), Integer.parseInt(cols[3]))[2];
                    try 
                    {
                        strValor = rs.getObject(i+1).toString().replace("\\", " ");
                    } 
                    catch (Exception e) 
                    {
                        //strValor = (((tipoExportacao == 1) & (dadosConexaoDestino.getTipoBanco() == 1) ) ? ((char)92) + "N" : "NULL").replace("\\", " ");
                        strValor = (((tipoExportacao == 1) & (dadosConexaoDestino.getTipoBanco() == 1) ) ? ((char)92) + "N" : "NULL");
                        strTipoColuna = "";
                    }

                    if ((i+1) == lstrColunas.size())
                        strSeparador = "";
                    else
                        strSeparador = (((tipoExportacao == 1) & (dadosConexaoDestino.getTipoBanco() == 1) ) ? "\t" : ", ");

                    if (strTipoColuna.equals("texto"))
                    {
                        //String ret = new String(strValor.getBytes("ISO-8859-1"), "UTF-8");
                            strValor = dados_dto.retiraCaracter(strValor).trim().replace("\\", " ");
                            if ((tipoExportacao == 1) & (dadosConexaoDestino.getTipoBanco() == 1))
                                strDelimitador = "";
                            else
                                strDelimitador = "'";
                            
                            strInformacao += strDelimitador + strValor + strDelimitador + strSeparador;
                    }
                    else
                        strInformacao += strValor + strSeparador;

                    strColunas += cols[0].trim() + strSeparador;
                    contar2++;
                }

                if (tipoExportacao == 0)
                {
                    strSqlInsert = montaInsert(dadosConexaoDestino.getSchema(), 
                                               dadosConexaoOrigem.getTabela(), 
                                               strColunas, 
                                               strInformacao);
                }
                else if ((tipoExportacao == 1) & (dadosConexaoDestino.getTipoBanco() == 1))
                {
                    strSqlInsert = strInformacao;
                }
                //retorno += "\n" + gravaArquivo(strSqlInsert, dadosConexaoOrigem.getTabela(), strCaminho);

                
                fw.write(strSqlInsert + "\n"); 
                
                contar++;
            } 
            
            fw.close();
        } 
        catch (SQLException | NumberFormatException | IOException e) 
        {
            retorno += "\n " + e.getMessage();
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Dados.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Dados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public int retTipoImportacao(int tipoBanco)
    {
        return new dadosDTO().verTipoImport(tipoBanco);
    }
//    public String insereDados(DadosConexao dadosConexaoOrigem, DadosConexao dadosConexaoDestino)
//    {
//        String retorno = "";
//        
//        String strConsulta = montaSelect(dadosConexaoOrigem);
//        String tt = montaInsert(strConsulta, dadosConexaoOrigem, dadosConexaoDestino);
//        return retorno;
//               
//    }
    
}
