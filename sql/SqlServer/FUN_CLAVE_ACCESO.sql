USE [BDQuality]
GO

/****** Object:  UserDefinedFunction [dbo].[FUN_CLAVE_ACCESO]    Script Date: 28/07/2015 18:15:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/* SQLINES EVALUATION VERSION TRUNCATES VARIABLE NAMES AND COMMENTS. */
/* OBTAIN A LICENSE AT WWW.SQLINES.COM FOR FULL CONVERSION. THANK YOU. */


CREATE FUNCTION [dbo].[FUN_CLAVE_ACCESO](
    @P_FECHA              VARCHAR(50) ,
    @P_CODIGO_COMPROBANTE VARCHAR(50) ,
    @P_NUMERO_COMPROBANTE VARCHAR(50) )
  RETURNS VARCHAR(4000)
AS
BEGIN
  DECLARE @v_clave              VARCHAR(100);
  DECLARE @v_ruc                VARCHAR(13);
  DECLARE @v_ambiente           VARCHAR(1)='1';--Pruebas 1, Pr... *** SQLINES FOR EVALUATION USE ONLY *** 
  DECLARE @v_codigo_comprobante VARCHAR(2);
  DECLARE @v_numero_comprobante VARCHAR(20);
 
  SELECT @v_ruc = ruc FROM V_INFO_TRIBUTARIA;
  
  set @v_numero_comprobante='001001'+RIGHT(REPLICATE('0', 9)+ CAST(@P_NUMERO_COMPROBANTE AS VARCHAR(9)), 9);  
  
  IF @P_CODIGO_COMPROBANTE   ='FAC' 
    SET @v_codigo_comprobante   ='01';
  ELSE IF @P_CODIGO_COMPROBANTE='RET' 
    SET @v_codigo_comprobante   ='07';
  ELSE IF @P_CODIGO_COMPROBANTE='DVC' 
    SET @v_codigo_comprobante   ='04';
  ELSE IF @P_CODIGO_COMPROBANTE='NCC' 
    SET @v_codigo_comprobante   ='04';
  ELSE IF @P_CODIGO_COMPROBANTE='GUI' 
    SET @v_codigo_comprobante   ='06';
   
SET @v_clave=REPLACE(@P_FECHA,'/','')+isnull(@v_codigo_comprobante, '')+isnull(@v_ruc, '')+isnull(@v_ambiente, '')+isnull(@v_numero_comprobante, '')+'123456781';
SET @v_clave=isnull(@v_clave, '')+ISNULL(CAST(dbo.funModulo11(@v_clave) as varchar), '');
RETURN @v_clave;

END;
GO


