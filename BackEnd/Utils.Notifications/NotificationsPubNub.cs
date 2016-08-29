using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PubNubMessaging.Core;

namespace Utils.Notifications
{
    public class NotificationsPubNub : INotifications
    {
        private static string PublishKey = "pub-c-c5089221-380a-41a7-ae4b-5501c8b31f1c";
        private static string SuscribeKey = "sub-c-241413ae-6d74-11e6-92a0-02ee2ddab7fe";

        private static Pubnub _pubnubCliente;
        public void CreateInstance()
        {
            _pubnubCliente = new Pubnub(PublishKey, SuscribeKey);
        }
        /// <summary>
        /// Channel names are UTF-8 compatible. Prohibited chars in a channel name are:
        /// comma: ,
        /// slash: /
        /// backslash: \
        /// period: .
        /// asterisks: *
        /// colon: :
        /// </summary>
        /// <param name="channelName"></param>
        public void SubscribeChanel(string channelName)
        {
            _pubnubCliente.Subscribe<string>(channel: channelName, subscribeCallback: DisplaySubscribeReturnMessage, connectCallback: DisplaySubscribeConnectStatusMessage,
                errorCallback: DisplayErrorMessage);
        }

        public void UnsubscribeChanel(string channelName)
        {
            _pubnubCliente.Subscribe<string>(channel: channelName, subscribeCallback: DisplaySubscribeReturnMessage, connectCallback: DisplaySubscribeConnectStatusMessage,
                errorCallback: DisplayErrorMessage);
        }

        public void SendMessage(string channelName, string message)
        {
            _pubnubCliente.Unsubscribe<string>( channel: channelName, subscribeCallback: DisplayReturnMessage, connectCallback: DisplaySubscribeConnectStatusMessage
                , disconnectCallback: DisplaySubscribeDisconnectStatusMessage, errorCallback: DisplayErrorMessage);
        }       


       /// <summary>
       /// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       /// </summary>
       /// 


        public NotificationsPubNub()
        {
            _pubnubCliente = new Pubnub(PublishKey, SuscribeKey);
            _pubnubCliente.Subscribe<string>(
        channel: "Channel-15t1x43ar",
        subscribeCallback: DisplaySubscribeReturnMessage,
        connectCallback: DisplaySubscribeConnectStatusMessage,
        errorCallback: DisplayErrorMessage);
        }

        public void DisplaySubscribeDisconnectStatusMessage(string var) {

        }
        public void DisplaySubscribeConnectStatusMessage(string connectMessage)
        {
            //Console.WriteLine("SUBSCRIBE CONNECT CALLBACK");
            //_pubnubCliente.Publish<string>(
            //    channel: "Channel-15t1x43ar",
            //    message: "Soy Andres",
            //    userCallback: DisplayReturnMessage,
            //    errorCallback: DisplayErrorMessage);
        }

        void DisplaySubscribeReturnMessage(string result)
        {
            //Console.WriteLine("SUBSCRIBE REGULAR CALLBACK:");
            //Console.WriteLine(result);
            if (!string.IsNullOrEmpty(result) && !string.IsNullOrEmpty(result.Trim()))
            {
                List<object> deserializedMessage = _pubnubCliente.JsonPluggableLibrary.DeserializeToListOfObject(result);
                if (deserializedMessage != null && deserializedMessage.Count > 0)
                {
                    object subscribedObject = (object)deserializedMessage[0];
                    if (subscribedObject != null)
                    {
                        //IF CUSTOM OBJECT IS EXCEPTED, YOU CAN CAST THIS OBJECT TO YOUR CUSTOM CLASS TYPE
                        string resultActualMessage = _pubnubCliente.JsonPluggableLibrary.SerializeToJsonString(subscribedObject);
                    }
                }
            }
        }

        void DisplayErrorMessage(PubnubClientError pubnubError)
        {
            Console.WriteLine(pubnubError.StatusCode);
        }

        void DisplayReturnMessage(string result)
        {
            //Console.WriteLine("PUBLISH STATUS CALLBACK");
            //Console.WriteLine(result);
        }


    }
}
