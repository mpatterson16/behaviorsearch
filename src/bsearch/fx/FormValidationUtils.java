
package bsearch.fx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class FormValidationUtils {
	// based on code by Evan Knowles
	// https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
	public static void enforceNumericInput(TextField field, boolean allowDouble) {
		field.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (allowDouble) {
					if (!newValue.matches("[0-9.]*")) {
						field.setText(newValue.replaceAll("[^0-9.]*", ""));
					}
				} else {
					if (!newValue.matches("[0-9]*")) {
						field.setText(newValue.replaceAll("[^0-9]*", ""));
					}
				}
				try {
					Double.valueOf(newValue);
					field.setStyle("");
				} catch (NumberFormatException ex) {
					field.setStyle("-fx-control-inner-background:pink;");
				}
			}
		});
		

	}
}
