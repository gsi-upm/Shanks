/*******************************************************************************
 * Copyright  (C) 2014 Álvaro Carrera Barroso
 * Grupo de Sistemas Inteligentes - Universidad Politecnica de Madrid
 *  
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.agent.capability.movement;

import sim.util.Double2D;
import sim.util.Double3D;

/**
 * 
 * This class represent the location of an agent in the simulation
 * 
 * @author a.carrera
 * 
 */
public class Location {

    private Double2D location2D;
    private Double3D location3D;
    private boolean is2D;
    private boolean is3D;

    /**
     * Create an empty location
     */
    public Location() {
        this.location2D = null;
        this.location3D = null;
        this.is2D = false;
        this.is3D = false;
    }

    /**
     * Create a 2D location
     * 
     * @param location
     */
    public Location(Double2D location) {
        this.setLocation2D(location);
        this.is2D = true;
        this.location3D = null;
        this.is3D = false;
    }

    /**
     * Create a 3D location
     * 
     * @param location
     */
    public Location(Double3D location) {
        this.location2D = null;
        this.is2D = false;
        this.setLocation3D(location);
        this.is3D = true;
    }

    /**
     * @return the location2D
     */
    public Double2D getLocation2D() {
        if (this.is2DLocation()) {
            return location2D;   
        } else {
            return null;
        }
    }

    /**
     * @param location2D
     *            the location2D to set
     */
    public void setLocation2D(Double2D location2D) {
        this.location2D = location2D;
        this.is2D = true;
    }

    /**
     * @return the location3D
     */
    public Double3D getLocation3D() {
        if (this.is3DLocation()) {
            return location3D;            
        } else {
            return null;   
        }
    }

    /**
     * @param location3D
     *            the location3D to set
     */
    public void setLocation3D(Double3D location3D) {
        this.location3D = location3D;
        this.is3D = true;
    }

    /**
     * @return true if the location has a Double2D location, else if not
     */
    public boolean is2DLocation() {
        return this.is2D;
    }

    /**
     * @return true if the location has a Double3D location, else if not
     */
    public boolean is3DLocation() {
        return this.is3D;
    }

    /**
     * Compare two locations and return if the locations are near or not
     * 
     * @param location Location to compare with
     * @param distance The distance between two locations
     * @return true is the real distance is lower or equals than the distance parameter
     */
    public boolean isNearTo(Location location, double distance) {
        if (this.is2DLocation() && location.is2DLocation()) {
            return this.isNearTo(location.getLocation2D(), distance);
        } else if (this.is3DLocation() && location.is3DLocation()) {
            return this.isNearTo(location.getLocation3D(), distance);
        } else {
            return false;
        }
    }

    /**
     * Compare two locations and return if the locations are near or not
     * 
     * @param location Location to compare with
     * @param distance The distance between two locations
     * @return true is the real distance is lower or equals than the distance parameter
     */
    public boolean isNearTo(Double2D location, double distance) {
        if (this.is2DLocation()) {
            return this.getLocation2D().distance(location) < distance;
        } else {
            return false;
        }
    }

    /**
     * Compare two locations and return if the locations are near or not
     * 
     * @param location Location to compare with
     * @param distance The distance between two locations
     * @return true is the real distance is lower or equals than the distance parameter
     */
    public boolean isNearTo(Double3D location, double distance) {
        if (this.is3DLocation()) {
            return this.getLocation3D().distance(location) < distance;
        } else {
            return false;
        }
    }
}
