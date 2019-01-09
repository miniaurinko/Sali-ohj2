package salirakenteet;
import java.io.*;
import java.util.Random;

/**
 * 
 * @version 20.4.2017
 *
 */
public class Jasen implements Cloneable{

    private int tunnusNro;
    private String nimi = "";
    private static int seuraavaNro = 1;
    
    /**
     * Kysytään jäsenen nimi
     * @return jäsenen nimi
     * @example
     * <pre name="test">
     *   Jasen make = new Jasen();
     *   make.annatiedot();
     *   make.getNimi() =R= "Make Muskeli .*";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }
    
    /**
     * Asettaa jäsenelle nimen
     * @param nimi nimi
     * @return string
     */
    public String setNimi(String nimi) {
        this.nimi = nimi;
        return null;
    }
    
  
    
    /**
     * Oletusmuodostaja
     */
    public Jasen(){
        
    }
    
    /**
     * toinen muodostaja
     * @param nimi jasenen nimi
     * @param maara jasenet
     */
    public Jasen(String nimi, int maara){
        seuraavaNro = maara+1;
        this.nimi = nimi;
    }
    
    /**
     * Jäsenen kloonaaminen
     */
    @Override
    public Jasen clone() throws CloneNotSupportedException {
        Jasen uusi;
        uusi = (Jasen) super.clone();
        return uusi;
    }
    
    /**
     * Antaa jäsenen tiedot
     */
    public void annaTiedot() {
        
        Random rand = new Random();
        nimi = "Make Muskeli " + rand.nextInt(9999);
        
        
    }
    
    /**
     * tulostus
     * @param out tulosta
     */
    public void tulosta(PrintStream out) {
        out.println(nimi);
    }
    
    /**
     * tulostus
     * @param o o
     */
    public void tulosta(OutputStream o){
        tulosta(new PrintStream(o));
    }
    
    /**
     * jäsenen rekisteröinti
     * @param j aikaisempien jasenten maara
     * @return tunnusnumero
     */
    public int rekisteroi(int j) {
        int k =j +1;
        seuraavaNro = k++;
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        
        return tunnusNro;
    }
    
    /**
     * tunnusnumeron palauttaminen 
     * @return tunnusnumero
     */
    public int getTunnusNro(){
        return tunnusNro;
    }
    
    /**
     * tostring
     */
    @Override
    public String toString(){
        return "" +
                getTunnusNro() + "|" + nimi;
    }
    
    /**
     * pääohjelma
     * @param args args
     */
    public static void main(String[] args) {
        
    
    Jasen Sami = new Jasen(), Make = new Jasen();
 
    Sami.tulosta(System.out);
    Sami.annaTiedot();
    Sami.tulosta(System.out);
    Make.annaTiedot();
    Make.tulosta(System.out);
    }

    /**
     * @param rivi h
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        String[] asd = sb.toString().split("\\|");
        nimi = asd[1];
        tunnusNro = Integer.parseInt(asd[0]);
    }
    
    @Override
         public boolean equals(Object jasen) {
             if ( jasen == null ) return false;
             return this.toString().equals(jasen.toString());
         }
     
     
         @Override
         public int hashCode() {
             return tunnusNro;
         }


}
