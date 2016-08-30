package pis16.memsysandroid.notifications.interfaces;

/**
 * Created by marccio on 30-Aug-16.
 */
public interface INotificationReceiver {

    /***
     * Método a implementar por el componente que se encargue de
     * recibir las notificaciones.
     *
     * @param msg Se recibe el mensaje como un String.
     */
    void onMessageReceived(String msg);

}
