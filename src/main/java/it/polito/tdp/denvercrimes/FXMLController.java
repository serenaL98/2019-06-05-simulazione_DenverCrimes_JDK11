package it.polito.tdp.denvercrimes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.denvercrimes.model.Distretto;
import it.polito.tdp.denvercrimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Integer> boxAnno;

    @FXML
    private ComboBox<Integer> boxMese;

    @FXML
    private ComboBox<Integer> boxGiorno;

    @FXML
    private Button btnCreaReteCittadina;

    @FXML
    private Button btnSimula;

    @FXML
    private TextField txtN;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaReteCittadina(ActionEvent event) {

    	Integer anno = this.boxAnno.getValue();
    	if(anno == null) {
    		txtResult.setText("Selezionare un anno!");
    		return;
    	}
    	
    	txtResult.setText("Crea grafo...");
    	this.model.creaGrafo(anno);
    	txtResult.appendText("\n\n#VERTICI: "+this.model.numeroVertici());
    	txtResult.appendText("\n#ARCHI: "+this.model.numeroArchi()+"\n");
    	
    	for(Distretto di: this.model.elencoVertici()) {
    		txtResult.appendText("\nDISTRETTI ADIACENTI A "+di.getCodice()+":\n"+this.model.distrettiAdiacenti(di));
    	}
    	
    	this.btnSimula.setDisable(false);
    	
    }

    @FXML
    void doSimula(ActionEvent event) {

    	txtResult.clear();
    	
    	Integer anno = this.boxAnno.getValue();

    	this.boxMese.getItems().addAll(this.model.prendiMese(anno));
    	Integer mese = this.boxMese.getValue();
    	if(mese == null) {
    		txtResult.setText("Selezionare un mese!");
    	}
    	
    	this.boxGiorno.getItems().addAll(this.model.prendiGiorno(anno, mese));
    	Integer giorno = this.boxGiorno.getValue();
    	if(giorno == null){
    		txtResult.setText("Selezionare un giorno!");
    	}
    	
    	
    	String numero = this.txtN.getText();
    	
    	try {
    		
    		int N = Integer.parseInt(numero);
    		
    		if(N<11 && N>0) {
    			this.model.simula(anno, mese, giorno, N);
        		
        		txtResult.appendText("SIMULA:\nCasi malgestiti = "+this.model.malGestiti());
        		
    		}
    		
    	}catch(NumberFormatException e) {
    		txtResult.setText("Interire un numero intero positivo da 1 a 10.\n");
    		return;
    	}
    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxAnno.getItems().addAll(this.model.prendiAnni());
		this.btnSimula.setDisable(true);
	}
}
