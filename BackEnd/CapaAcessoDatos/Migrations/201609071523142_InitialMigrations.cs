namespace Emsys.DataAccesLayer.Core
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class InitialMigrations : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Evento",
                c => new
                {
                    NombreGenerador = c.String(nullable: false, maxLength: 128),
                    Descripcion = c.String(),
                    Direccion = c.String(),
                    FechaCreacion = c.DateTime(nullable: false),
                })
                .PrimaryKey(t => t.NombreGenerador);
        }
        
        public override void Down()
        {
        }
    }
}
