package Sali;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.Pane;
import salirakenteet.*;
import Sali.SaliGUIController;
/**
 * P‰‰ohjelma sali -ohjelman k‰ynnistyst‰ varten
 * 
 * @author Ville Kuokkanen, Matti Huttunen
 * @version 16.2.2017
 * 
 * Ohjelmassa ei toimi j‰senen ja salik‰ynnin lis‰‰minen omien tehtyjen ikkunoiden kautta.
 * Liikkeen tietoja(painot, sarjat, toistot) ei tyhjennet‰ joka kerta kun painetaan jostain miss‰ niit‰ ei ole.
 * Tulostamiselle ei olla tehty toteutusta.
 * 
 */


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		    
			final FXMLLoader ldr = new FXMLLoader(getClass().getResource("SaliGUIView.fxml"));
			final Pane root = (Pane)ldr.load();
			final Scene scene = new Scene(root);
			final SaliGUIController saliCtrl = (SaliGUIController)ldr.getController();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			Sali sali = new Sali();
			saliCtrl.setSali(sali);
			sali.getdefaultiikkeet();
			sali.lueTiedostostaMaarat("maarat");
	        sali.lueTiedostostaJasenet("nimet");
	        sali.lueTiedostostaLiikkeet("liikkeet");
	        
	        
	        primaryStage.show();
	        saliCtrl.avaa();
	        
			
			primaryStage.setOnCloseRequest((event) -> {
			if( !saliCtrl.voikoSulkea() ) event.consume();
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param args p‰‰ohjelma
	 */
	public static void main(String[] args) {
		launch(args);
		
		
        
	}
}
