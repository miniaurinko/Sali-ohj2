package salirakenteet;

import java.io.PrintStream;

/**
 * 
 * @version 21.4.2017
 *
 */
public class Merkinta {
    private int MerkintaID;
    private int KavijaID;
    private int Toistot;
    private int Painot;
    private int Sarjat;
    private int LiikeID;
    private int Kesto;
    private int Paiva;
    private int Kuukausi;
    private int Vuosi;
    
    private int seuraavaNro = 1;
    
 
    
    
    /**
     * merkinnän alustus
     */
    public Merkinta(){
        
    }
    
    /**
     * Merkinnän alustus
     * @param KavijaID numero
     */
    public Merkinta(int KavijaID){
        this.KavijaID = KavijaID;
    }
    /**
     * toinen konstruktori merkinnälle
     * @param kavija nimi
     * @param toistot toistot
     * @param painot painot
     * @param sarjat sarjat
     * @param liikeid liikeid
     * @param kesto kesto
     * @param paiva päivä
     * @param kuukausi kuukausi
     * @param vuosi vuosi
     */
    public Merkinta(int kavija, int toistot, int painot, int sarjat, int liikeid, int kesto, int paiva, int kuukausi, int vuosi){
        MerkintaID = seuraavaNro;
        KavijaID = kavija;
        Toistot = toistot;
        Painot = painot;
        Sarjat = sarjat;
        LiikeID = liikeid;
        Kesto = kesto;
        Paiva = paiva;
        Kuukausi = kuukausi;
        Vuosi = vuosi;
    }
    /**
     * Esimerkkitiedon antaminen
     * @param nro numero
     * @param mones mones
     */
    public void vastaaEsim(int nro, int mones) {
        MerkintaID = seuraavaNro + mones;
        KavijaID = nro;
        Toistot = 3;
        Painot = 50;
        Sarjat = 5;
        LiikeID = 1;
        Kesto = 60;
        Paiva = 30;
        Kuukausi = 5;
        Vuosi = 2050;
       
        
    }
    /**
     * getkesto
     * @return kesto
     */
    public int getKesto(){
        return this.Kesto;
    }
    /**
     * gettoistot
     * @return toistot
     */
    public int getToistot(){
        return this.Toistot;
    }
    /**
     * getsarjat
     * 
     * @return sarjat
     */
    public int getSarjat(){
        return this.Sarjat;
    }
    
    /**
     * getpainot
     * @return painot
     */
    public int getPainot(){
        return this.Painot;
    }
    
    /**
     * tulostus
     * @param out printline
     * @param liikenimi liikkeen nimi
     */
    public void tulosta(PrintStream out, String liikenimi) {
        
        
        out.println("Päivämäärä: "+Paiva+"."+Kuukausi+"."+Vuosi + " Liike: "+liikenimi+ " Toistot: "+Toistot+" Painot: "+Painot+" Sarjat: "+Sarjat+" Kesto: "+Kesto );
    }
    
    /**
     * Merkinnän rekisteröinti
     * @return merkinnän id
     */
    public int rekisteroi() {
        MerkintaID = seuraavaNro;
        seuraavaNro++;
        return MerkintaID;
    }
    
    /**
     * Merkintäid:n palautus
     * @return merkintäid
     * @example
     * <pre name="test">
     *   Merkinta asd = new Merkintä();
     *   asd.vastaaEsim(6);
     *   asd.getMerkintaID() =R= 6;
     * </pre>
     */
    public int getMerkintaID(){
        return MerkintaID;
    }
    
    /**
     * hakee liikeidn
     * @return liikeid
     */
    public int getLiikeID(){
        return LiikeID;
    }
    /**
     * päivämäärälle get-metodi
     * @return string
     */
    public String getPvm(){
        String asd = Paiva + "." + Kuukausi + "." + Vuosi;
        return asd;
    }
    
    /**
     * kävijäid:n get -metodi
     * @return idnumero
     */
    public int getKavijaID() {
        return KavijaID;
    }
    /**
     * tostring
     */
    @Override
    public String toString(){
        return "" +
                MerkintaID +"|"+ KavijaID+"|"+Toistot+"|"+Painot+"|"+Sarjat+"|"+LiikeID+"|"+Kesto+"|"+
                Paiva+"."+Kuukausi+"."+Vuosi+"|";
    }
    

    /**
     * @param rivi rivi
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        String[] asd = sb.toString().split("\\|");
        String[] pvm = asd[7].split("\\.");
        
        MerkintaID = Integer.parseInt(asd[0]);
        KavijaID = Integer.parseInt(asd[1]);
        Toistot = Integer.parseInt(asd[2]);
        Painot = Integer.parseInt(asd[3]);
        Sarjat = Integer.parseInt(asd[4]);
        LiikeID = Integer.parseInt(asd[5]);
        Kesto = Integer.parseInt(asd[6]);
        
        Paiva = Integer.parseInt(pvm[0]);
        Kuukausi = Integer.parseInt(pvm[1]);
        Vuosi = Integer.parseInt(pvm[2]);
        
    }
}
