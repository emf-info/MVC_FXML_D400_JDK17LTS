package services;

import models.Voiture;

public class ServiceLocation {

    private final static int MAX_VOITURES_LOUEES = 4;

    private Voiture[] voituresDisponibles;
    private Voiture[] voituresLoueesParLeClient;

    public ServiceLocation() {
        voituresLoueesParLeClient = new Voiture[MAX_VOITURES_LOUEES];
        voituresDisponibles = new Voiture[] {
                new Voiture("Audi", "S3"),
                new Voiture("Audi", "S4"),
                new Voiture("BMW", "X5"),
                new Voiture("BMW", "530e"),
                new Voiture("BMW", "320i"),
                new Voiture("Bugatti", "Veyron"),
                new Voiture("Seat", "Cupra sport"),
                new Voiture("Seat", "Leon"),
                new Voiture("Skoda", "Fabia break")
        };
    }

    public Voiture[] listeDesVoituresDisponibles() {
        return voituresDisponibles;
    }

    public Voiture[] listeDesVoituresLoueesParLeClient() {
        return voituresLoueesParLeClient;
    }

    public boolean clientLoueUneVoiture(Voiture voiture) {

        boolean reussi = false;

        // A-t-on reçu une voiture à louer ?
        if (voiture != null) {

            // Trouver une place libre pour y mettre cette voiture louée.
            // S'il n'y en a plus, c'est que le client a loué trop de voitures
            int positionPlaceLibre = -1;
            for (int i = 0; i < voituresLoueesParLeClient.length; i++) {
                // Peut-il encore louer une voiture ?
                if (voituresLoueesParLeClient[i] == null) {
                    // On arrête d'en chercher une autre
                    positionPlaceLibre = i;
                    break;
                }
            }

            // A-t-on trouvé une place libre ?
            if (positionPlaceLibre >= 0) {

                // Vérifier que cette voiture est toujours encore disponible à la location
                // et la sortir de la liste si c'est le cas
                for (int i = 0; i < voituresDisponibles.length; i++) {

                    // Voiture souhaitée toujours encore dans la liste des voiture louables ?
                    if (voituresDisponibles[i] == voiture) {

                        // La sortir des voitures louables
                        voituresDisponibles[i] = null;

                        // La mettre dans les voitures louées par ce client
                        voituresLoueesParLeClient[positionPlaceLibre] = voiture;

                        // Indiquer qu'on a réussi à tout faire
                        reussi = true;

                        // Arrêter de chercher ce véhicule car trouvé et traité
                        break;
                    }
                }
            }
        }

        return reussi;
    }

    public boolean clientRestitueUneVoiture(Voiture voiture) {

        boolean reussi = false;

        // A-t-on reçu une voiture à restituer ?
        if (voiture != null) {

            // Trouver l'emplace de ce véhicule dans la liste des véhicules loués
            // et le supprimer de là
            for (int i = 0; i < voituresLoueesParLeClient.length; i++) {
                if (voituresLoueesParLeClient[i] == voiture) {
                    voituresLoueesParLeClient[i] = null;
                    break; // Inutile de continuer à le chercher
                }
            }

            // Trouver une place libre pour remettre ce véhicule dans la liste
            // des véhicules désormais à nouveau louables
            for (int i = 0; i < voituresDisponibles.length; i++) {
                if (voituresDisponibles[i] == null) {
                    voituresDisponibles[i] = voiture;
                    reussi = true; // On a réussi à tout faire !
                    break; // Ne remettre ce véhicule dans la liste qu'une seule fois !
                }

            }
        }

        return reussi;
    }

}
