USE [BDQuality]
GO

/****** Object:  View [dbo].[V_INFO_RETENCION_MAESTRO]    Script Date: 03/08/2015 15:23:31 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[V_INFO_RETENCION_MAESTRO]
AS
SELECT        Codigo, Numero, CodigoDocumento AS CODIGO_DOCUMENTO, Estab AS ESTABLECIMIENTO, PtoEmision AS PUNTO_EMISION, Secuencial, CONVERT(VARCHAR(10), Fecha, 103) AS FECHA, 
                         TipoDocumento AS TIPO_DOCUMENTO, Documento, RazonSocial AS RAZON_SOCIAL, Periodo AS PERIODO_FISCAL, CASE WHEN Direccion = '' THEN NULL ELSE Direccion END AS Direccion, 
                         CASE WHEN TELEFONO = '' THEN NULL ELSE TELEFONO END AS TELEFONO, CASE WHEN Mail = '' THEN NULL ELSE Mail END AS Mail
FROM            dbo.fe_retencionmaestro

GO


