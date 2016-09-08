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
package com.jorge.datos.guia.remision;

import java.util.List;

/**
 *
 * @author jorjoluiso
 */
public class Destinatarios {

    List<Destinatario> Destinatario;

    public Destinatarios(List<Destinatario> Destinatario) {
        this.Destinatario = Destinatario;
    }

    public List<Destinatario> getDestinatario() {
        return Destinatario;
    }

    public void setDestinatario(List<Destinatario> Destinatario) {
        this.Destinatario = Destinatario;
    }
}
