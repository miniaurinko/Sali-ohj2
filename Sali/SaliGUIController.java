package Sali;

import java.io.PrintStream;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;



import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import fi.jyu.mit.fxgui.*;
import salirakenteet.*;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;


/**
 * Luokka k‰yttˆliittym‰n tapahtumien hoitamiseksi
 * @author Ville Kuokkanen, Matti Huttunen
 * @version 16.2.2017
 * 
 */
public class SaliGUIController implements Initializable{
	private Sali sali;
	@FXML private ListChooser<Jasen> chooserJasenet;
	@FXML private ListChooser<Liike> chooserLiikkeet;
	@FXML private TextField hakuehto;
	@FXML private Label labelVirhe;
	@FXML private DatePicker guiPvm;
	@FXML private Button paivitaPaiva;
	@FXML private TextField fxKesto;
	@FXML private TextField fxPainot;
	@FXML private TextField fxSarjat;
	@FXML private TextField fxToistot;
	private Jasen jasenKohdalla;
	private Liike liikeKohdalla;
	private String paivamaara ="1.1.2017";
	
	
	
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
             alusta();
             
                       
        }
   
    /**
     * alustaa
     */
    protected void alusta() {
        chooserJasenet.addSelectionListener(e -> naytaJasen());
        
        chooserLiikkeet.addSelectionListener(f -> naytaMerkinta());
        
    }
    /**
	 * K‰sitell‰‰n j‰senen lis‰ys
	 */
    @FXML void handleUusiJasen() {
        
        uusiJasen();
        
        
    }
    
    /**
     * ok- napin painamiselle tarvittava k‰sittelij‰
     */
    @FXML void handleOk(){
        if (guiPvm.getValue() == null)
            return;

        paivamaara = guiPvm.getValue()
                .format(DateTimeFormatter.ofPattern("d.M.yyyy"));
        if (paivamaara == null)
            return;
        naytaLiikkeet(jasenKohdalla);
        
    }
    
    @FXML void handleVaihdanimi(){
        String uusinimi = Dialogs.showInputDialog("Anna uusi nimesi", null);
        if(uusinimi == null) return;
        Jasen vaihto = jasenKohdalla;
        vaihto.setNimi(uusinimi);
        avaa();
        
    }
    
    /**
     * K‰sittelij‰ j‰senen poistamiseen
     */   
    @FXML private void handlePoistaJasen() {
        if ( jasenKohdalla == null ) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko j‰sen: " + jasenKohdalla.getNimi(), "Kyll‰", "Ei") )
            return;
        sali.poista(jasenKohdalla);
       
        avaa();
        
    }
    /**
     * salik‰ynnin poisto
     */
    @FXML private void handlePoistaSalikaynti(){
        if (liikeKohdalla == null) return;
        if( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko merkint‰", "Joo", "Ei"))
            return;
        sali.poista(jasenKohdalla, paivamaara);
        liikeKohdalla = null;
        avaa();
    }
     
    /**
     * Hakee j‰senen numeron perusteella
     *
     * 
     * @param jnro j‰senen numero
     */
    protected void haeJasen(int jnro) {
              chooserJasenet.clear();
              List<Jasen> jasenet = sali.annaKaikkiJasenetlist();
              List<String> nimet = new ArrayList<String>();
              List<Jasen> sortjasenet = new ArrayList<Jasen>();
              
              for (int i = 0; i < sali.getJasenia(); i++) {
                  Jasen jasen = sali.annaJasen(i);
                  if (jasen.getTunnusNro() == jnro);
                  nimet.add(jasen.getNimi());
               }
              
              java.util.Collections.sort(nimet);
              int a = 0;
              int b = 0;
              while( sortjasenet.size() < jasenet.size()){
                  if(b == jasenet.size()) b = 0;
                  Jasen jasen = jasenet.get(b);
                  if(nimet.get(a).equals(jasenet.get(b).getNimi())) {
                      sortjasenet.add(jasen);
                      a++; b = 0;
                  }
                  
                  b++;
              }
              
              
              
              
              int index = 0;
             for (int i = 0; i < sali.getJasenia(); i++) {
                Jasen jasen = sortjasenet.get(i);
                //if (jasen.getTunnusNro() == jnro) 
                index = i;
                chooserJasenet.add(jasen.getNimi(), jasen);
             }
            chooserJasenet.setSelectedIndex(index); 
        }
    
    /**
     * Uuden j‰senen lis‰‰minen k‰yttˆliittym‰‰n
     */
    protected void uusiJasen() {
                   Jasen uusi = new Jasen();
                   
                   String a = Dialogs.showInputDialog("Anna nimi", null);
                   if(a == null || a.length() <= 0) return;
                   uusi.setNimi(a);
                   uusi.rekisteroi(sali.getJasenia());
                    
                   try {
                      sali.lisaa(uusi);
                   } catch (SailoException e) {
                       Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
                       return;
                   }
                   haeJasen(uusi.getTunnusNro());
        }
    
    /**
     * N‰ytt‰‰ valitun j‰senen merkinn‰t
     */
    protected void naytaMerkinta(){
        List<Merkinta> merc = sali.annaMerkinnat(jasenKohdalla);
        liikeKohdalla = chooserLiikkeet.getSelectedObject();
        
        fxKesto.clear();
        fxPainot.clear();
        fxSarjat.clear();
        fxToistot.clear();
        
        for(Merkinta ok : merc){
            if(ok.getLiikeID() == liikeKohdalla.getTunnusNro()){
                if(ok.getPvm().equals(paivamaara)){
                    fxKesto.setText(""+ok.getKesto());
                    fxPainot.setText(""+ok.getPainot());
                    fxSarjat.setText(""+ok.getSarjat());
                    fxToistot.setText(""+ok.getToistot());
                }
            }
          
        }
    }
    
    /**
     * N‰ytt‰‰ uuden j‰senen k‰yttˆliittym‰ss‰
     */
    protected void naytaJasen() { 
        
      jasenKohdalla = chooserJasenet.getSelectedObject();
      if (jasenKohdalla == null) return; 
      
      naytaLiikkeet(jasenKohdalla);
      fxKesto.clear();
      fxPainot.clear();
      fxSarjat.clear();
      fxToistot.clear();

    }
       
    
    /**
     * Asetetaan sali
     * @param sali kuntosali
     */
    public void setSali(Sali sali) {
        this.sali = sali;
        
        naytaJasen();
    }
    
 
    /**
     * Lis‰t‰‰n uusi liike
     */
    @FXML void handleUusiSalikaynti() {
        
        
        try {
            uusiLiike();
        } catch (SailoException e) {
            e.printStackTrace();
        }
        
        
        
        
    }
    
    /**
     * metodi liikkeen hakuun
     * @param lnro liikkeen numero
     */
    protected void haeliike(int lnro) {
        chooserLiikkeet.clear();
        
  
        int index = 0;
       for (int i = 0; i < sali.getLiikkeet(); i++) {
           Liike liike = new Liike();
          if (liike.getTunnusNro() == lnro) index = i;
          chooserLiikkeet.add(liike.getLiike(), liike);
       }
      chooserJasenet.setSelectedIndex(index); 
    }
    
 
   /**
    * n‰ytt‰‰ j‰senen liikkeet
    * @param jasen
    */
    private void naytaLiikkeet(Jasen jasen){
        chooserLiikkeet.clear();
        if(jasen == null) return;
        
        List<Merkinta> merc = sali.annaMerkinnat(jasen);
        
        for(Merkinta ok : merc){
            
            if (paivamaara.equals( ok.getPvm()))
            chooserLiikkeet.add(sali.annaLiikkeet(ok).getLiike(), sali.annaLiikkeet(ok));
        }
        
         
        
    }
    
    /**
     * Luo listaan uuden liikkeen
     * @throws SailoException asd
     */
    protected void uusiLiike() throws SailoException{
        
        if (jasenKohdalla == null) return;
        
        String kesto = "0";
        String painot = "0";
        String sarjat = "0";
        String toistot = "0";
        String nimi = "0";
        String paiva = "0";
        String kuukausi = "0";
        String vuosi = "0";
        
        String liike = "0";
        
        nimi = Dialogs.showInputDialog("J‰senen nimi", null);
        if(nimi != null) 
            kesto = Dialogs.showInputDialog("Harjoituksen kesto", null);
            else return;
            if(kesto != null && !kesto.matches(".*[a-z].*"))
                liike = Dialogs.showInputDialog("Anna liikkeen nimi", null);
                else return;
            if(liike != null)
                    sarjat = Dialogs.showInputDialog("Sarjojen m‰‰r‰", null);
                    else return;
                    if(sarjat != null && !sarjat.matches(".*[a-z].*"))
                        toistot = Dialogs.showInputDialog("Toistojen m‰‰r‰", null);
                        else return;
                        if(toistot != null && !toistot.matches(".*[a-z].*"))
                            painot = Dialogs.showInputDialog("Painojen m‰‰r‰", null);
                            else return;
                        if(painot != null && !painot.matches(".*[a-z].*"))
                                paiva = Dialogs.showInputDialog("P‰iv‰", null );
                                else return;
                                if(paiva == null || paiva.matches(".*[a-z].*")) return; if( 0 < Integer.parseInt(paiva) || Integer.parseInt(paiva) < 32 )
                                    kuukausi = Dialogs.showInputDialog("Kuukausi", null);
                                else return;
                                    if(kuukausi == null || kuukausi.matches(".*[a-z].*")) return; if( 0 < Integer.parseInt(kuukausi)||Integer.parseInt(kuukausi) < 13) 
                                        vuosi = Dialogs.showInputDialog("Vuosi", null);
                                        else return;
                                        if(vuosi ==null || vuosi.matches(".*[a-z].*")) return; if( 1900 < Integer.parseInt(paiva)||Integer.parseInt(paiva) < 3000);
                                        else return;
                            
                        
                    
                
            
        
       
        
        
        Jasen tseissen = new Jasen();
        String[] asb = new String[sali.getJasenia()];
        List<String> asd = sali.annaKaikkiJasenet();
        boolean onkoNimiMuuttunut = true;
        for(int i=0;i<asb.length;i++){
            asb[i] = asd.get(i);
        }
        
        
        for(int i = 0; i<sali.getJasenia(); i++){
            if (asb[i].equals(nimi)){
                tseissen = sali.annaJasenNimi(nimi);
                onkoNimiMuuttunut = false;
                continue;
            }
        }
        
        if (onkoNimiMuuttunut){
            tseissen = new Jasen(nimi, sali.getJasenia());
            tseissen.rekisteroi(sali.getJasenia());
            sali.lisaa(tseissen);
        }
        
        
        
        Liike move = new Liike();
        List<String> a = sali.annaKaikkiliikkeet();
        
        boolean onkoMuuttunut = true;
        for( String liikel : a){
            
            if(liikel.equals(liike)){
                move = sali.annaLiike(liike);
                onkoMuuttunut = false;
                continue;
            }
        }
        if(onkoMuuttunut){
            move = new Liike(liike, sali.getLiikkeet());
            move.rekisteroi();
            sali.lisaa(move);
            
        }
        
        int intliike = move.getTunnusNro();
        int intkesto = Integer.parseInt(kesto);
        int intpainot = Integer.parseInt(painot);
        int intsarjat = Integer.parseInt(sarjat);
        int inttoistot = Integer.parseInt(toistot);
        int intnimi = tseissen.getTunnusNro();
        int intpaiva = Integer.parseInt(paiva);
        int intkuukausi = Integer.parseInt(kuukausi);
        int intvuosi = Integer.parseInt(vuosi);
        
        Merkinta merk = new Merkinta(intnimi, inttoistot, intpainot, intsarjat, intliike, intkesto, intpaiva, intkuukausi, intvuosi);
          merk.rekisteroi();
          sali.lisaa(merk);
                   
            
        
        naytaLiikkeet(jasenKohdalla);
        avaa();
    }
    /**
     * apua -ruutu
     */
    @FXML void handleApua() {
        
        ModalController.showModal(AboutController.class.getResource("about.fxml"), "About", null, "");
    }

    /**
     * tallentaa tiedoston
     */
    @FXML void handleTallenna() {
        tallenna();
    }
    
   

    /**
     * tulostuksen k‰sittelij‰
     */
    @FXML void handleTulosta() {
        TulostusController tulostusCtrl = TulostusController.tulosta(null); 
        tulostaValitut(tulostusCtrl.getTextArea());
    }
    
    /**
     * Tulostaa listassa olevat j‰senet tekstialueeseen
     * @param text alue johon tulostetaan
     */
    public void tulostaValitut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("J‰senten merkinn‰t"); 
            for (Jasen jasen: chooserJasenet.getObjects()) { 
                tulosta(os, jasen);
                os.println("\n\n");
            }
        }
    }
    
    /**
     * Tulostaa j‰senen tiedot
     * @param os tietovirta johon tulostetaan
     * @param jasen tulostettava j‰sen
     */
    public void tulosta(PrintStream os, final Jasen jasen) {
        os.println("----------------------------------------------");
        jasen.tulosta(os);
        os.println("----------------------------------------------");
        List<Merkinta> merkinnat = sali.annaMerkinnat(jasen);
        for (Merkinta har:merkinnat) {
            String o = sali.annaLiike(har.getLiikeID()).getLiike();
            har.tulosta(os, o); 
        }
    }

    /**
     * tallentamisen k‰sittelij‰
     */
    private void tallenna() {
        
        try {
            sali.tallenna();
        } catch (SailoException e) {
            
            e.printStackTrace();
        }
    }
    
    /**
     * Ohjelman lopetus
     */
    @FXML void handleLopeta() {
        tallenna();
        Platform.exit();
    }

 

    
    /**
     * Kysyt‰‰n halutaanko sulkea
     * @return true tai false
     */
    public boolean voikoSulkea() {
        return true;
    }
    
   
    
    /**
     * @return salinnimi
     */
    public boolean avaa() {
        for(int i = 0; i<sali.getJasenia();i++){
            haeJasen(i);
            }
              
         return true;
    }
    
}
