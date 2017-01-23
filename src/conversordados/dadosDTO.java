/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conversordados;
import java.io.*;
/**
 *
 * @author marcio
 */
public class dadosDTO
{
    protected String retiraCaracter(String texto)
    {
        String strCaracterInicial = "'";
        String strCaracterTrocar  = " ";
        int    intAjuste = 0;
        String nTexto ;
        
        //nTexto = texto.replaceAll("\n", " - "); 
        //nTexto = texto.replaceAll("\\r$", " - ");
        nTexto = texto.replaceAll("\\p{Cntrl}", " - ");
        
        String strRet             = "";
        int intTamanhoTexto = nTexto.length();
        
        
        
        for (int i = 0; i < intTamanhoTexto; i++) 
        {
            for (int j = 0; j < strCaracterInicial.length(); j++) 
            {
                String strCaracterTexto = nTexto.substring(i, (i + 1));
                String strCaracterIni = strCaracterInicial.substring(j, (j + 1));
                if (strCaracterTexto.equals(strCaracterIni))
                {
                    //strRet += texto.replace(strCaracterInicial.substring(i, (i + 1)), strCaracterTrocar.trim());
                    strRet += strCaracterTrocar.substring(j, (j + 1));
                    intAjuste += 1;
                }
                else
                {
                    //strRet +=  strCaracterTexto = texto.substring(i, (i + 1));
                    strRet +=  nTexto.substring(i, (i + 1));
                }
            }
            //strRet += strCaracterNovo;
        }

        return strRet;
    }
    
    protected int verTipoImport(int tipoBanco)
    {
        int retorno = 0;
        if (tipoBanco == 1)
            retorno = 1;
        
        return retorno;
    }
    
}
