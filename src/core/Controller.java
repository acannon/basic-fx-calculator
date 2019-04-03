package core;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private Label result;
    private float a = 0;
    private float b = 0;
    private String op = "";
    private boolean start = true;
    private boolean firstNum = true;    // "are we looking for the first number?"

    private Model model = new Model();

    @FXML
    public void processNumbers(ActionEvent e) {
        // if we just opened the calculator, set display to blank
        if(start) {
            result.setText("");
            start = false;
        }

        // get the value that was pressed by getting the Button label
        String value = ((Button)e.getSource()).getText();

        // if this is the first number, assign to a
        if(firstNum == true) {
            a = Float.parseFloat(value);
            firstNum = false;
        }
        else {  // else assign to b
            b = Float.parseFloat(value);
            firstNum = true;
        }

        // concatenate the number to the current display
        result.setText(result.getText() + value);
    }

    @FXML
    public void processOps(ActionEvent e) {
        // store the text of the button pressed
        String value = ((Button)e.getSource()).getText();

        if(!value.equals("=")) {
            // if we pressed an operator, but there is already one stored, do nothing
            if(!op.equals(""))
                return;

            // else store the pressed operator and display
            op = value;
            // a = Long.parseLong(result.getText());
            result.setText(result.getText() + value);
        }

        // if = was pressed, perform operation
        else {
            // if = was pressed, but there is no operator stored, do nothing
            if(op.equals(""))
                return;

            // otherwise, perform calculation and display result
            float output = model.calculate(a, b, op);

            // tricky way to display decimal only if needed
            if(op.equals("/"))
                result.setText(String.valueOf(output));
            else {
                int display = Math.round(output);   // since calc only allows integers as input, will only "chop" off .0
                result.setText(String.valueOf(display));
            }

            // reset values: no op, start is now true, and we are looking for firstNum
            op = "";
            start = true;
            firstNum = true;
        }
    }
}
