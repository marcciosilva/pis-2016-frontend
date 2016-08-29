using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Utils.Notifications
{
    public interface INotifications
    {
        void CreateInstance();
        void SubscribeChanel(string ChanelName);
        void UnsubscribeChanel(string channelName);
        void SendMessage(string channelName, string Message);
    }
}
