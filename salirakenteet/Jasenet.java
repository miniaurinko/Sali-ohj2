package salirakenteet;

import java.io.*;
import java.util.*;

/**
 * 
 * @version 20.4.2017
 *
 */
public class Jasenet implements Iterable<Jasen> {
    private static final int MAX_JASENIA = 5;
    private int lkm = 0;
    private Jasen alkiot[] = new Jasen[MAX_JASENIA];
    private String tiedostonPerusNimi = "nimet";
    private String kokoNimi = "";
    /**
     * Jäsenet -olion alustus
     */
    public Jasenet() {
        
    }
    
    /**
     * jäsenen lisäys taulukkoon
     * @param jasen olio
     * @throws SailoException virhe
     */
    public void lisaa(Jasen jasen) throws SailoException {
        if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm+5);
        alkiot[lkm] = jasen;
        lkm++;
    }
    
    /**
     * jäsenen palautus
     * @param i indeksi
     * @return Jäsen
     * @throws IndexOutOfBoundsException error
     */
    public Jasen anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Tosi laiton indeksi: " + i);
        return alkiot[i];
    }
    
    /**
     * palauttaa jäsenen nimellä
     * @param nimi nimi
     * @return int
     */
    public int anna(String nimi) {
        
        for(int i =0; i < alkiot.length; i++){
            if(alkiot[i].getNimi() == nimi) return alkiot[i].getTunnusNro();
        }
        return 0;
    }
    
    /**
     * antaa jasenen nimen perusteella
     * @param nimi nimi
     * @return jasen
     */
    public Jasen annaJasen(String nimi){
        for (int i = 0; i<alkiot.length; i++){
            if (alkiot[i].getNimi().equals(nimi)) return alkiot[i];
        }
        return null;
}
    /**
     * antaa listan kaikista jasenista
     * @return lista
     */
    public List<String> annaKaikkiJasenet(){
        List<String> lista = new ArrayList<String>();
        for (int i = 0; i<lkm; i++){
            if(alkiot[i] != null)lista.add(alkiot[i].getNimi());
            
        }
        return lista;
    }
    /**
     * 
     * @return tiedosto
     */
    public String getTiedostonPerusNimi() { 
        return tiedostonPerusNimi; 
    }
    
    /**
     * tiedosto
     * @param nimi nimi
     */
    public void setTiedostonPerusNimi(String nimi) { 
        tiedostonPerusNimi = nimi; 
    }
    
    /**
     * Tiedostosta luku
     * @param tied abc
     * @throws SailoException terror
     */
   
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        File f = new File(getTiedostonNimi());
        if(!f.exists())
            try {
                f.createNewFile();
            } catch (IOException e1) {
                
                e1.printStackTrace();
            }
        try (BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi()))) {
           
            String rivi;
            while ((rivi = fi.readLine()) != null) {
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';')
                    continue;
                Jasen jasen = new Jasen();
                jasen.parse(rivi); 
                lisaa(jasen);
            }
        } catch (FileNotFoundException e) {
            throw new SailoException(
                    "Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch (IOException e) {
            throw new SailoException(
                    "Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }     
        

 
    /**
     * Kutsuu lueTiedostosta
     * @throws SailoException virhe
     */
    public void lueTiedostosta() throws SailoException { 
        lueTiedostosta(getTiedostonPerusNimi()); 
    } 
    
    /**
     * tallentaminen
     * @throws SailoException virhe
     */
    public void tallenna() throws SailoException {
        

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); 
        ftied.renameTo(fbak); 

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Jasen jasen : this) {
                fo.println(jasen.toString());
            }
          
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
    }
    /**
     * ei käytössä
     * @return jotain
     */
    public String getBakNimi(){
        return tiedostonPerusNimi + ".bak";
    }
    /**
     * tiedoston nimi 
     * @return merkkijono
     */
    public String getTiedostonNimi(){
        return tiedostonPerusNimi + ".dat";
    }
    /**
     * tiedoston kokonimi
     * @return kokonimi
     */
    public String getKokoNimi(){
        return kokoNimi;
    }
    
    /**
     * metodi
     * @return palauttaa jäsenten lukumäärän
     */
    public int getLkm(){
        return lkm;        
    }
     /**
      * pääohjelma
      * @param args aargh
      */
    public static void main(String[] args) {
        Jasenet jasenet = new Jasenet();
        Jasen Make = new Jasen(), Sami = new Jasen();
        
        Make.annaTiedot();
        
        Sami.annaTiedot();
          
        
        try{
            jasenet.lisaa(Make);
            jasenet.lisaa(Sami);
            
            for (int i = 0; i < jasenet.getLkm(); i++){
                Jasen jasen = jasenet.anna(i);
                System.out.println("Jäsenen numero; " + i);
                jasen.tulosta(System.out);
            }
            
        } catch (SailoException g){
            System.out.println(g.getMessage());
        }
        
        try {
            jasenet.lueTiedostosta();
        } catch (SailoException e) {
            
            e.printStackTrace();
        }
        
    }

    /**
     * iteraattori
     *
     */
    public class JasenetIterator implements Iterator<Jasen> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraavaa jäsentä
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä jäseniä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava jäsen
         * @return seuraava jäsen
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Jasen next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }


    /**
     * Palautetaan iteraattori jäsenistään.
     * @return jäsen iteraattori
     */
    @Override
    public Iterator<Jasen> iterator() {
        return new JasenetIterator();
    }
    
    /**
     * @param hakuehto string
     * @param k int
     * @return collection
     */
    @SuppressWarnings("unused")
    public Collection<Jasen> etsi(String hakuehto, int k) { 
        Collection<Jasen> loytyneet = new ArrayList<Jasen>(); 
        for (Jasen jasen : this) { 
            loytyneet.add(jasen);  
        } 
        return loytyneet; 
    }

    /**
     * @param id mones
     * @return int
     */
    public int poista(int id) { 
        int ind = etsiId(id); 
        if (ind < 0) return 0; 
        lkm--; 
        for (int i = ind; i < lkm; i++) 
            alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null; 
        return 1; 
    } 

    /**
     * @param id etsittava
     * @return mones
     */
    public int etsiId(int id) { 
        for (int i = 0; i < lkm; i++) 
            if (id == alkiot[i].getTunnusNro()) return i; 
        return -1; 
    }
    /**
     * antaa listan jasenista
     * @return lista
     */
    public List<Jasen> annaKaikkiJasenetList() {
        List<Jasen> lista = new ArrayList<Jasen>();
        for (int i = 0; i<lkm; i++){
            lista.add(alkiot[i]);
            
        }
        return lista;
        
    } 
    
    
}