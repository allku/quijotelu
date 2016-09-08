USE [BDQuality]
GO

/****** Object:  UserDefinedFunction [dbo].[funGetSumaPorDigitos]    Script Date: 28/07/2015 18:16:17 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[funGetSumaPorDigitos](
    @pCadena VARCHAR(500))
  RETURNS numeric(38,0)
AS
BEGIN
  DECLARE @vPivote        numeric(38,0)=2;
  DECLARE @vLongitudCadena numeric(38,0);
  DECLARE @vCantidadTotal  numeric(38,0)=0;
  DECLARE @i               numeric(38,0)=1;
  DECLARE @vTemporal       numeric(38,0);
 
  SET @vLongitudCadena=LEN(@pCadena);
  WHILE @i         <= @vLongitudCadena
  BEGIN
    IF @vPivote= 8 BEGIN
      SET @vPivote=2;
    END 
    SET @vTemporal     =cast(SUBSTRING(@pCadena,@i,1) as numeric(38,0));
    SET @vTemporal     =@vTemporal     *@vPivote;
    SET @vPivote      =@vPivote      +1;
    SET @vCantidadTotal=@vCantidadTotal+@vTemporal;
    SET @i             =@i             +1;
  END;
  SET @vCantidadTotal=11-(@vCantidadTotal %11);
  RETURN @vCantidadTotal;
END ;
GO


