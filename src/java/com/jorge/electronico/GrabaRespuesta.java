/*
 * Copyright (C) 2014 jorjoluiso
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jorge.electronico;

import com.jorge.db.DataBaseConnection;
import com.jorge.db.Instrucciones;
import com.jorge.propiedades.MotorConfiguracion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorjoluiso
 */
public class GrabaRespuesta {

    Instrucciones instrucciones;
    String codigoComprobante;
    String numeroComprobante;
    String numeroAutorizacion;
    String fechaAutorizacion;
    String observacion;
    String estado;

    public GrabaRespuesta() {
        instrucciones = new Instrucciones();
    }

    public GrabaRespuesta(String codigoComprobante, String numeroComprobante) {
        instrucciones = new Instrucciones();
        this.codigoComprobante = codigoComprobante;
        this.numeroComprobante = numeroComprobante;
    }

    public GrabaRespuesta(String codigoComprobante, String numeroComprobante, String observacion) {
        instrucciones = new Instrucciones();
        this.codigoComprobante = codigoComprobante;
        this.numeroComprobante = numeroComprobante;
        this.observacion = observacion;
        estado = "ENVIADO";
    }

    public GrabaRespuesta(String codigoComprobante, String numeroComprobante, String numeroAutorizacion, String fechaAutorizacion, String observacion) {
        instrucciones = new Instrucciones();
        this.codigoComprobante = codigoComprobante;
        this.numeroComprobante = numeroComprobante;
        this.numeroAutorizacion = numeroAutorizacion;
        this.fechaAutorizacion = fechaAutorizacion;
        this.observacion = observacion;
        estado = "AUTORIZADO";
    }

    public void setRespuesta() {
        MotorConfiguracion configMotor = new MotorConfiguracion();
        DataBaseConnection oc = new DataBaseConnection();
        Connection conn;
        try {

            conn = oc.getConnection(configMotor.getHost(), configMotor.getPuerto(), configMotor.getServicio(), configMotor.getUsuario(), configMotor.getClave());
            if (!existeComprobante(conn, instrucciones.verificarComprobante, codigoComprobante, numeroComprobante)) {
                insertarComprobante(conn, instrucciones.insertaComprobante, codigoComprobante, numeroComprobante);
            } else {
                actualizarComprobante(conn, instrucciones.actualizaComprobante, codigoComprobante, numeroComprobante);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(GrabaRespuesta.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    boolean existeComprobante(Connection conn, String consulta, String codigo, String numero) {
        Statement stmt;
        ResultSet rs;
        String sql;
        try {
            stmt = conn.createStatement();
            sql = consulta + " where CODIGO='" + codigo + "' and NUMERO='" + numero + "'";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(GrabaRespuesta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean existeComprobante() {
        MotorConfiguracion configMotor = new MotorConfiguracion();
        DataBaseConnection oc = new DataBaseConnection();
        Connection conn;
        boolean respuesta;
        try {
            conn = oc.getConnection(configMotor.getHost(), configMotor.getPuerto(), configMotor.getServicio(), configMotor.getUsuario(), configMotor.getClave());
            respuesta=existeComprobante(conn, instrucciones.verificarComprobante, codigoComprobante, numeroComprobante);
            conn.close();
            return respuesta;
        } catch (SQLException ex) {
            Logger.getLogger(GrabaRespuesta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    void insertarComprobante(Connection conn, String insert, String codigo, String numero) {
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(insert);
            stmt.setString(1, codigoComprobante);
            stmt.setString(2, numeroComprobante);
            stmt.setString(3, numeroAutorizacion);
            stmt.setString(4, fechaAutorizacion);
            stmt.setString(5, observacion);
            stmt.setString(6, estado);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GrabaRespuesta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void actualizarComprobante(Connection conn, String update, String codigo, String numero) {
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(update);
            stmt.setString(1, numeroAutorizacion);
            stmt.setString(2, fechaAutorizacion);
            stmt.setString(3, observacion);
            stmt.setString(4, estado);
            stmt.setString(5, codigoComprobante);
            stmt.setString(6, numeroComprobante);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GrabaRespuesta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
