package conversordados;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import com.hxtt.sql.dbf.DBFDriver;

public class Conexao 
{
    private String bancoSchema;
    private String strConexao;
    private String strDriveJdbc;
        
    Connection conexao;
         
    private void indicaDadosConexao(int tipoBanco) 
    {
        if (tipoBanco == 0) //Banco Sybase
        {
            BancoSybase banco = new BancoSybase();
            strConexao        = banco.getStrConexao();
            strDriveJdbc      = banco.getStrDriverJdbc();
        }
        else if (tipoBanco == 1) //Banco Postgres
        {
            BancoPostgres banco = new BancoPostgres();
            strConexao        = banco.getStrConexao();
            strDriveJdbc      = banco.getStrDriverJdbc();            
        }
        else if (tipoBanco == 2) //Banco Mysql
        {
            BancoMySql banco = new BancoMySql();
            strConexao        = banco.getStrConexao();
            strDriveJdbc      = banco.getStrDriverJdbc();            
        }
        else if (tipoBanco == 3) //Banco FIREBIRD
        {
            BancoFirebird banco = new BancoFirebird();
            strConexao        = banco.getStrConexao();
            strDriveJdbc      = banco.getStrDriverJdbc();            
        }
        else if (tipoBanco == 4) //Banco SqlServer
        {
            BancoSqlServer banco = new BancoSqlServer();
            strConexao        = banco.getStrConexao();
            strDriveJdbc      = banco.getStrDriverJdbc();            
        }
        else if (tipoBanco == 5) //Banco DBASE
        {
            BancoDbase banco = new BancoDbase();
            strConexao        = banco.getStrConexao();
            strDriveJdbc      = banco.getStrDriverJdbc();            
        }
            
    }
    
    private void conectar(int tipoBanco, String usuario, String senha,String servidor,String porta, String bancoServico) 
    {
        indicaDadosConexao(tipoBanco);
            
        try 
        {
            strConexao = strConexao.replace("[servidor]", servidor);
            strConexao = strConexao.replace("[porta]", porta);
            strConexao = strConexao.replace("[servico]", bancoServico);
               
            if (conexao == null || conexao.isClosed())
            {
                Class.forName(this.strDriveJdbc);
                conexao = DriverManager.getConnection(strConexao, usuario, senha);    
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Falha na conexao: " + e.getMessage(), "Aviso", 0);
        }
        
        //desconectar();
            
    }

    public void desconectar() 
    {
        try
        {
            if (conexao != null && !conexao.isClosed())
            {
                conexao.close();
            }
       }
            catch (Exception ex) {}
    }
        
    public Connection retConexao(DadosConexao dadosConexao) 
    {
        conectar(dadosConexao.getTipoBanco(), 
                 dadosConexao.getUsuario(),
                 dadosConexao.getSenha(),
                 dadosConexao.getServidor(),
                 dadosConexao.getPorta(),
                 dadosConexao.getBancoServico());
            
        return conexao;
    }
    
    public void verConexao(DadosConexao dadosConexao) 
    {
        conectar(dadosConexao.getTipoBanco(), 
                 dadosConexao.getUsuario(),
                 dadosConexao.getSenha(),
                 dadosConexao.getServidor(),
                 dadosConexao.getPorta(),
                 dadosConexao.getBancoServico());
        JOptionPane.showMessageDialog(null, "ConexaoEstabelecida", "Aviso", 0);
    }
}





 
    

    