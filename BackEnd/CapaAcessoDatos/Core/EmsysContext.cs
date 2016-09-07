namespace Emsys.DataAccesLayer.Core
{
    using System.Data.Entity;
    using Microsoft.AspNet.Identity.EntityFramework;
    using Model;

    public partial class EmsysContext : IdentityDbContext
    {
        public DbSet<Evento> Evento { get; set; }
        //public DbSet<Usuario> Usuario { get; set; }

    }
}
