USE [BDQuality]
GO

/****** Object:  View [dbo].[V_INFO_RETENCION_DETALLE]    Script Date: 03/08/2015 15:23:49 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE VIEW [dbo].[V_INFO_RETENCION_DETALLE]
AS
SELECT        Codigo, Numero, Tipo, Codigo_Sri, BaseImponible AS BASE_IMPONIBLE, CAST(ROUND(Porcentaje, 0) AS decimal(3)) AS Porcentaje, CAST(ROUND(ValorRetenido, 2) AS decimal(18,2)) AS VALOR_RETENIDO, 
                         Tipo_Comp AS TIPO_COMPROBANTE, Comprobante AS NUMERO_COMPROBANTE, CONVERT(VARCHAR(10), Fecha_Comp, 103) AS FECHA_COMPROBANTE
FROM            dbo.fe_retenciondetalle1


GO
