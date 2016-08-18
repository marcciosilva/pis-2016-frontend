using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace Servicios.Models
{
    public class Usuario
    {
        [Key]
        public string NombreUsuario { get; set; }
        public string Contraseña { get; set; }
    }
}