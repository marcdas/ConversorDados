/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//
package conversordados;

import Conversor.Tela.frmPrincipal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author marcio
 */
public class ConversorDados {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
       // TODO code application logic here
        frmPrincipal janela = new frmPrincipal();
        
        String str_atual;
        String str_comparacao = "31/12/2030";
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dt_comparacao = df.parse(str_comparacao);
        Date dt_atual = Calendar.getInstance().getTime();
        
        if (dt_atual.after(dt_comparacao))
        {
            janela.dispose();
            JOptionPane.showMessageDialog(null, "Falha na execução do sistema!","Atenção",JOptionPane.ERROR_MESSAGE); 
            System.exit(0);
        }
        else
        {
            janela.show();
        }
    }
    
}
