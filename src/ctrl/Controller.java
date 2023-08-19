package ctrl;

import models.PrevisionMeteo;
import models.Voiture;
import services.ServiceLocation;
import services.ServiceMeteo;
import views.View;

public class Controller {

    private final View view;
    private final ServiceMeteo serviceMeteo;
    private final ServiceLocation serviceLocation;

    public Controller() {
        view = new View(this);
        serviceMeteo = new ServiceMeteo();
        serviceLocation = new ServiceLocation();
    }

    public void start() {
        view.start();

        view.afficheVoituresDisponibles(serviceLocation.listeDesVoituresDisponibles(), null);
        view.afficheVoituresLouees(serviceLocation.listeDesVoituresLoueesParLeClient(), null);
    }

    public void actionRafraichirPrevisionMeteo() {
        PrevisionMeteo dernierePrevision = serviceMeteo.prochainBulletinMeteo();
        view.afficheDernierePrevision(dernierePrevision);
    }

    public void actionLouerUneVoiture(Voiture voiture) {
        if (serviceLocation.clientLoueUneVoiture(voiture)) {
            view.afficheVoituresDisponibles(serviceLocation.listeDesVoituresDisponibles(), null);
            view.afficheVoituresLouees(serviceLocation.listeDesVoituresLoueesParLeClient(), voiture);
            view.messageInfo("Voiture louée !");
        } else {
            view.messageErreur("La location de cette voiture n'a pas réussi !");
        }
    }

    public void actionRestituerUneVoiture(Voiture voiture) {
        if (serviceLocation.clientRestitueUneVoiture(voiture)) {
            view.afficheVoituresDisponibles(serviceLocation.listeDesVoituresDisponibles(), voiture);
            view.afficheVoituresLouees(serviceLocation.listeDesVoituresLoueesParLeClient(), null);
            view.messageInfo("Voiture restituée !");
        } else {
            view.messageErreur("La restitution de cette voiture n'a pas réussi !");
        }
    }

}
