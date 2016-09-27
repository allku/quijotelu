--------------------------------------------------------
--  DDL for View V_FORMA_PAGO_FACTURA
--------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_FORMA_PAGO_FACTURA ("PAGO", "FORMA_PAGO",
  "CODIGO", "FACTURA", "PLAZO", "TIEMPO")
AS
  SELECT
    NVL(EFECTIVO,0)                          AS pago,
    'SIN UTILIZACION DEL SISTEMA FINANCIERO' AS forma_pago,
    '01'                                     AS codigo,
    num_pago,
    NULL,
    NULL
  FROM
    CXC_PAGO_CONTADO
  WHERE
    COD_DOCUMENTO    ='FAC'
  AND NVL(EFECTIVO,0)>0
  UNION

  /*
  select 0, 'Dinero electrónico','17' as codigo,num_pago
  from CXC_PAGO_CONTADO
  where COD_DOCUMENTO='FAC'
  union
  */
  SELECT
    NVL(tarjeta,0),
    'TARJETA DE CRÉDITO',
    '19' AS codigo,
    num_pago,
    NULL,
    NULL
  FROM
    CXC_PAGO_CONTADO
  WHERE
    COD_DOCUMENTO   ='FAC'
  AND NVL(tarjeta,0)>0
  UNION
  SELECT
    NVL(CHEQUES,0),
    'CHEQUE PROPIO',
    '02' AS codigo,
    num_pago,
    NULL,
    NULL
  FROM
    CXC_PAGO_CONTADO
  WHERE
    COD_DOCUMENTO   ='FAC'
  AND NVL(CHEQUES,0)>0
  UNION
  SELECT
    NVL(DEPOSITO,0),
    'DEPOSITO EN CUENTA',
    '13' AS codigo,
    num_pago,
    NULL,
    NULL
  FROM
    CXC_PAGO_CONTADO
  WHERE
    COD_DOCUMENTO    ='FAC'
  AND NVL(DEPOSITO,0)>0
  UNION
  SELECT
    NVL(f.credito,0),
    ' OTROS CON UTILIZACION DEL SISTEMA FINANCIERO',
    '20' AS codigo,
    f.num_pago,
    1,
    (
      SELECT
        cxc.DIAS_PLAZO
      FROM
        CXC_DOC_COBRAR cxc
      WHERE
        cxc.COD_DOCUMENTO  ='FAC'
      AND cxc.NUM_DOCUMENTO=f.num_pago
    )
  FROM
    CXC_PAGO_CONTADO f
  WHERE
    f.COD_DOCUMENTO   ='FAC'
  AND NVL(f.credito,0)>0;
