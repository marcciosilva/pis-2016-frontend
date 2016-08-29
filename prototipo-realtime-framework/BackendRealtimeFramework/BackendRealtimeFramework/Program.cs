using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Ibt.Ortc.Api.Extensibility;

namespace BackendRealtimeFramework
{
    class Program
    {
        static OrtcClient ortcClient;

        static void Main(string[] args)
        {
            Program program = new Program();
            program.blah();
            while (true)
            {
                string msg = Console.ReadLine();
                Console.WriteLine("Enviando mensaje...");
                while (ortcClient.IsSubscribed("myChannel") == false) ;
                ortcClient.Send("myChannel", msg);
            }
        }

        private void blah()
        {
            var api = new Ibt.Ortc.Api.Ortc();
            IOrtcFactory factory = api.LoadOrtcFactory("IbtRealTimeSJ");
            ortcClient = factory.CreateClient();

            ortcClient.ClusterUrl = "http://ortc-developers.realtime.co/server/2.1/";
            ortcClient.ConnectionMetadata = "myConnectionMetadata";

            ortcClient.OnConnected += new OnConnectedDelegate(ortc_OnConnected);
            ortcClient.OnSubscribed += new OnSubscribedDelegate(ortc_OnSubscribed);

            ortcClient.Connect("HDzwHh", "myToken");
        }

        private void ortc_OnConnected(object sender)
        {
            ortcClient.Subscribe("myChannel", true, OnMessageCallback);
        }

        private void OnMessageCallback(object sender, string channel, string message)
        {
            // Received message from channel
        }

        private void ortc_OnSubscribed(object sender, string channel)
        {
            // Sending Hello World message
            ortcClient.Send("myChannel", "Backend conectado a canal");
        }

    }


}
