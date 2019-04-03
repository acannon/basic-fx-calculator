package core;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.EmptyStackException;
import java.util.Stack;

public class Controller {

    @FXML
    private Label result;
    private boolean start = true;
    String lastKey = "";


    private Model model = new Model();

    @FXML
    public void clear() {
        start = true;
        result.setText("");
    }

    @FXML
    public void processNumbers(ActionEvent e) {
        if(start || lastKey.equals("=")) {
            result.setText("");
            start = false;
        }

        // get the value that was pressed by getting the Button label
        String value = ((Button)e.getSource()).getText();

        // concatenate the number to the current display
        result.setText(result.getText() + value);

        lastKey = value;
    }

    @FXML
    public void processOps(ActionEvent e) {
        Double output = 0.0;

        // store the text of the button pressed
        String value = ((Button)e.getSource()).getText();

        if(!value.equals("="))
            result.setText(result.getText() + value);

        // if = was pressed, perform calculation
        else {
            // get full equation as string, then tokenize and try to parse
            String equation = result.getText();

            try {
                output = model.calculate(equation);
            } catch (EmptyStackException exception) {
                result.setText("ERROR");
                clear();
            }

            // tricky way to display decimal only if needed
            if(output % 1 != 0)
                result.setText(String.valueOf(output));
            else {
                int display = (int)Math.round(output);   // since calc only allows integers as input, will only "chop" off .0
                result.setText(String.valueOf(display));
            }
        }

        lastKey = value;
    }
}
