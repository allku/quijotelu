USE [BDQuality]
GO

/****** Object:  View [dbo].[V_INFO_FACTURA_MAESTRO]    Script Date: 28/07/2015 18:56:37 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



CREATE VIEW [dbo].[V_INFO_FACTURA_MAESTRO]
AS
SELECT     codigo, numero, '01' AS CODIGO_DOCUMENTO, Noserie AS ESTABLECIMIENTO, Noserieemision AS PUNTO_EMISION, RIGHT('000000000' + CONVERT(VARCHAR, 
                      Nosecuencial), 9) AS SECUENCIAL, CONVERT(VARCHAR(10), fecha, 103) AS FECHA, CASE WHEN CAST(ROUND(Iva, 2) AS decimal(18, 2)) 
                      = 0 THEN CAST(ROUND(Total_con_Iva, 2) AS decimal(18, 2)) ELSE 0 END AS TOTAL_SIN_IVA, CASE WHEN CAST(ROUND(Iva, 2) AS decimal(18, 2)) 
                      = 0 THEN 0 ELSE CAST(ROUND(Total_con_Iva, 2) AS decimal(18, 2)) END AS TOTAL_CON_IVA, CAST(ROUND(Iva, 2) AS decimal(18, 2)) AS IVA, 
                      CAST(ROUND(Descuento, 2) AS decimal(18, 2)) AS DESCUENTOS, CAST(ROUND(Total, 2) AS decimal(18, 2)) AS TOTAL, Estado, Codigo_Cli AS TIPO_DOCUMENTO, 
                      Documento_cli AS DOCUMENTO, razonSocial AS RAZON_SOCIAL, CASE WHEN Direccion='' then null else  Direccion end as Direccion, case when Fono='' then null else Fono end AS TELEFONO, case when eMail='' then null else eMail end AS MAIL, Guia_Remision
FROM         dbo.fe_facturamaestro1



GO
