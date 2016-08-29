using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Utils.Notifications;
using Utils.Notifications;
namespace TriggerProgram
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("abrio una app el trigger :D");

            //logica para realtime framework                       
            //var inicioComunicacion = new OrtcUsageForm();
            //inicioComunicacion.ortc_OnConnected(new object());
            //inicioComunicacion.ortc_OnSubscribed(new object(),"");
            //Console.ReadLine();

            //logica para pubnub
            var cliente = FactoryNotifications.GetInstance();
            cliente.CreateInstance();
            string channel = "Channel-15t1x43ar";
            cliente.SuscribeChanel(channel);
            cliente.SendMessage(channel,"Esto es una prueba :D");
            Console.ReadLine();
        }
    }
}
