package ba.unsa.etf.rs.controller;

import ba.unsa.etf.rs.database.CountryDao;
import ba.unsa.etf.rs.model.Country;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class StartScreenController implements Initializable {
    private CountryDao countryDao;

    public TextField tfUsernamLeogIn;
    public TextField tfPasswordLogIn;
    public TextField tfUsernameSignIn;
    public TextField tfPasswordSignIn;
    public ImageView imvIkonaDrzave;
    public ChoiceBox<Country> cbCountrySignIn;

    public StartScreenController(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    /*
    pokrece se odmah pri kreiranju
    ovi parametri metode moraju postojati, jer je obavezno preklopiti ovu metodu kada implementiramo "Initializable"
    */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        cbCountrySignIn.setItems(countryDao.getAllCountries());

        //LISTENER
        cbCountrySignIn.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value) {

                // set the text for the label to the selected item
                System.out.println(cbCountrySignIn.getItems().get(new Integer(new_value.toString())));

                Country currentlySelectedCountry = cbCountrySignIn.getItems().get(new Integer(new_value.toString()));

                setCountriesIcon(currentlySelectedCountry);

            }

        });

    }

    private void setCountriesIcon(Country currentlySelectedCountry)
    {
        String countryNameInLowerCase = currentlySelectedCountry.toString().toLowerCase();

        String partsOfCountyName[] = countryNameInLowerCase.split(" ");

        String countryNameWithDashes = new String("");
        for(int i = 0; i < partsOfCountyName.length; i++)
        {
            countryNameWithDashes += partsOfCountyName[i];

            if(i+1 != partsOfCountyName.length)
                countryNameWithDashes += "-";
        }

        imvIkonaDrzave.setImage(new Image("/img/countryFlags/png/" +countryNameWithDashes+".png"));
        imvIkonaDrzave.setPreserveRatio(true);
    }
}