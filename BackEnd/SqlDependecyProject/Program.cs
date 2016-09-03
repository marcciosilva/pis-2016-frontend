using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
using System.Configuration;
using System.Data;

namespace SqlDependecyProject
{
    class Program
    {
        private static bool llamo = true;
        static void Main(string[] args)
        {
            while (true) {
                if (llamo) {
                    Listener();
                    llamo = false;
                }
            }
        }
        private static SqlCommand comand;
        static void Listener()
        {
            string conectionString = "Data Source=DESKTOP-T27K22L\\SQLExpressLocal;Initial Catalog=Prototipo1;Integrated Security=True";//ConfigurationManager.ConnectionStrings["Entities"].ConnectionString
            var sampleSqlConnection = new SqlConnection(conectionString);
            SqlDependency.Stop(conectionString);
            SqlDependency.Start(conectionString);
            //var
            comand = new SqlCommand();
            comand.Connection = sampleSqlConnection;
            comand.Connection.Open();
            comand.CommandType = CommandType.Text;
            comand.CommandText = "SELECT [NombreGenerador],[Descripcion],[Direccion],[FechaCreacion] FROM[dbo].[Evento];";
            comand.Notification = null;
            comand.CommandTimeout = 15;

            var sampleSqlDependency = new SqlDependency(comand);
            sampleSqlDependency.OnChange += new OnChangeEventHandler(SqlDependencyOnChange);
            var EjecuarLectura = comand.ExecuteReader();
            EjecuarLectura.Read();
        }

        static void SqlDependencyOnChange(object sender, SqlNotificationEventArgs eventArgs)
        {
            if (eventArgs.Info == SqlNotificationInfo.Invalid)
            {
                Console.WriteLine("The above notification query is not valid.");
            }
            else
            {
                Console.WriteLine("Notification Info: " + eventArgs.Info);//esto dice siu es un delete, update o insert
                //Console.WriteLine("Notification source: " + eventArgs.Source);
                //Console.WriteLine("Notification type: " + eventArgs.Type);
                //aca tengo que disparar un mensaje para que actualice.
                llamo = true;
            }
        }
    }
}
