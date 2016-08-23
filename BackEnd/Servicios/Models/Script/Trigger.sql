
-----------------------------------------------
--CREAR EL TRIGGER
-----------------------------------------------
create trigger AuditandoEventos on Evento
for insert
as
exec master ..xp_cmdshell 'C:\Users\Andres\Desktop\PIS_repo_local\BackEnd\TriggerProgram\bin\Debug\TriggerProgram.exe'


-----------------------------------------------
--El trigger por configuracion por defecto no se puede instalar, pero ejecuten las siguientes lineas y sale magia!
-----------------------------------------------


--EXEC sp_configure 'show advanced options', 1;
--GO
--RECONFIGURE;
--GO
--EXEC sp_configure 'xp_cmdshell', 1;
--GO
--RECONFIGURE;
--GO

-----------------------------------------------
--El trigger por configuracion por defecto no se puede instalar, pero ejecuten las siguientes lineas y sale magia!
-----------------------------------------------

--si te dice "acceso denegado" entonces los permisos a nivel del usuario que ejecuta SQL Server no le permiten ejecutar la aplicacion en el directorio.
-- con este comando ves el usuario que ejecuta SQL server
--Entras a Srevicios y en SQL Agent pones como usuario local(el que estas usando) y reinicias el servicio