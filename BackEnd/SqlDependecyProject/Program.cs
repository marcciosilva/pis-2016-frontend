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
        static  void Main(string[] args)
        {
            Task.Run(() =>
            {
                MainAsync();
            }).Wait();
        }

        static async void MainAsync()
        {
            string conectionString = "Data Source=DESKTOP-T27K22L\\SQLExpressLocal;Initial Catalog=Prototipo1;Integrated Security=True";//ConfigurationManager.ConnectionStrings["Entities"].ConnectionString
            var sampleSqlConnection = new SqlConnection(conectionString);
            SqlDependency.Start(conectionString);
            var comand = new SqlCommand();
            comand.Connection = sampleSqlConnection;
            var conection= comand.Connection.OpenAsync();
            Task.WaitAll(conection); //make sure the task is completed
            if (conection.IsFaulted) // in case of failure
            {
                throw new Exception("Connection failure", conection.Exception);
            }
            comand.CommandType = CommandType.Text;
            comand.CommandText = "SELECT [NombreGenerador],[Descripcion],[Direccion],[FechaCreacion] FROM[dbo].[Evento];";
            comand.Notification = null;

            var sampleSqlDependency = new SqlDependency(comand);
            sampleSqlDependency.OnChange += new OnChangeEventHandler(SqlDependencyOnChange);
            var EjecuarLectura= comand.ExecuteReaderAsync();

            Task.WaitAll(EjecuarLectura); //make sure the task is completed
            if (EjecuarLectura.IsFaulted) // in case of failure
            {
                throw new Exception("Connection failure", EjecuarLectura.Exception);
            }

            Console.ReadLine();
            //while (true)
            //{
            //    Console.ReadLine();
            //}
        }

        static void SqlDependencyOnChange(object sender, SqlNotificationEventArgs eventArgs)
        {
            if (eventArgs.Info == SqlNotificationInfo.Invalid)
            {
                Console.WriteLine("The above notification query is not valid.");
            }
            else
            {
                Console.WriteLine("Notification Info: " + eventArgs.Info);
                Console.WriteLine("Notification source: " + eventArgs.Source);
                Console.WriteLine("Notification type: " + eventArgs.Type);
                Task.Run(() =>
                {
                    MainAsync();
                }).Wait();
            }
        }
    }
}
