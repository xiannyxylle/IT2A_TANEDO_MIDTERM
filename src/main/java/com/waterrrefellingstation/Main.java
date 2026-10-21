package com.waterrrfellingstation;

import com.waterrrfellingstation.dao.ScenarioDao;
import com.waterrrfellingstation.ui.ScenarioMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ScenarioDao dao = new ScenarioDao();
        Scanner scanner = new Scanner(System.in);

        ScenarioMenu menu = new ScenarioMenu(dao, scanner);
        menu.start();
    }
}