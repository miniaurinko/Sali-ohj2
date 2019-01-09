package salirakenteet;

import java.io.*;
import java.util.*;

/**
 * 
 * @version 20.4.2017
 *
 */
public class Maarat {
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "maarat";
    private final Collection<Merkinta> alkiot = new ArrayList<Merkinta>();
    
    /**
     * määrät -listan alustus
     */
    public Maarat() {
        
    }
    
    /**
     * uuden merkinnän lisäys
     * @param merc merkintä
     */
    public void lisaa(Merkinta merc) {
        alkiot.add(merc);
        muutettu = true;
    }
    
   
    
    /**
     * Tiedostosta luku
     * @param a merkkijono
     * @throws SailoException virhe
     */
    public void lueTiedostosta(String a) throws SailoException {
      
        setTiedostonPerusNimi(a);
        File f = new File(getTiedostonNimi());
        if(!f.exists())
            try {
                f.createNewFile();
            } catch (IOException e1) {
                
                e1.printStackTrace();
            }
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            
            
            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Merkinta merc = new Merkinta();
                merc.parse(rivi);
                lisaa(merc);
            }
            muutettu = false;

        } catch ( FileNotFoundException e ) {
            
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
            
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    /**
     * tiedosto
     * @return perusnimi
     */
    private String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat"; 
    }

    /**
     * tiedosto
     * @param nimi perusnimi
     */
    private void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
        
    }
    
    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    /**
     * perusnimi
     * @return merkkijono
     */
    private String getTiedostonPerusNimi() {
        return tiedostonPerusNimi + ".dat";
    }

    /**
     * 
     * @throws SailoException virhe
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;
        File ftied = new File(getTiedostonNimi());

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Merkinta merc : alkiot) {
                fo.println(merc.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;      
    }
    
    /**
     * palauttaa merkintöjen lukumäärän
     * @return listan koko
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    /**
     * Vipeltäjä
     * @return d
     */
    public Iterator<Merkinta> iterator() {
        return alkiot.iterator();
    }
    
    /**
     * Palauttaa merkinnän id:n perusteella
     * @param tunnus luku
     * @return merkintä
     */
    public List<Merkinta> annaMerkinnat(int tunnus) {
        List<Merkinta> loydetyt = new ArrayList<Merkinta>();
        for (Merkinta merc : alkiot)
            if (merc.getKavijaID() == tunnus) loydetyt.add(merc);
        return loydetyt;
    }
    
  
   
    /**
     * esimerkki
     * @param jnro numero
     */
    public void vastaaEsim(int jnro) {
        this.vastaaEsim(jnro);
        
    }

    /**
     * merkinnän poisto
     * @param merk merkintä
     * @return int
     */
    public boolean poista(Merkinta merk) {
        boolean ret = alkiot.remove(merk);
        if (ret) muutettu = true;
        return ret;
    }
}
