package salirakenteet;

import java.util.Collection;
import java.util.List;

/**
 * 
 * @version 20.4.2017
 *
 */
public class Sali {
    private final Jasenet jasenet = new Jasenet();
    private final Liikkeet liikkeet = new Liikkeet();
    private final Maarat maarat = new Maarat();
    
    
    /**testaus
     * @param muskeli sali 
     */
    public static void testi(Sali muskeli){
        
        Jasen jeissen = new Jasen();
        Jasen make = new Jasen();
        System.out.println("Jasenia: " + muskeli.jasenet.getLkm());
        try {
            muskeli.lisaa(jeissen);
            muskeli.lisaa(make);
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
       
        jeissen.rekisteroi(muskeli.getJasenia());
        make.rekisteroi(muskeli.getJasenia());
        System.out.println("Jasenia: " +muskeli.jasenet.getLkm());
        muskeli.poista(jeissen);
        System.out.println("Jasenia: "+muskeli.jasenet.getLkm());
        Liike mave = new Liike("mave", muskeli.liikkeet.getLkm());
        muskeli.lisaa(mave);
        mave.rekisteroi();
        System.out.println("Liikkeiden maara: " +muskeli.liikkeet.getLkm());
        
        int kavija = make.getTunnusNro();
        int toistot = 3;
        int painot = 30;
        int sarjat = 4;
        int liikeid = mave.getTunnusNro();
        int kesto = 60;
        int paiva = 1;
        int kuukausi = 1;
        int vuosi = 2017;
        int paiva2 = 2;
        
        Merkinta merkinta = new Merkinta(kavija,toistot,painot,sarjat,liikeid,kesto,paiva,kuukausi,vuosi);
        Merkinta merkinta2 = new Merkinta(kavija,toistot,painot,sarjat,liikeid,kesto,paiva2,kuukausi,vuosi);
        muskeli.lisaa(merkinta);
        muskeli.lisaa(merkinta2);
        List<Merkinta> makenmerkinnat = muskeli.annaMerkinnat(make);
        System.out.println("Merkintojen maara "+muskeli.maarat.getLkm());
        System.out.println("\n"+"Maken merkinnat:");
        
        for(Merkinta merc : makenmerkinnat){
            System.out.println(merc.toString());
        }
        
        muskeli.poista(make);
        System.out.println("Merkintoja: " +muskeli.getMerkinnat() + " Jasenia: "+muskeli.getJasenia());
        System.out.println("Lisatty liike: " + muskeli.annaLiike(merkinta.getLiikeID()).getLiike());
    }
    
    /**
     * metodi j‰senten m‰‰r‰n hakemiseen
     * @return palauttaa j‰senten lukum‰‰r‰n
     */
    public int getJasenia() {
                 return jasenet.getLkm();
             }
    
    /**
     * antaa listan kaikista jasenista
     * @return lista
     */
    public List<String> annaKaikkiJasenet(){
        return jasenet.annaKaikkiJasenet();
    }
    /**
     * antaa listan jasenista
     * @return lista
     */
    public List<Jasen> annaKaikkiJasenetlist(){
        return jasenet.annaKaikkiJasenetList();
    }
    /**
     * antaa listan kaikista liikkeist‰
     * @return lista
     */
    public List<String> annaKaikkiliikkeet(){
        return liikkeet.annaKaikkiliikkeet();
    }
  
    /**palauttaa liikkeen nimen perusteella
     * @param nimi nimi
     * @return liike
     */
    public Liike annaLiike(String nimi){
        return liikkeet.annaLiike(nimi);
    }
    
    /**
     * antaa liikkeen sen numeron perusteella
     * @param numero luku
     * @return liike
     */
    public Liike annaLiike(int numero){
       return liikkeet.annaLiike(numero);
        
    }
    
    
    /**
     * metodi liikkeiden m‰‰r‰n hakemiseen
     * @return palauttaa liikkeiden lukum‰‰r‰n
     */
    public int getLiikkeet() {
        return liikkeet.getLkm();
    }
    /**
     * palauttaa merkinn‰t
     * @return int
     */
    public int getMerkinnat(){
        return maarat.getLkm();
    }
    
    /**
     * tallentaa uuteen tiedostoon oletusliikkeet
     */
    public void getdefaultiikkeet(){
        try {
            liikkeet.getdefaultiikkeet();
        } catch (SailoException e) {
           
            e.printStackTrace();
        }
    }
    
    /**
     * metodi jasenen lis‰‰miseen
     * @param jasen j‰sen
     * @throws SailoException poikkeus
     */
    public void lisaa(Jasen jasen) throws SailoException {
        jasenet.lisaa(jasen);
    }
    
    
    /**
     * metodi liikkeiden lis‰‰miseen
     * @param liike liike
     */
    public void lisaa(Liike liike) {
        liikkeet.lisaa(liike);
    }
    
    /**
     * metodi p‰iv‰kirjamerkinn‰n lis‰‰miseen
     * @param merkinta merkint‰
     */
    public void lisaa(Merkinta merkinta) {
        maarat.lisaa(merkinta);
    }
    
    
    
    /**
     * metodi j‰senen hakemiseen
     * @param i indeksi jonka kohdalta listasta j‰sent‰ haetaan
     * @return palauttaa haeutun j‰senen
     * @throws IndexOutOfBoundsException poikkeus
     */
    public Jasen annaJasen(int i) throws IndexOutOfBoundsException {
        return jasenet.anna(i);
    }
    /**
     * antaa j‰senen nimen perusteella
     * @param nimi string
     * @return int
     */
    public int annaJasen(String nimi){
        return jasenet.anna(nimi);
    }
    
    /**
     * palauttaa jasenen nimen perusteella
     * @param nimi nimi 
     * @return jasen
     * @throws IndexOutOfBoundsException dd
     */
    public Jasen annaJasenNimi(String nimi) throws IndexOutOfBoundsException {
        return jasenet.annaJasen(nimi);
    }
    /**
     * J‰senen poistaminen
     * @param jasen j‰sen
     * @return luku
     */
    public int poista(Jasen jasen) {
        if ( jasen == null ) return 0;
        int ret = jasenet.poista(jasen.getTunnusNro());
        List<Merkinta> nimi = annaMerkinnat(jasen);
        for(Merkinta ok : nimi){
            if(ok.getKavijaID() == jasen.getTunnusNro()){
                maarat.poista(ok);
            }
        }
         
        return ret; 
    }
    /**
     * poistaa j‰senen liikkeit‰
     * @param jasen j‰sen
     * @param paivamaara aika
     */
    public void poista(Jasen jasen, String paivamaara) {
        List<Merkinta> nimi = annaMerkinnat(jasen);
        for(Merkinta ok : nimi){
            if(ok.getKavijaID() == jasen.getTunnusNro()){
                if(paivamaara.equals(ok.getPvm())){
                    maarat.poista(ok);
                }
            }
        }
        
    }
    
    /**
     * metodi liikkeiden hakemiseen
     * @param merk ei
     * @return palauttaa haetut liikkeet
     * @throws IndexOutOfBoundsException poikkeus
     */
    public Liike annaLiikkeet(Merkinta merk) throws IndexOutOfBoundsException {
        return liikkeet.annaLiike(merk.getLiikeID());
    }
    /**
     * J‰senen etsiminen
     * @param hakuehto string
     * @param k int
     * @return collection
     */
    public Collection<Jasen> etsi(String hakuehto, int k) { 
        return jasenet.etsi(hakuehto, k); 
    } 
    /**
     * esimerkki - ei k‰ytˆss‰
     * @param jnro int
     */
    public void vastaaEsim(int jnro){
        Merkinta test = new Merkinta();
        test.vastaaEsim(jnro, getMerkinnat());
    }
    
    
    /**
     * metodi merkintˆjen hakemiseen
     * @param jasen tseissen
     * @return palauttaa haetut p‰iv‰kirjamerkinn‰t
     * @throws IndexOutOfBoundsException poikkeus
     */
    public List<Merkinta> annaMerkinnat(Jasen jasen) throws IndexOutOfBoundsException {
        return maarat.annaMerkinnat(jasen.getTunnusNro());
    }
    
    /**
     * metodi tiedostosta lukemiseen
     * @param nimi nimi
     * @throws SailoException poikkeus
     */
    public void lueTiedostostaJasenet(String nimi) throws SailoException {   
        jasenet.lueTiedostosta(nimi);
    }
    
    /**
     * lukee tiedostosta liikkeet
     * @param nimi string
     * @throws SailoException exception
     */
    public void lueTiedostostaLiikkeet(String nimi) throws SailoException { 
        liikkeet.lueTiedostosta(nimi);
    }
    /**
     * lukee tiedostosta m‰‰r‰t
     * @param nimi nimi
     * @throws SailoException exception
     */
    public void lueTiedostostaMaarat(String nimi) throws SailoException {
        maarat.lueTiedostosta(nimi);
    }
    /**
     * metodi tiedostoon tallentamiseen
     * @throws SailoException poikkeus
     */
    public void tallenna() throws SailoException {
        jasenet.tallenna();
        liikkeet.tallenna();
        maarat.tallenna();
        
    }
    
    /**
     * p‰‰ohjelma
     * @param args p‰‰ohjelmalle annettavat argumentit
     */
    public static void main(String args[]) {
       Sali sali = new Sali();
       testi(sali);

    }

    
}
