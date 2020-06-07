package it.polito.tdp.denvercrimes;

import java.net.URL;
import java.util.ResourceBundle;

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
    private ComboBox<?> boxAnno;

    @FXML
    private ComboBox<?> boxMese;

    @FXML
    private ComboBox<?> boxGiorno;

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

    }

    @FXML
    void doSimula(ActionEvent event) {

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
	}
}
