using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Utils.Notifications;
namespace TriggerProgram
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("abrio una app el trigger :D");
            var cliente = FactoryNotifications.GetInstance();
            //string channel = "Channel-15t1x43ax";
            string channel = "myChannel";
            cliente.SubscribeChanel(channel);
            while (true)
            {
                Console.WriteLine("Ingrese un mensaje para enviar...");
                string msg = Console.ReadLine();
                cliente.SendMessage(channel, msg);
            }
        }
    }
}
