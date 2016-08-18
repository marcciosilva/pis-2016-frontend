namespace Servicios.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class _Inicio : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Usuarios",
                c => new
                    {
                        NombreUsuario = c.String(nullable: false, maxLength: 128),
                        Contraseña = c.String(),
                    })
                .PrimaryKey(t => t.NombreUsuario);
            
        }
        
        public override void Down()
        {
            DropTable("dbo.Usuarios");
        }
    }
}
