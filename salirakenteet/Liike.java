package salirakenteet;

import java.io.*;

/**
 * 
 * @version 20.4.2017
 *
 */
public class Liike {
    private int tunnusNro;
    private String muuvi;
    
    private static int seuraavaNro = 1;
    
    /**
     * Liikkeen alustus
     */
    public Liike() {
        
    }
    
    /**
     * @param nimi liikkeen nimi
     * @param monta int
     */
    public Liike(String nimi, int monta){
        seuraavaNro = monta+1;
        muuvi = nimi;
    }
    
    /**
     * Liikkeen alustus
     * @param id1 numero
     */
    public Liike(int id1) {
       this.tunnusNro = id1;
    }
    
    /**
     * metodi joka palauttaa esimerkkidataa
     * @param nro int
     */
    public void vastaaEsim(int nro) {
        tunnusNro = nro;
        muuvi = "Penkki";
    }
    
    /**
     * metodi joka palauttaa esimerkkiliikkeen
     * @return liike
     * @example
     * <pre name="test">
     *   Liike pena = new Liike();
     *   pena.getLiike() =R= "Penkki v. .*";
     * </pre>
     */
    public String getLiike() {
     
        return muuvi;
    }
  
              
    
    
    /**
     * tulostus
     * @param out printstream
     */
    public void tulosta(PrintStream out) {
        out.println(muuvi);
    }
    
    /**
     * tulostus
     * @param os outputstream
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Liikkeen rekisteröinti
     * @return tunnusnumero
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    /**
     * metodi tunnusnumeron hakemiselle
     * @return tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * parsija
     * @param rivi rivi
     */
    
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        String[] asd = sb.toString().split("\\|");
        tunnusNro = Integer.parseInt(asd[0]);
        muuvi = asd[1];
    }
    
    /**
     * pääohjelma
     * @param args d
     */
    public static void main(String[] args) {
        Liike moi = new Liike();
        moi.vastaaEsim(5);
        moi.tulosta(System.out);
    }
}
