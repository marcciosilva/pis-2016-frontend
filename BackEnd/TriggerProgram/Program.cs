using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace TriggerProgram
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("abrio una app el trigger :D");

            //cambiar esto por una notificacion push 
            Model.Entities db = new Model.Entities();
            db.Usuario.Add(new Model.Usuario
            {
                NombreUsuario = "inserto un evento" + Guid.NewGuid(),
                Contraseña = "pass"
            });
            db.SaveChanges();
        }
    }
}
