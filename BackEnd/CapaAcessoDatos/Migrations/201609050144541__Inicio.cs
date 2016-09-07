namespace Emsys.DataAccesLayer.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class _Inicio : DbMigration
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
            
            //CreateTable(
            //    "dbo.Usuario",
            //    c => new
            //        {
            //            NombreUsuario = c.String(nullable: false, maxLength: 128),
            //            Contraseña = c.String(),
            //        })
            //    .PrimaryKey(t => t.NombreUsuario);
            
        }
        
        public override void Down()
        {
            //DropTable("dbo.Usuario");
            //DropTable("Evento");
        }
    }
}
