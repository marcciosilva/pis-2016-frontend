using Emsys.DataAccesLayer.Core;
using Servicios.App_Start;
using System.Data.Entity;
using System.Web.Http;

namespace Emsys.ServiceLayer
{
    public class WebApiApplication : System.Web.HttpApplication
    {
        protected void Application_Start()
        {
            GlobalConfiguration.Configure(WebApiConfig.Register);
            Database.SetInitializer(new Initializer());
            GlobalConfiguration.Configure(FilterConfig.Configure);
        }
    }
}
