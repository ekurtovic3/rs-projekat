package ba.unsa.etf.rs.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.time.LocalDate;

public class Calendar {
    public ListView lvCalendar;
    public Label lbYear;
    private int year = 2020;
    int day = LocalDate.now().getDayOfYear();

    @FXML
    public void initialize() {
        lbYear.setText(String.valueOf(year));
        setLvCalendar();


    }

    public boolean isLeap(int year) {
        boolean isLeap = false;

        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0)
                    isLeap = true;
                else
                    isLeap = false;
            } else
                isLeap = true;
        } else {
            isLeap = false;
        }
        return isLeap;
    }

    public void btnLeft(ActionEvent actionEvent) {
        if (year > 2020) {
            year -= 1;
            lbYear.setText(String.valueOf(year));
            boolean sa = isLeap(year);
            setLvCalendar();
        }
    }

    public void btnRight(ActionEvent actionEvent) {
        year += 1;
        lbYear.setText(String.valueOf(year));
        setLvCalendar();

    }

    void setLvCalendar() {
        lvCalendar.getItems().clear();
        if (year != 2020) {
            day = 1;
        } else {
            day = LocalDate.now().getDayOfYear();
        }
        int days = 0;
        if (isLeap(year)) {
            days = 366;
        } else {
            days = 365;
        }
        for (int i = day; i <= days; i++) {
            lvCalendar.getItems().add(LocalDate.ofYearDay(year, i));
        }
    }
}
