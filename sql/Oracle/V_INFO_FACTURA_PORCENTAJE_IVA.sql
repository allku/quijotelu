--------------------------------------------------------
-- Archivo creado  - jueves-octubre-20-2016   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for View V_INFO_FACTURA_PORCENTAJE_IVA
--------------------------------------------------------

  CREATE OR REPLACE FORCE VIEW "V_INFO_FACTURA_PORCENTAJE_IVA" ("CODIGO", "NUMERO", "PORCENTAJE_IVA") AS 
  SELECT DISTINCT CODIGO,NUMERO,PORCENTAJE_IVA
    
FROM V_INFO_FACTURA_DETALLE
WHERE PORCENTAJE_IVA>0;
