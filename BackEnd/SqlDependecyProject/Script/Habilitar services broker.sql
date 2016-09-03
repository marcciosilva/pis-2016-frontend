--habilitar service broker

SELECT NAME, IS_BROKER_ENABLED FROM SYS.DATABASES

ALTER DATABASE Prototipo1 SET ENABLE_BROKER


alter database Prototipo1 set enable_broker with rollback immediate;