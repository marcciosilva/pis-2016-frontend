using Ibt.Ortc.Api.Extensibility;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Utils.Notifications
{
    public partial class OrtcUsageForm : Form
    {
        private OrtcClient ortcClient;

        public OrtcUsageForm()
        {
            var api = new Ibt.Ortc.Api.Ortc();
            IOrtcFactory factory = api.LoadOrtcFactory("IbtRealTimeSJ");
            ortcClient = factory.CreateClient();

            ortcClient.ClusterUrl = "http://ortc-developers.realtime.co/server/2.1/";
            ortcClient.ConnectionMetadata = "myConnectionMetadata";

            ortcClient.OnConnected += new OnConnectedDelegate(ortc_OnConnected);
            ortcClient.OnSubscribed += new OnSubscribedDelegate(ortc_OnSubscribed);
            var aplicaciontion_key = "oGRNHh";
            var myToken= "hjTlU7c3MFRd";
            ortcClient.Connect(aplicaciontion_key, myToken);
        }



        public void ortc_OnConnected(object sender)
        {
            ortcClient.Subscribe("myChannel", true, OnMessageCallback);
        }

        public void OnMessageCallback(object sender, string channel, string message)
        {
            // Received message from channel
        }

        public void ortc_OnSubscribed(object sender, string channel)
        {
            // Sending Hello World message
            ortcClient.Send("myChannel", "Soy Andres");
        }
    }

}
