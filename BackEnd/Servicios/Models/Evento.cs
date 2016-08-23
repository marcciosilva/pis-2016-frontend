using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace Servicios.Models
{
    public class Evento
    {
        [Key]
        public string NombreGenerador { get; set; }
        public string  Descripcion { get; set; }
        public string Direccion { get; set; }
        public DateTime FechaCreacion { get; set; }


    }
}