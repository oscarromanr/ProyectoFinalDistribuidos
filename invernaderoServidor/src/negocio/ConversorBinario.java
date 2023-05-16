/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

/**
 *
 * @author Fernando
 */
public class ConversorBinario {
    
    private static volatile ConversorBinario instance;
    
    
    public static ConversorBinario getInstance() 
    {
        ConversorBinario result = instance;
        if (result != null) {
            return result;
        }
        synchronized(ConversorBinario.class) 
        {
            if(instance == null) 
            {
                instance = new ConversorBinario();
            }
        return instance;
        }
    }
    
    public String revertirFormatoBinario(String binario) {
    StringBuilder sb = new StringBuilder();
    String[] binarioSeparado = binario.split(" ");
    for (String bin : binarioSeparado) {
        int decimal = Integer.parseInt(bin, 2);
        sb.append((char) decimal);
    }
    return sb.toString();
}
    
}
