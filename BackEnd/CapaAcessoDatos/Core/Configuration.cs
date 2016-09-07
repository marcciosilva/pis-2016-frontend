namespace Emsys.DataAccesLayer.Core
{
    using Model;
    using System;
    using System.Data.Entity;
    using System.Data.Entity.Migrations;
    using System.Linq;

    public class Configuration : DbMigrationsConfiguration<EmsysContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = true;
            AutomaticMigrationDataLossAllowed = false;
        }

        protected override void Seed(EmsysContext context)
        {
            //  This method will be called after migrating to the latest version.

            //  You can use the DbSet<T>.AddOrUpdate() helper extension method 
            //  to avoid creating duplicate seed data. E.g.
            //
            //    context.People.AddOrUpdate(
            //      p => p.FullName,
            //      new Person { FullName = "Andrew Peters" },
            //      new Person { FullName = "Brice Lambson" },
            //      new Person { FullName = "Rowan Miller" }
            //    );
            //
            context.Evento.AddOrUpdate(new Evento { NombreGenerador = "event1", Descripcion = "blablalblba", Direccion = "direccion", FechaCreacion = new DateTime(1980,10,10) });
            context.Evento.AddOrUpdate(new Evento { NombreGenerador = "event2", Descripcion = "blablaasdasdlblba", Direccion = "direccion", FechaCreacion = new DateTime(1980, 11, 12) });
            //public string NombreGenerador { get; set; }
            //public string Descripcion { get; set; }
            //public string Direccion { get; set; }
            //public DateTime FechaCreacion { get; set; }

        }
    }
}
