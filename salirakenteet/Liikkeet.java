package salirakenteet;

import java.util.*;
import java.io.*;


/**
 * 
 * @version 20.4.2017
 *
 */
public class Liikkeet implements Iterable<Liike> {
    private String tiedostonPerusNimi = "liikkeet";
    private final Collection<Liike> alkiot = new ArrayList<Liike>();
    
    /**
     * Liikkeet -listan alustus
     */
    public Liikkeet() {
        
    }
    
    /**
     * antaa kaikki liikkeet
     * @return lista
     */
    public List<String> annaKaikkiliikkeet(){
        List<String> lista = new ArrayList<String>();
        for (Liike muuvi : alkiot){
            lista.add(muuvi.getLiike());
        }
        return lista;
            
    }
    
    /**
     * Liikkeen lisääminen listaan
     * @param muuvi liike
     */
    public void lisaa(Liike muuvi) {
        alkiot.add(muuvi);
    }
    
    /**
     * Tiedostosta luku
     * @param hakemisto merkkijono
     * @throws SailoException virhe
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        setTiedostonPerusNimi(hakemisto);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {

            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Liike liic = new Liike();
                liic.parse(rivi); 
                lisaa(liic);
            }

        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    private void setTiedostonPerusNimi(String hakemisto) {
        tiedostonPerusNimi = hakemisto;
        
    }
    
    
    /**
     * 
     * @throws SailoException virhe
     */
    public void tallenna() throws SailoException {
        
        File ftied = new File(getTiedostonNimi());

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Liike merc : alkiot) {
                fo.println(merc.getTunnusNro()+ "|" +merc.getLiike());
                
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }      
    }
 
    
    private String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }

    /**
     * Listan koon palautus
     * @return koko
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    
    
    /**
     * Vipeltäjä
     */
    @Override
    public Iterator<Liike> iterator() {
        return alkiot.iterator();
    }
    
    /**
     * metodi joka palauttaa kaikki liikkeet
     * @param tunnusnro numero
     * @return kaikki löydetyt liikkeet
     */
    public Liike annaLiike(int tunnusnro) {
        
        for (Liike muuvi : alkiot)
        if (muuvi.getTunnusNro() == tunnusnro) return muuvi;
        return null; 
     
     
        
    }
    
    /**Annetaan liike nimen perusteella
     * @param nimi liikkeen nimi
     * @return liike
     */
    public Liike annaLiike(String nimi){
        for (Liike muuvi : alkiot){
            if(muuvi.getLiike().equals(nimi)) return muuvi;
        }
        return null;
    }
   
     
    /**
     * pääohjelma
     * @param args f
     */
    public static void main(String[] args) {
        Liikkeet muuvit = new Liikkeet();
        Liike pena = new Liike();
        pena.vastaaEsim(2);
        Liike pena2 = new Liike();
        pena2.vastaaEsim(1);
        Liike pena3 = new Liike();
        pena3.vastaaEsim(3);
        Liike pena4 = new Liike();
        pena4.vastaaEsim(2);
        
        muuvit.lisaa(pena);
        muuvit.lisaa(pena2);
        muuvit.lisaa(pena3);
        muuvit.lisaa(pena4);
        
        System.out.println("============= Liikkeet testi =================");
        
     
    }
    
    /**
     * oletusliikkeet
     * @throws SailoException sailo
     */
    public void getdefaultiikkeet() throws SailoException {
        File ftied = new File(getTiedostonNimi());
        if(ftied.exists() && !ftied.isDirectory()) return;
        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            
                fo.println("1|Penkkipunnerrus");
                fo.println("2|Pystypunnerrus");
                fo.println("3|Penkkipunnerrus, vino");
                fo.println("4|Leuanveto, myötäote");
                fo.println("5|Leuanveto, vastaote");
                fo.println("6|Ojentajaliike");
                fo.println("7|Hauisliike");
                fo.println("8|Vatsalihasliike");
                fo.println("9|Etureisiliike");
                fo.println("10|Takareisiliike");
                fo.println("11|Kyykky");
                fo.println("12|Maastaveto");
                fo.println("13|Pohjeliike");
            
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        
    }
}
