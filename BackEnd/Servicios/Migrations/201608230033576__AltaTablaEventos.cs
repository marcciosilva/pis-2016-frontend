namespace Servicios.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class _AltaTablaEventos : DbMigration
    {
        public override void Up()
        {
            RenameTable(name: "dbo.Usuarios", newName: "Usuario");
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
            DropTable("dbo.Evento");
            RenameTable(name: "dbo.Usuario", newName: "Usuarios");
        }
    }
}
