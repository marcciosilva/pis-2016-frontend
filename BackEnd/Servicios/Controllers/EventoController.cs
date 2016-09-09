using Emsys.DataAccesLayer.Core;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;

namespace Servicios.Controllers
{
    public class EventoController : ApiController
    {
        [HttpGet]
        public async Task<IHttpActionResult> Get()
        {
            using (var context = new EmsysContext())
            {
                return null;// Ok(await context.Evento.ToListAsync());
            }
        }
    }
}