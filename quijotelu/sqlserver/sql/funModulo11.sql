USE [BDQuality]
GO

/****** Object:  UserDefinedFunction [dbo].[funModulo11]    Script Date: 23/07/2015 18:30:25 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/* SQLINES EVALUATION VERSION TRUNCATES VARIABLE NAMES AND COMMENTS. */
/* OBTAIN A LICENSE AT WWW.SQLINES.COM FOR FULL CONVERSION. THANK YOU. */

CREATE FUNCTION [dbo].[funModulo11](
    @pCadena VARCHAR(500))
  RETURNS numeric(38,0)
AS
BEGIN
  DECLARE @vResult numeric(38,0);
   
  SET @vResult=dbo.funGetSumaPorDigitos(dbo.funInvertirCadena(@pCadena));

  if @vResult=11 
		return 0
  else if @vResult=10
    return 1
	   
  RETURN @vResult;
END ;
GO


