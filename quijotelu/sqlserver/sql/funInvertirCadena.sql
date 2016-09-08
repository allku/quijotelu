USE [BDQuality]
GO

/****** Object:  UserDefinedFunction [dbo].[funInvertirCadena]    Script Date: 23/07/2015 18:29:00 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[funInvertirCadena](

    @pCadena VARCHAR(4000))

  RETURNS VARCHAR(4000)

AS

BEGIN
  DECLARE @vResult VARCHAR(100); 
  SELECT @vResult = REVERSE(@pCadena);

  RETURN @vResult;
END ;
GO


