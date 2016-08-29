using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Utils.Notifications
{
    public static class FactoryNotifications
    {
        public static INotifications GetInstance() {
            return new NotificationsPubNub();
        }
    }
}
