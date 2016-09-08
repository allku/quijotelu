USE [BDQuality]
GO

/****** Object:  View [dbo].[V_INFO_TRIBUTARIA]    Script Date: 28/07/2015 18:55:20 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[V_INFO_TRIBUTARIA]
AS
SELECT     razonsocial AS RAZON_SOCIAL, Nombre_Comercial, RUC, 'CALLE BOLIVAR' AS DIRECCION, Obligado_Contabilidad, 285 AS CONTRIBUYENTE_ESPECIAL
FROM         dbo.fe_infotributaria
WHERE     (RUC = '1708982259001')

GO
