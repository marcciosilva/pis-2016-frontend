using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
using System.Configuration;
using System.Data;
using TableDependency.Mappers;
using TableDependency.SqlClient;
using TableDependency.Enums;
using Emsys.DataAccesLayer.Model;
using System.Data.Entity;
using Emsys.DataAccesLayer.Core;

namespace SqlDependecyProject
{
    class Program
    {
        private static bool llamo = true;
        static void Main(string[] args)
        {
            
            //me quedo loopeando
            while (true)
            {
                if (llamo)
                {
                    Console.WriteLine("Estoy esperando por modificaciones..");
                    Listener();
                    llamo = false;
                }
            }
        }

        static void Listener()
        {
            EmsysContext  db = new EmsysContext();
            var mapper = new ModelToTableMapper<Evento>();
            mapper.AddMapping(model => model.NombreGenerador, "NombreGenerador");
            _dependency = new SqlTableDependency<Evento>(_connectionString, "Evento", mapper);
            _dependency.OnChanged += _dependency_OnChanged;
            _dependency.OnError += _dependency_OnError;
            _dependency.Start();
        }

        // private static IList<Model.Evento> _stocks;
        private static readonly string _connectionString = System.Configuration.ConfigurationManager.ConnectionStrings["Context"].ConnectionString;
        // "data source=DESKTOP-T27K22L\\SQLExpressLocal;initial catalog=Prototipo1;integrated security=True";
        private static SqlTableDependency<Evento> _dependency;

        private static void _dependency_OnError(object sender, TableDependency.EventArgs.ErrorEventArgs e)
        {
            throw e.Error;
        }
        private static void _dependency_OnChanged(object sender, TableDependency.EventArgs.RecordChangedEventArgs<Evento> e)
        {
            if (e.ChangeType != ChangeType.None)
            {
                switch (e.ChangeType)
                {
                    case ChangeType.Delete:
                        Console.WriteLine("Boorrroo: " +e.Entity.NombreGenerador);
                    //_stocks.Remove(_stocks.FirstOrDefault(c => c.NombreGenerador == e.Entity.NombreGenerador));
                        break;
                    case ChangeType.Insert:
                        Console.WriteLine("Agrego: " + e.Entity.NombreGenerador);
                    //_stocks.Add(e.Entity);
                    break;
                    case ChangeType.Update:
                        //var customerIndex = _stocks.IndexOf(
                        //        _stocks.FirstOrDefault(c => c.NombreGenerador == e.Entity.NombreGenerador));
                        //if (customerIndex >= 0) _stocks[customerIndex] = e.Entity;
                        Console.WriteLine("Actualizo: " + e.Entity.NombreGenerador);
                        break;
                }
            }
        }
    }
}
