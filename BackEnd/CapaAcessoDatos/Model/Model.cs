namespace Emsys.DataAccesLayer.Model
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;
    using Migrations;
    using System.Data.Entity.Migrations;

    public partial class Model : DbContext
    {
        public Model()
            : base("name=Context")
        {
            Database.SetInitializer(new MigrateDatabaseToLatestVersion<Model, Configuration>());            
        }
        
        public virtual DbSet<Evento> Evento { get; set; }
        public virtual DbSet<Usuario> Usuario { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
        }
    }
}
