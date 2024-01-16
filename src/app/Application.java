package app;

import ctrl.Controller;

public class Application {

    /**
     * Modèle d'application "MVC" pour le D400 (réalisé sans interfaces qui sont
     * vues au 226a en 2ème), avec ihm JavaFX.
     * 
     * @author EMF-Informatique
     */
    public static void main(String[] args) {
        new Controller().start();
    }

}
