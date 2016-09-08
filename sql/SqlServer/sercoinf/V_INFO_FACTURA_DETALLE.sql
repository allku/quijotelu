USE [BDQuality]
GO

/****** Object:  View [dbo].[V_INFO_FACTURA_DETALLE]    Script Date: 28/07/2015 18:57:02 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE VIEW [dbo].[V_INFO_FACTURA_DETALLE]
AS
SELECT     codigo AS CODIGO, numero AS NUMERO, codigo_principal AS CODIGO_PRINCIPAL, Descripcion AS DESCRIPCION, CAST(ROUND(Cantidad, 2) AS decimal(18, 2))  AS CANTIDAD, 
                      CAST(ROUND(Precio_Uni, 2) AS decimal(18, 2)) AS PRECIO_UNITARIO, cod_porc AS CODIGO_PORCENTAJE, porc_iva AS PORCENTAJE_IVA, CAST(ROUND(Impuesto, 2) AS decimal(18, 2)) AS VALOR_IVA,CAST(ROUND(Descuento, 2) AS decimal(18, 2)) AS DESCUENTO, 
                      CAST(ROUND(Precio_total_sin_imp, 2) AS decimal(18, 2)) AS PRECIO_TOTAL_SIN_IMPUESTO
FROM         dbo.fe_facturadetalle

GO


